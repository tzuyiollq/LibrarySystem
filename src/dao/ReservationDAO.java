package dao;

import java.sql.*;
import java.util.*;
import model.Reservation;

public class ReservationDAO {

    public boolean reserveBook(int userId, int bookId) {

        String checkBookSql = "SELECT status FROM books WHERE book_id = ?";
        String checkDuplicateSql = """
            SELECT COUNT(*)
            FROM reservations
            WHERE user_id = ?
              AND book_id = ?
              AND status = 'WAITING'
        """;

        String insertSql = """
            INSERT INTO reservations (user_id, book_id)
            VALUES (?, ?)
        """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement checkBookStmt = conn.prepareStatement(checkBookSql);
            PreparedStatement checkDupStmt = conn.prepareStatement(checkDuplicateSql);
            PreparedStatement insertStmt = conn.prepareStatement(insertSql)
        ) {

            checkBookStmt.setInt(1, bookId);
            ResultSet bookRs = checkBookStmt.executeQuery();

            if (!bookRs.next()) {
                System.out.println("找不到此書！");
                return false;
            }

            String status = bookRs.getString("status");

            if (!status.equals("BORROWED")) {
                System.out.println("只有已借出的書才能預約！");
                return false;
            }

            checkDupStmt.setInt(1, userId);
            checkDupStmt.setInt(2, bookId);

            ResultSet dupRs = checkDupStmt.executeQuery();

            if (dupRs.next() && dupRs.getInt(1) > 0) {
                System.out.println("你已經預約過這本書！");
                return false;
            }

            insertStmt.setInt(1, userId);
            insertStmt.setInt(2, bookId);

            insertStmt.executeUpdate();

            System.out.println("預約成功！");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Reservation> getUserReservations(int userId) {

        List<Reservation> reservations = new ArrayList<>();

        String sql = """
            SELECT r.reservation_id,
                   r.user_id,
                   r.book_id,
                   b.title,
                   r.reserved_at,
                   r.status
            FROM reservations r
            JOIN books b ON r.book_id = b.book_id
            WHERE r.user_id = ?
            ORDER BY r.reserved_at DESC
        """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reservations.add(new Reservation(
                        rs.getInt("reservation_id"),
                        rs.getInt("user_id"),
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("reserved_at"),
                        rs.getString("status")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reservations;
    }

    public boolean cancelReservation(int reservationId, int userId) {

        String sql = """
            UPDATE reservations
            SET status = 'CANCELLED'
            WHERE reservation_id = ?
              AND user_id = ?
              AND status = 'WAITING'
        """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, reservationId);
            stmt.setInt(2, userId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public int countWaitingReservations(int userId) {

        String sql = """
            SELECT COUNT(*)
            FROM reservations
            WHERE user_id = ?
              AND status = 'WAITING'
        """;

        try (
            java.sql.Connection conn = DBConnection.getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, userId);

            java.sql.ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}