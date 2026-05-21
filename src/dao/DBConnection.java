package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/library_system";
    private static final String USER = "root";
    private static final String PASSWORD = "5678";

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("資料庫連線成功！");
            return conn;
        } catch (Exception e) {
            System.out.println("資料庫連線失敗！");
            e.printStackTrace();
            return null;
        }
    }
}