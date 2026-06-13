package util;

import dao.DBConnection;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;

public class SQLRunner {

    public static void runSqlFile(String filePath) {

        try {
            String sql = Files.readString(Paths.get(filePath));
            String[] statements = sql.split(";");

            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();

            for (String statement : statements) {
                statement = statement.trim();

                if (!statement.isEmpty()) {
                    stmt.execute(statement);
                }
            }

            stmt.close();
            conn.close();

            System.out.println("SQL 執行完成：" + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void resetDatabase() {

        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();

            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.execute("TRUNCATE TABLE borrow_records");
            stmt.execute("TRUNCATE TABLE book_isbns");
            stmt.execute("TRUNCATE TABLE books");
            stmt.execute("TRUNCATE TABLE users");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

            stmt.close();
            conn.close();

            System.out.println("資料表清空完成！");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}