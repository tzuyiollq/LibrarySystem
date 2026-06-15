package dao;

import model.BookReview;
import java.sql.*;
import java.util.*;

public class BookReviewDAO {

    public boolean addReview(int userId, int bookId, int rating, String comment) {

        String sql = """
            INSERT INTO book_reviews
            (user_id, book_id, rating, comment)
            VALUES (?, ?, ?, ?)
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            stmt.setInt(3, rating);
            stmt.setString(4, comment);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<BookReview> getAllReviews() {

        List<BookReview> reviews = new ArrayList<>();

        String sql = """
            SELECT r.review_id,
                   r.user_id,
                   r.book_id,
                   u.name,
                   b.title,
                   r.rating,
                   r.comment,
                   r.created_at
            FROM book_reviews r
            JOIN users u ON r.user_id = u.user_id
            JOIN books b ON r.book_id = b.book_id
            ORDER BY r.created_at DESC
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                reviews.add(new BookReview(
                        rs.getInt("review_id"),
                        rs.getInt("user_id"),
                        rs.getInt("book_id"),
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getString("created_at")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reviews;
    }

    public List<BookReview> getReviewsByBookId(int bookId) {

        List<BookReview> reviews = new ArrayList<>();

        String sql = """
            SELECT r.review_id,
                   r.user_id,
                   r.book_id,
                   u.name,
                   b.title,
                   r.rating,
                   r.comment,
                   r.created_at
            FROM book_reviews r
            JOIN users u ON r.user_id = u.user_id
            JOIN books b ON r.book_id = b.book_id
            WHERE r.book_id = ?
            ORDER BY r.created_at DESC
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, bookId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reviews.add(new BookReview(
                        rs.getInt("review_id"),
                        rs.getInt("user_id"),
                        rs.getInt("book_id"),
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getString("created_at")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reviews;
    }

    public boolean deleteReviewByUser(int reviewId, int userId) {

        String sql = """
            DELETE FROM book_reviews
            WHERE review_id = ?
              AND user_id = ?
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, reviewId);
            stmt.setInt(2, userId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteReviewByAdmin(int reviewId) {

        String sql = """
            DELETE FROM book_reviews
            WHERE review_id = ?
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, reviewId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public double getAverageRating(int bookId) {

        String sql = """
            SELECT AVG(rating)
            FROM book_reviews
            WHERE book_id = ?
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, bookId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}