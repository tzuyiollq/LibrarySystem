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
            INSERT INTO users (student_no, name, password, role_level, created_at, status)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            JsonArray users = JsonParser.parseReader(new FileReader(path)).getAsJsonArray();

            for (JsonElement element : users) {
                JsonObject u = element.getAsJsonObject();

                stmt.setString(1, u.get("student_no").getAsString());
                stmt.setString(2, u.get("name").getAsString());
                stmt.setString(3, u.get("password").getAsString());
                stmt.setString(4, u.get("role_level").getAsString());
                stmt.setString(5, u.get("created_at").getAsString());
                stmt.setString(6, u.get("status").getAsString());

                stmt.executeUpdate();
            }

            System.out.println("Users 匯入完成：" + users.size() + " 筆");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void importBooks() {
        String path = "assets/data/Books.json";

        String bookSql = """
            INSERT INTO books
            (title, authors, subjects, publisher, publish_year, edition, format_desc, source, note, status)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'AVAILABLE')
        """;

        String isbnSql = """
            INSERT INTO book_isbns (book_id, isbn)
            VALUES (?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement bookStmt = conn.prepareStatement(bookSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement isbnStmt = conn.prepareStatement(isbnSql)) {

            JsonArray books = JsonParser.parseReader(new FileReader(path)).getAsJsonArray();

            for (JsonElement element : books) {
                JsonObject b = element.getAsJsonObject();

                bookStmt.setString(1, b.get("題名").getAsString());
                bookStmt.setString(2, joinArray(b.getAsJsonArray("作者")));
                bookStmt.setString(3, joinArray(b.getAsJsonArray("主題")));
                bookStmt.setString(4, b.get("出版者").getAsString());
                bookStmt.setString(5, b.get("出版年").getAsString());
                bookStmt.setString(6, b.get("版本").getAsString());
                bookStmt.setString(7, b.get("格式").getAsString());
                bookStmt.setString(8, b.get("資料來源").getAsString());
                bookStmt.setString(9, b.get("附註").getAsString());

                bookStmt.executeUpdate();

                ResultSet keys = bookStmt.getGeneratedKeys();
                if (keys.next()) {
                    int bookId = keys.getInt(1);

                    JsonArray isbns = b.getAsJsonArray("識別號");
                    for (JsonElement isbn : isbns) {
                        isbnStmt.setInt(1, bookId);
                        isbnStmt.setString(2, isbn.getAsString());
                        isbnStmt.executeUpdate();
                    }
                }
            }

            System.out.println("Books 匯入完成：" + books.size() + " 筆");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void importBorrowRecords() {
        String path = "assets/data/Borrow_records.json";

        String sql = """
            INSERT INTO borrow_records
            (user_id, book_id, borrow_date, due_date, return_date, borrow_days, created_at)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        String updateBookSql = """
            UPDATE books SET status = 'BORROWED'
            WHERE book_id = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             PreparedStatement updateBookStmt = conn.prepareStatement(updateBookSql)) {

            JsonArray records = JsonParser.parseReader(new FileReader(path)).getAsJsonArray();

            for (JsonElement element : records) {
                JsonObject r = element.getAsJsonObject();

                int userId = r.get("user_id").getAsInt();
                int bookId = r.get("book_id").getAsInt();

                stmt.setInt(1, userId);
                stmt.setInt(2, bookId);
                stmt.setString(3, parseRelativeDate(r.get("borrow_date").getAsString()));
                stmt.setString(4, parseRelativeDate(r.get("due_date").getAsString()));

                if (r.get("return_date").isJsonNull()) {
                    stmt.setNull(5, Types.TIMESTAMP);
                } else {
                    stmt.setString(5, parseRelativeDate(r.get("return_date").getAsString()));
                }

                stmt.setInt(6, r.get("borrow_days").getAsInt());
                stmt.setString(7, parseRelativeDate(r.get("created_at").getAsString()));

                stmt.executeUpdate();

                if (r.get("return_date").isJsonNull()) {
                    updateBookStmt.setInt(1, bookId);
                    updateBookStmt.executeUpdate();
                }
            }

            System.out.println("Borrow_records 匯入完成：" + records.size() + " 筆");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String joinArray(JsonArray array) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < array.size(); i++) {
            sb.append(array.get(i).getAsString());
            if (i < array.size() - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

    private static String parseRelativeDate(String text) {
        text = text.trim();

        if (text.equals("0 days")) {
            return LocalDateTime.now().format(FORMATTER);
        }

        boolean isMinus = text.startsWith("-");
        int days = Integer.parseInt(text.replace("-", "").replace(" days", "").trim());

        LocalDateTime date = isMinus
                ? LocalDateTime.now().minusDays(days)
                : LocalDateTime.now().plusDays(days);

        return date.format(FORMATTER);
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
            System.out.println("預設管理者可能已存在，略過建立。");
        }
    }
}