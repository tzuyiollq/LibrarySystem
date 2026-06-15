package dao;

import model.User;
import java.sql.*;
import java.util.*;

public class UserDAO {

    public User loginByStudentNoOrName(String account, String password) {

        String sql = """
            SELECT *
            FROM users
            WHERE (student_no = ? OR name = ?)
              AND password = ?
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, account);
            stmt.setString(2, account);
            stmt.setString(3, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("student_no"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("role_level"),
                        rs.getString("status"),
                        rs.getInt("credit_score")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = """
            SELECT *
            FROM users
            ORDER BY user_id DESC
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("student_no"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("role_level"),
                        rs.getString("status"),
                        rs.getInt("credit_score")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public boolean suspendUser(String studentNo) {
        return updateStatus(studentNo, "SUSPENDED");
    }

    public boolean activateUser(String studentNo) {
        return updateStatus(studentNo, "ACTIVE");
    }

    private boolean updateStatus(String studentNo, String status) {

        String sql = """
            UPDATE users
            SET status = ?
            WHERE student_no = ?
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, status);
            stmt.setString(2, studentNo);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateUserRole(String studentNo, String roleLevel) {

        String sql = """
            UPDATE users
            SET role_level = ?,
                credit_score = CASE
                    WHEN ? = 'VIP' AND credit_score < 150 THEN 150
                    WHEN ? = 'NORMAL' AND credit_score > 100 THEN 100
                    ELSE credit_score
                END
            WHERE student_no = ?
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, roleLevel);
            stmt.setString(2, roleLevel);
            stmt.setString(3, roleLevel);
            stmt.setString(4, studentNo);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean registerUser(
            String studentNo,
            String name,
            String password,
            String roleLevel
    ) {
        String sql = """
            INSERT INTO users
            (student_no, name, password, role_level, status, credit_score)
            VALUES (?, ?, ?, ?, 'ACTIVE', ?)
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            int creditScore =
                    "VIP".equals(roleLevel) ? 150 : 100;

            stmt.setString(1, studentNo);
            stmt.setString(2, name);
            stmt.setString(3, password);
            stmt.setString(4, roleLevel);
            stmt.setInt(5, creditScore);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}