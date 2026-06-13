package util;

import com.google.gson.*;
import dao.DBConnection;

import java.io.FileReader;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataImporter {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void importUsers() {

        String path = "assets/data/Users.json";

        String sql = """
            INSERT INTO users
            (student_no, name, password, role_level, created_at, status)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            FileReader reader = new FileReader(path)
        ) {
            JsonArray users = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement element : users) {
                JsonObject user = element.getAsJsonObject();

                stmt.setString(1, user.get("student_no").getAsString());
                stmt.setString(2, user.get("name").getAsString());
                stmt.setString(3, user.get("password").getAsString());
                stmt.setString(4, user.get("role_level").getAsString());
                stmt.setString(5, user.get("created_at").getAsString());
                stmt.setString(6, user.get("status").getAsString());

                stmt.executeUpdate();
            }

            System.out.println("Users 匯入完成！");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void importBooks() {

        String path = "assets/data/Books.json";

        String bookSql = """
            INSERT INTO books
            (title, authors, subjects, publisher, publish_year,
             edition, format_desc, source, note, status)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'AVAILABLE')
        """;

        String isbnSql = """
            INSERT INTO book_isbns (book_id, isbn)
            VALUES (?, ?)
        """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement bookStmt =
                    conn.prepareStatement(bookSql, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement isbnStmt =
                    conn.prepareStatement(isbnSql);
            FileReader reader = new FileReader(path)
        ) {
            JsonArray books = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement element : books) {
                JsonObject book = element.getAsJsonObject();

                bookStmt.setString(1, book.get("題名").getAsString());
                bookStmt.setString(2, joinJsonArray(book.getAsJsonArray("作者")));
                bookStmt.setString(3, joinJsonArray(book.getAsJsonArray("主題")));
                bookStmt.setString(4, book.get("出版者").getAsString());
                bookStmt.setString(5, book.get("出版年").getAsString());
                bookStmt.setString(6, book.get("版本").getAsString());
                bookStmt.setString(7, book.get("格式").getAsString());
                bookStmt.setString(8, book.get("資料來源").getAsString());
                bookStmt.setString(9, book.get("附註").getAsString());

                bookStmt.executeUpdate();

                ResultSet keys = bookStmt.getGeneratedKeys();

                if (keys.next()) {
                    int bookId = keys.getInt(1);

                    JsonArray isbns = book.getAsJsonArray("識別號");

                    for (JsonElement isbnElement : isbns) {
                        isbnStmt.setInt(1, bookId);
                        isbnStmt.setString(2, isbnElement.getAsString());
                        isbnStmt.executeUpdate();
                    }
                }
            }

            System.out.println("Books 匯入完成！");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void importBorrowRecords() {

        String path = "assets/data/Borrow_records.json";

        String sql = """
            INSERT INTO borrow_records
            (user_id, book_id, borrow_date, due_date,
             return_date, borrow_days, created_at)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            FileReader reader = new FileReader(path)
        ) {
            JsonArray records = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement element : records) {
                JsonObject record = element.getAsJsonObject();

                stmt.setInt(1, record.get("user_id").getAsInt());
                stmt.setInt(2, record.get("book_id").getAsInt());

                stmt.setString(3, parseRelativeDate(record.get("borrow_date")));
                stmt.setString(4, parseRelativeDate(record.get("due_date")));

                if (record.get("return_date") == null
                        || record.get("return_date").isJsonNull()) {
                    stmt.setNull(5, Types.TIMESTAMP);
                } else {
                    stmt.setString(5, parseRelativeDate(record.get("return_date")));
                }

                stmt.setInt(6, record.get("borrow_days").getAsInt());
                stmt.setString(7, parseRelativeDate(record.get("created_at")));

                stmt.executeUpdate();
            }

            updateBorrowedBookStatus();

            System.out.println("BorrowRecords 匯入完成！");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void importDefaultAdmin() {

        String sql = """
            INSERT INTO admins (username, password)
            VALUES (?, ?)
        """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, "admin");
            stmt.setString(2, "1234");

            stmt.executeUpdate();

            System.out.println("預設管理者建立完成！");

        } catch (Exception e) {
            System.out.println("預設管理者已存在，略過建立。");
        }
    }

    private static void updateBorrowedBookStatus() {

        String sql = """
            UPDATE books
            SET status = 'BORROWED'
            WHERE book_id IN (
                SELECT book_id
                FROM borrow_records
                WHERE return_date IS NULL
            )
        """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String joinJsonArray(JsonArray array) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < array.size(); i++) {
            sb.append(array.get(i).getAsString());

            if (i < array.size() - 1) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

    private static String parseRelativeDate(JsonElement element) {

        if (element == null || element.isJsonNull()) {
            return null;
        }

        String value = element.getAsString().trim();

        if (value.contains("days")) {
            int days = Integer.parseInt(value.replace("days", "").trim());

            return LocalDateTime.now()
                    .plusDays(days)
                    .format(FORMATTER);
        }

        return value;
    }
}