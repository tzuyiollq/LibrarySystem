package dao;

import model.Book;
import java.sql.*;
import java.util.*;

public class BookDAO {

    public void searchBooks(String keyword) {
        for (Book b : searchBookList(keyword)) {
            System.out.println("書名：" + b.getTitle());
            System.out.println("作者：" + b.getAuthors());
            System.out.println("ISBN：" + b.getIsbns());
            System.out.println("狀態：" + b.getStatus());
            System.out.println("---------------------");
        }
    }

    public List<Book> searchBookList(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql = """
            SELECT b.*, GROUP_CONCAT(i.isbn SEPARATOR ', ') AS isbns
            FROM books b
            LEFT JOIN book_isbns i ON b.book_id = i.book_id
            WHERE b.title LIKE ? OR b.authors LIKE ? OR b.subjects LIKE ? OR b.publisher LIKE ?
               OR b.book_id IN (SELECT book_id FROM book_isbns WHERE isbn LIKE ?)
            GROUP BY b.book_id
        """;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            String s = "%" + keyword + "%";
            for (int i = 1; i <= 5; i++) stmt.setString(i, s);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book b = new Book(rs.getInt("book_id"), rs.getString("title"), rs.getString("authors"),
                        rs.getString("subjects"), rs.getString("publisher"), rs.getString("publish_year"),
                        rs.getString("edition"), rs.getString("format_desc"), rs.getString("source"),
                        rs.getString("note"), rs.getString("status"));
                b.setIsbns(rs.getString("isbns"));
                books.add(b);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return books;
    }

    public List<Book> getAllBooks() {
        return searchBookList("");
    }

    public boolean addBook(Book book, List<String> isbns) {
        String bookSql = """
            INSERT INTO books (title, authors, subjects, publisher, publish_year, edition, format_desc, source, note, status)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        String isbnSql = "INSERT INTO book_isbns (book_id, isbn) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement bookStmt = conn.prepareStatement(bookSql, Statement.RETURN_GENERATED_KEYS)) {
                bookStmt.setString(1, book.getTitle()); bookStmt.setString(2, book.getAuthors()); bookStmt.setString(3, book.getSubjects());
                bookStmt.setString(4, book.getPublisher()); bookStmt.setString(5, book.getPublishYear()); bookStmt.setString(6, book.getEdition());
                bookStmt.setString(7, book.getFormatDesc()); bookStmt.setString(8, book.getSource()); bookStmt.setString(9, book.getNote());
                bookStmt.setString(10, book.getStatus());
                bookStmt.executeUpdate();
                ResultSet keys = bookStmt.getGeneratedKeys();
                if (!keys.next()) return false;
                int bookId = keys.getInt(1);
                try (PreparedStatement isbnStmt = conn.prepareStatement(isbnSql)) {
                    for (String isbn : isbns) {
                        isbn = isbn.trim();
                        if (!isbn.isEmpty()) {
                            isbnStmt.setInt(1, bookId); isbnStmt.setString(2, isbn); isbnStmt.addBatch();
                        }
                    }
                    isbnStmt.executeBatch();
                }
            }
            conn.commit();
            return true;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean removeBook(int bookId) {
        String sql = "UPDATE books SET status = 'REMOVED' WHERE book_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
    
    public java.util.List<model.HotBook> getHotBooks() {

        java.util.List<model.HotBook> books = new java.util.ArrayList<>();

        String sql = """
            SELECT b.book_id,
                   b.title,
                   b.authors,
                   COUNT(br.record_id) AS borrow_count
            FROM books b
            JOIN borrow_records br ON b.book_id = br.book_id
            GROUP BY b.book_id, b.title, b.authors
            ORDER BY borrow_count DESC
            LIMIT 10
        """;

        try (
            java.sql.Connection conn = DBConnection.getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
            java.sql.ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                books.add(new model.HotBook(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("authors"),
                        rs.getInt("borrow_count")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }
}
