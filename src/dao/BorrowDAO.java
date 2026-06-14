package dao;

import model.BorrowRecord;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class BorrowDAO {

    public void borrowBook(int userId, int bookId, int days) {

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
                    return;
                }

                if (!"AVAILABLE".equals(rs.getString("status"))) {
                    System.out.println("此書目前無法借閱！");
                    return;
                }
            }

            LocalDateTime borrowDate =
                    LocalDateTime.now();

            LocalDateTime dueDate =
                    borrowDate.plusDays(days);

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean returnBook(int userId, int bookId) {

        String findSql = """
            SELECT record_id
            FROM borrow_records
            WHERE user_id = ?
              AND book_id = ?
              AND return_date IS NULL
            ORDER BY borrow_date DESC
            LIMIT 1
        """;

        String returnSql = """
            UPDATE borrow_records
            SET return_date = NOW()
            WHERE record_id = ?
        """;

        String updateBookSql = """
            UPDATE books
            SET status = 'AVAILABLE'
            WHERE book_id = ?
        """;

        try (Connection conn = DBConnection.getConnection()) {

            int recordId = -1;

            try (PreparedStatement stmt = conn.prepareStatement(findSql)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, bookId);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    recordId = rs.getInt("record_id");
                } else {
                    return false;
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(returnSql)) {
                stmt.setInt(1, recordId);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(updateBookSql)) {
                stmt.setInt(1, bookId);
                stmt.executeUpdate();
            }

            return true;

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
            Connection conn =
                    DBConnection.getConnection();

            PreparedStatement stmt =
                    conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, userId);

            ResultSet rs =
                    stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<BorrowRecord> getUserBorrowRecords(int userId) {

        List<BorrowRecord> records =
                new ArrayList<>();

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
            Connection conn =
                    DBConnection.getConnection();

            PreparedStatement stmt =
                    conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, userId);

            ResultSet rs =
                    stmt.executeQuery();

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
            Connection conn =
                    DBConnection.getConnection();

            PreparedStatement stmt =
                    conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, bookId);

            ResultSet rs =
                    stmt.executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getString("name")
                                + " - "
                                + rs.getString("title")
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

    public List<BorrowRecord> getRecordsByStudentNo(
            String studentNo
    ) {

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

    private List<BorrowRecord> queryRecords(
            String sql,
            String studentNo
    ) {

        List<BorrowRecord> records =
                new ArrayList<>();

        try (
            Connection conn =
                    DBConnection.getConnection();

            PreparedStatement stmt =
                    conn.prepareStatement(sql)
        ) {

            if (studentNo != null) {
                stmt.setString(1, studentNo);
            }

            ResultSet rs =
                    stmt.executeQuery();

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
}