package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReminderDAO {

    public void showDueSoonReminders(int userId) {

        String sql = """
            SELECT br.record_id, b.title, br.borrow_date, br.due_date
            FROM borrow_records br
            JOIN books b ON br.book_id = b.book_id
            WHERE br.user_id = ?
              AND br.return_date IS NULL
              AND br.due_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 3 DAY)
            ORDER BY br.due_date ASC
        """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            System.out.println("====== 到期提醒 ======");

            boolean hasReminder = false;

            while (rs.next()) {
                hasReminder = true;

                System.out.println("紀錄ID：" + rs.getInt("record_id"));
                System.out.println("書名：" + rs.getString("title"));
                System.out.println("借出時間：" + rs.getString("borrow_date"));
                System.out.println("到期時間：" + rs.getString("due_date"));
                System.out.println("-----------------------------");
            }

            if (!hasReminder) {
                System.out.println("目前沒有 3 天內到期的書籍。");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}