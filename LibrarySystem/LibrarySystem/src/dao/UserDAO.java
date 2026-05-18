package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.User;

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
            		    rs.getString("name"),
            		    rs.getString("password"),
            		    rs.getString("role_level")
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
}