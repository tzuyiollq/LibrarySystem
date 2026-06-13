package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.User;
import java.sql.*;
import java.util.*;

public class UserDAO {

    public User login(String name, String password) {

        String sql = "SELECT * FROM users WHERE name=? AND password=?";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, name);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

            	return new User(
            		    rs.getInt("user_id"),
            		    rs.getString("student_no"),
            		    rs.getString("name"),
            		    rs.getString("password"),
            		    rs.getString("role_level"),
            		    rs.getString("status")
            	);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public void register(String studentNo, String name, String password, String roleLevel) {

        String sql = """
            INSERT INTO users (student_no, name, password, role_level, status)
            VALUES (?, ?, ?, ?, 'ACTIVE')
        """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, studentNo);
            stmt.setString(2, name);
            stmt.setString(3, password);
            stmt.setString(4, roleLevel);

            stmt.executeUpdate();

            System.out.println("註冊成功！");

        } catch (Exception e) {
            System.out.println("註冊失敗，可能是學號重複。");
            e.printStackTrace();
        }
    }
    public boolean existsStudentNo(String studentNo) {

        String sql =
                "SELECT COUNT(*) FROM users WHERE student_no = ?";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, studentNo);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
    //管理者功能
    // 查詢所有使用者
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                    rs.getInt("user_id"),
                    rs.getString("student_no"),
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getString("role_level"),
                    rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

 // 停權使用者
    public boolean suspendUser(String studentNo) {
        return updateStatus(studentNo, "SUSPENDED");
    }

    // 復權使用者
    public boolean activateUser(String studentNo) {
        return updateStatus(studentNo, "ACTIVE");
    }

    private boolean updateStatus(String studentNo, String status) {
        String sql = "UPDATE users SET status = ? WHERE student_no = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setString(2, studentNo);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}