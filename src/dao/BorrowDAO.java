package dao;

import model.BorrowRecord;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class BorrowDAO {

    public boolean borrowBook(int userId, int bookId, int days) {

        String checkSql =
                "SELECT status FROM books WHERE book_id = ?";

        String insertSql = """
            INSERT INTO borrow_records
            (user_id, book_id, borrow_date, due_date, borrow_days)
            VALUES (?, ?, ?, ?, ?)
        """;

        String updateSql =
                "UPDATE books SET status = 'BORROWED' WHERE book_id = ?";

        try (Connection conn = DBConnection.getConnection()) {

            try (PreparedStatement checkStmt =
                         conn.prepareStatement(checkSql)) {

                checkStmt.setInt(1, bookId);

                ResultSet rs = checkStmt.executeQuery();

                if (!rs.next()) {
                    System.out.println("找不到此書！");
                    return false;
                }

                if (!"AVAILABLE".equals(rs.getString("status"))) {
                    System.out.println("此書目前無法借閱！");
                    return false;
                }
            }

            LocalDateTime borrowDate = LocalDateTime.now();
            LocalDateTime dueDate = borrowDate.plusDays(days);

            try (PreparedStatement stmt =
                         conn.prepareStatement(insertSql)) {

                stmt.setInt(1, userId);
                stmt.setInt(2, bookId);
                stmt.setTimestamp(3, Timestamp.valueOf(borrowDate));
                stmt.setTimestamp(4, Timestamp.valueOf(dueDate));
                stmt.setInt(5, days);

                stmt.executeUpdate();
            }

            try (PreparedStatement stmt =
                         conn.prepareStatement(updateSql)) {

                stmt.setInt(1, bookId);
                stmt.executeUpdate();
            }

            System.out.println("借書成功！");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean returnBook(int recordId) {

        String getSql = """
            SELECT book_id
            FROM borrow_records
            WHERE record_id = ?
              AND return_date IS NULL
        """;

        String returnSql =
                "UPDATE borrow_records SET return_date = ? WHERE record_id = ?";

        String updateSql =
                "UPDATE books SET status = 'AVAILABLE' WHERE book_id = ?";

        try (Connection conn = DBConnection.getConnection()) {

            int bookId;

            try (PreparedStatement stmt =
                         conn.prepareStatement(getSql)) {

                stmt.setInt(1, recordId);

                ResultSet rs = stmt.executeQuery();

                if (!rs.next()) {
                    System.out.println("找不到借閱紀錄或已歸還！");
                    return false;
                }

                bookId = rs.getInt("book_id");
            }

            try (PreparedStatement stmt =
                         conn.prepareStatement(returnSql)) {

                stmt.setTimestamp(
                        1,
                        Timestamp.valueOf(LocalDateTime.now())
                );
                stmt.setInt(2, recordId);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt =
                         conn.prepareStatement(updateSql)) {

                stmt.setInt(1, bookId);
                stmt.executeUpdate();
            }

            System.out.println("還書成功！");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean returnBook(int userId, int bookId) {

        String getSql = """
            SELECT record_id
            FROM borrow_records
            WHERE user_id = ?
              AND book_id = ?
              AND return_date IS NULL
            ORDER BY borrow_date DESC
            LIMIT 1
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(getSql)
        ) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int recordId = rs.getInt("record_id");
                return returnBook(recordId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public int countCurrentBorrowedBooks(int userId) {

        String sql = """
            SELECT COUNT(*)
            FROM borrow_records
            WHERE user_id = ?
              AND return_date IS NULL
        """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<BorrowRecord> getUserBorrowRecords(int userId) {

        List<BorrowRecord> records = new ArrayList<>();

        String sql = """
            SELECT br.record_id,
                   b.title,
                   br.borrow_date,
                   br.due_date,
                   br.return_date,
                   br.borrow_days
            FROM borrow_records br
            JOIN books b ON br.book_id = b.book_id
            WHERE br.user_id = ?
            ORDER BY br.borrow_date DESC
        """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                records.add(new BorrowRecord(
                        rs.getInt("record_id"),
                        "",
                        "",
                        rs.getString("title"),
                        rs.getString("borrow_date"),
                        rs.getString("due_date"),
                        rs.getString("return_date"),
                        rs.getInt("borrow_days")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return records;
    }

    public void showUserBorrowRecords(int userId) {
        for (BorrowRecord r : getUserBorrowRecords(userId)) {
            System.out.println(r.getBookTitle());
        }
    }

    public void showBookBorrowRecords(int bookId) {
        String sql = """
            SELECT br.record_id,
                   u.student_no,
                   u.name,
                   b.title,
                   br.borrow_date,
                   br.due_date,
                   br.return_date,
                   br.borrow_days
            FROM borrow_records br
            JOIN users u ON br.user_id = u.user_id
            JOIN books b ON br.book_id = b.book_id
            WHERE br.book_id = ?
            ORDER BY br.borrow_date DESC
        """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, bookId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getString("name") + " - " + rs.getString("title")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showOverdueBooks() {
        for (BorrowRecord r : getAllRecords()) {
            if (r.getReturnDate() == null) {
                System.out.println(r.getBookTitle());
            }
        }
    }

    public List<BorrowRecord> getAllRecords() {
        return queryRecords("""
            SELECT br.record_id,
                   u.student_no,
                   u.name,
                   b.title,
                   br.borrow_date,
                   br.due_date,
                   br.return_date,
                   br.borrow_days
            FROM borrow_records br
            JOIN users u ON br.user_id = u.user_id
            JOIN books b ON br.book_id = b.book_id
            ORDER BY br.record_id DESC
        """, null);
    }

    public List<BorrowRecord> getRecordsByStudentNo(String studentNo) {
        return queryRecords("""
            SELECT br.record_id,
                   u.student_no,
                   u.name,
                   b.title,
                   br.borrow_date,
                   br.due_date,
                   br.return_date,
                   br.borrow_days
            FROM borrow_records br
            JOIN users u ON br.user_id = u.user_id
            JOIN books b ON br.book_id = b.book_id
            WHERE u.student_no = ?
            ORDER BY br.record_id DESC
        """, studentNo);
    }

    private List<BorrowRecord> queryRecords(String sql, String studentNo) {

        List<BorrowRecord> records = new ArrayList<>();

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            if (studentNo != null) {
                stmt.setString(1, studentNo);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                records.add(new BorrowRecord(
                        rs.getInt("record_id"),
                        rs.getString("student_no"),
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getString("borrow_date"),
                        rs.getString("due_date"),
                        rs.getString("return_date"),
                        rs.getInt("borrow_days")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return records;
    }
    public boolean returnBookByUserAndBook(int userId, int bookId) {

        String sql = """
            SELECT record_id
            FROM borrow_records
            WHERE user_id = ?
              AND book_id = ?
              AND return_date IS NULL
            ORDER BY borrow_date DESC
            LIMIT 1
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int recordId = rs.getInt("record_id");

                return returnBookWithCredit(
                        recordId,
                        userId,
                        bookId
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean bookExists(int bookId) {

        String sql = """
            SELECT COUNT(*)
            FROM books
            WHERE book_id = ?
        """;

        try (
                java.sql.Connection conn = DBConnection.getConnection();
                java.sql.PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, bookId);

            java.sql.ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isBookAvailable(int bookId) {

        String sql = """
            SELECT status
            FROM books
            WHERE book_id = ?
        """;

        try (
                java.sql.Connection conn = DBConnection.getConnection();
                java.sql.PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, bookId);

            java.sql.ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return "AVAILABLE".equals(rs.getString("status"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    private int getMaxCredit(Connection conn, int userId) throws Exception {

        String sql = """
            SELECT role_level
            FROM users
            WHERE user_id = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return "VIP".equals(rs.getString("role_level")) ? 150 : 100;
            }
        }

        return 100;
    }
    private boolean returnBookWithCredit(
            int recordId,
            int userId,
            int bookId
    ) {

        String getRecordSql = """
            SELECT DATEDIFF(NOW(), due_date) AS overdue_days
            FROM borrow_records
            WHERE record_id = ?
        """;

        String getUserSql = """
            SELECT credit_score, role_level
            FROM users
            WHERE user_id = ?
        """;

        String returnSql = """
            UPDATE borrow_records
            SET return_date = NOW()
            WHERE record_id = ?
        """;

        String bookSql = """
            UPDATE books
            SET status = 'AVAILABLE'
            WHERE book_id = ?
        """;

        String updateUserSql = """
            UPDATE users
            SET credit_score = ?,
                status = ?
            WHERE user_id = ?
        """;

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            int overdueDays = 0;

            try (PreparedStatement stmt = conn.prepareStatement(getRecordSql)) {
                stmt.setInt(1, recordId);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    overdueDays = rs.getInt("overdue_days");
                }
            }

            int creditScore = 100;
            String roleLevel = "NORMAL";

            try (PreparedStatement stmt = conn.prepareStatement(getUserSql)) {
                stmt.setInt(1, userId);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    creditScore = rs.getInt("credit_score");
                    roleLevel = rs.getString("role_level");
                }
            }

            int maxCredit =
                    "VIP".equals(roleLevel) ? 150 : 100;

            int newCredit;

            if (overdueDays > 0) {
                newCredit = creditScore - overdueDays * 10;
            } else {
                newCredit = creditScore + 5;
            }

            if (newCredit > maxCredit) {
                newCredit = maxCredit;
            }

            if (newCredit < 0) {
                newCredit = 0;
            }

            String newStatus =
                    newCredit <= 0 ? "SUSPENDED" : "ACTIVE";

            try (PreparedStatement stmt = conn.prepareStatement(returnSql)) {
                stmt.setInt(1, recordId);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(bookSql)) {
                stmt.setInt(1, bookId);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(updateUserSql)) {
                stmt.setInt(1, newCredit);
                stmt.setString(2, newStatus);
                stmt.setInt(3, userId);
                stmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public int countUserTotalBorrowRecords(int userId) {

        String sql = """
            SELECT COUNT(*)
            FROM borrow_records
            WHERE user_id = ?
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}