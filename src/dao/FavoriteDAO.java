package dao;

import model.FavoriteBook;
import java.sql.*;
import java.util.*;

public class FavoriteDAO {

    public boolean addFavorite(int userId, int bookId) {

        String sql = """
            INSERT INTO favorite_books (user_id, book_id)
            VALUES (?, ?)
        """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);

            return stmt.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("已收藏過此書");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean removeFavorite(int favoriteId, int userId) {

        String sql = """
            DELETE FROM favorite_books
            WHERE favorite_id = ?
              AND user_id = ?
        """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, favoriteId);
            stmt.setInt(2, userId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<FavoriteBook> getUserFavorites(int userId) {

        List<FavoriteBook> favorites = new ArrayList<>();

        String sql = """
            SELECT f.favorite_id,
                   b.book_id,
                   b.title,
                   b.authors,
                   f.created_at
            FROM favorite_books f
            JOIN books b ON f.book_id = b.book_id
            WHERE f.user_id = ?
            ORDER BY f.created_at DESC
        """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                favorites.add(new FavoriteBook(
                        rs.getInt("favorite_id"),
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("authors"),
                        rs.getString("created_at")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return favorites;
    }
}