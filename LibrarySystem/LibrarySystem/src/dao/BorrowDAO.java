package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

public class BorrowDAO {

    public void borrowBook(int userId, int bookId, int days) {

        String checkSql = """
            SELECT status
            FROM books
            WHERE book_id = ?
        """;

        String insertSql = """
            INSERT INTO borrow_records
            (user_id, book_id, borrow_date, due_date, borrow_days)
            VALUES (?, ?, ?, ?, ?)
        """;

        String updateSql = """
            UPDATE books
            SET status = 'BORROWED'
            WHERE book_id = ?
        """;

        try (
            Connection conn = DBConnection.getConnection();

            PreparedStatement checkStmt =
                conn.prepareStatement(checkSql);

            PreparedStatement insertStmt =
                conn.prepareStatement(insertSql);

            PreparedStatement updateStmt =
                conn.prepareStatement(updateSql);

        ) {

            // 檢查書籍狀態
            checkStmt.setInt(1, bookId);

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {

                String status = rs.getString("status");

                if (!status.equals("AVAILABLE")) {

                    System.out.println("此書目前無法借閱！");
                    return;
                }
            } else {

                System.out.println("找不到此書！");
                return;
            }

            LocalDateTime borrowDate = LocalDateTime.now();
            LocalDateTime dueDate = borrowDate.plusDays(days);

            // 新增借閱紀錄
            insertStmt.setInt(1, userId);
            insertStmt.setInt(2, bookId);
            insertStmt.setString(3, borrowDate.toString());
            insertStmt.setString(4, dueDate.toString());
            insertStmt.setInt(5, days);

            insertStmt.executeUpdate();

            // 更新書籍狀態
            updateStmt.setInt(1, bookId);

            updateStmt.executeUpdate();

            System.out.println("借書成功！");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    public void returnBook(int recordId) {

        String getBookSql = """
            SELECT book_id
            FROM borrow_records
            WHERE record_id = ?
              AND return_date IS NULL
        """;

        String returnSql = """
            UPDATE borrow_records
            SET return_date = ?
            WHERE record_id = ?
        """;

        String updateBookSql = """
            UPDATE books
            SET status = 'AVAILABLE'
            WHERE book_id = ?
        """;

        try (

            Connection conn = DBConnection.getConnection();

            PreparedStatement getStmt =
                conn.prepareStatement(getBookSql);

            PreparedStatement returnStmt =
                conn.prepareStatement(returnSql);

            PreparedStatement updateStmt =
                conn.prepareStatement(updateBookSql);

        ) {

            // 找 book_id
            getStmt.setInt(1, recordId);

            ResultSet rs = getStmt.executeQuery();

            if (!rs.next()) {

                System.out.println("找不到借閱紀錄或已歸還！");
                return;
            }

            int bookId = rs.getInt("book_id");

            // 更新 return_date
            returnStmt.setString(
                1,
                LocalDateTime.now().toString()
            );

            returnStmt.setInt(2, recordId);

            returnStmt.executeUpdate();

            // 更新書籍狀態
            updateStmt.setInt(1, bookId);

            updateStmt.executeUpdate();

            System.out.println("還書成功！");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
    public void showUserBorrowRecords(int userId) {

        String sql = """
            SELECT br.record_id, b.title, br.borrow_date, br.due_date,
                   br.return_date,
                   CASE
                       WHEN br.return_date IS NULL AND br.due_date < NOW() THEN '已逾期'
                       WHEN br.return_date IS NOT NULL AND br.return_date > br.due_date THEN '曾逾期'
                       ELSE '未逾期'
                   END AS overdue_status
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

            System.out.println("====== 個人借還紀錄 ======");

            boolean hasRecord = false;

            while (rs.next()) {
                hasRecord = true;

                System.out.println("紀錄ID：" + rs.getInt("record_id"));
                System.out.println("書名：" + rs.getString("title"));
                System.out.println("借出時間：" + rs.getString("borrow_date"));
                System.out.println("到期時間：" + rs.getString("due_date"));
                System.out.println("歸還時間：" + rs.getString("return_date"));
                System.out.println("狀態：" + rs.getString("overdue_status"));
                System.out.println("-----------------------------");
            }

            if (!hasRecord) {
                System.out.println("沒有借還紀錄。");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showBookBorrowRecords(int bookId) {

        String sql = """
            SELECT br.record_id,
                   u.name,
                   b.title,
                   br.borrow_date,
                   br.due_date,
                   br.return_date
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

            System.out.println("====== 書籍借還紀錄 ======");

            boolean hasRecord = false;

            while (rs.next()) {

                hasRecord = true;

                System.out.println("紀錄ID：" + rs.getInt("record_id"));
                System.out.println("借閱者：" + rs.getString("name"));
                System.out.println("書名：" + rs.getString("title"));
                System.out.println("借出時間：" + rs.getString("borrow_date"));
                System.out.println("到期時間：" + rs.getString("due_date"));
                System.out.println("歸還時間：" + rs.getString("return_date"));

                System.out.println("----------------------------");
            }

            if (!hasRecord) {
                System.out.println("查無借還紀錄");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showOverdueBooks() {

        String sql = """
            SELECT br.record_id, u.name, b.title, br.borrow_date, br.due_date
            FROM borrow_records br
            JOIN users u ON br.user_id = u.user_id
            JOIN books b ON br.book_id = b.book_id
            WHERE br.return_date IS NULL
              AND br.due_date < NOW()
            ORDER BY br.due_date ASC
        """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            ResultSet rs = stmt.executeQuery();

            System.out.println("===== 逾期名單 =====");

            boolean hasData = false;

            while (rs.next()) {
                hasData = true;

                System.out.println("紀錄ID：" + rs.getInt("record_id"));
                System.out.println("借閱者：" + rs.getString("name"));
                System.out.println("書名：" + rs.getString("title"));
                System.out.println("借出時間：" + rs.getString("borrow_date"));
                System.out.println("到期時間：" + rs.getString("due_date"));
                System.out.println("-------------------------");
            }

            if (!hasData) {
                System.out.println("目前沒有逾期書籍。");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}