package dao;

import model.BorrowRecord;
import java.sql.*;
import java.util.*;

public class ReminderDAO {
    public void showDueSoonReminders(int userId) { for (BorrowRecord r : getDueSoonBooks(userId)) System.out.println(r.getBookTitle()); }

    public List<BorrowRecord> getDueSoonBooks(int userId) {
        List<BorrowRecord> records = new ArrayList<>();
        String sql = """
            SELECT br.record_id, b.title, br.borrow_date, br.due_date
            FROM borrow_records br JOIN books b ON br.book_id = b.book_id
            WHERE br.user_id = ? AND br.return_date IS NULL
              AND br.due_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 3 DAY)
            ORDER BY br.due_date ASC
        """;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) records.add(new BorrowRecord(rs.getInt("record_id"), "", "", rs.getString("title"), rs.getString("borrow_date"), rs.getString("due_date"), null, 0));
        } catch (Exception e) { e.printStackTrace(); }
        return records;
    }

    public List<BorrowRecord> getOverdueRecords(int userId) {
        List<BorrowRecord> records = new ArrayList<>();
        String sql = """
            SELECT br.record_id, b.title, br.borrow_date, br.due_date, DATEDIFF(NOW(), br.due_date) AS overdue_days
            FROM borrow_records br JOIN books b ON br.book_id = b.book_id
            WHERE br.user_id = ? AND br.return_date IS NULL AND br.due_date < NOW()
            ORDER BY br.due_date ASC
        """;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) records.add(new BorrowRecord(rs.getInt("record_id"), "", "", rs.getString("title"), rs.getString("borrow_date"), rs.getString("due_date"), null, rs.getInt("overdue_days")));
        } catch (Exception e) { e.printStackTrace(); }
        return records;
    }
    public void suspendLongOverdueUsers() {
        String sql = """
            UPDATE users
            SET status = 'SUSPENDED'
            WHERE user_id IN (
                SELECT user_id
                FROM borrow_records
                WHERE return_date IS NULL
                  AND DATEDIFF(NOW(), due_date) >= 7
            )
        """;
        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            int count = stmt.executeUpdate();
            System.out.println("逾期停權完成，共停權 " + count + " 位使用者。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
