package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Book;
import java.sql.*;
import java.util.*;

public class BookDAO {

    public void searchBooks(String keyword) {

    	String sql = """
    		    SELECT b.*, GROUP_CONCAT(all_i.isbn SEPARATOR ', ') AS isbns
    		    FROM books b
    		    LEFT JOIN book_isbns all_i ON b.book_id = all_i.book_id
    		    WHERE b.title LIKE ?
    		       OR b.authors LIKE ?
    		       OR b.subjects LIKE ?
    		       OR b.publisher LIKE ?
    		       OR b.book_id IN (
    		            SELECT book_id
    		            FROM book_isbns
    		            WHERE isbn LIKE ?
    		       )
    		    GROUP BY b.book_id
    		""";
    	try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            String search = "%" + keyword + "%";

            stmt.setString(1, search);
            stmt.setString(2, search);
            stmt.setString(3, search);
            stmt.setString(4, search);
            stmt.setString(5, search);
            ResultSet rs = stmt.executeQuery();

            System.out.println("====== 查詢結果 ======");

            while (rs.next()) {

            	    System.out.println("題名：" + rs.getString("title"));
            	    System.out.println("作者：" + rs.getString("authors"));
            	    System.out.println("主題：" + rs.getString("subjects"));
            	    System.out.println("出版者：" + rs.getString("publisher"));
            	    System.out.println("出版年：" + rs.getString("publish_year"));
            	    System.out.println("版本：" + rs.getString("edition"));
            	    System.out.println("格式：" + rs.getString("format_desc"));
            	    System.out.println("資料來源：" + rs.getString("source"));
            	    System.out.println("識別號：" + rs.getString("isbns"));
            	    System.out.println("附註：" + rs.getString("note"));
            	    System.out.println("狀態：" + rs.getString("status"));

            	    System.out.println("-----------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //管理者介面
    // 查詢所有書籍
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                books.add(new Book(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("authors"),
                    rs.getString("subjects"),
                    rs.getString("publisher"),
                    rs.getString("publish_year"),
                    rs.getString("edition"),
                    rs.getString("format_desc"),
                    rs.getString("source"),
                    rs.getString("note"),
                    rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // 新增書籍
    public boolean addBook(Book book, List<String> isbns) {
        String bookSql = """
            INSERT INTO books
            (title, authors, subjects, publisher, publish_year, edition, format_desc, source, note, status)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'AVAILABLE')
        """;

        String isbnSql = "INSERT INTO book_isbns (book_id, isbn) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement bookStmt = conn.prepareStatement(bookSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement isbnStmt = conn.prepareStatement(isbnSql)) {

            bookStmt.setString(1, book.getTitle());
            bookStmt.setString(2, book.getAuthors());
            bookStmt.setString(3, book.getSubjects());
            bookStmt.setString(4, book.getPublisher());
            bookStmt.setString(5, book.getPublishYear());
            bookStmt.setString(6, book.getEdition());
            bookStmt.setString(7, book.getFormatDesc());
            bookStmt.setString(8, book.getSource());
            bookStmt.setString(9, book.getNote());
            bookStmt.executeUpdate();

            ResultSet keys = bookStmt.getGeneratedKeys();
            if (keys.next()) {
                int bookId = keys.getInt(1);
                for (String isbn : isbns) {
                    isbnStmt.setInt(1, bookId);
                    isbnStmt.setString(2, isbn);
                    isbnStmt.executeUpdate();
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 下架書籍（直接刪除）
    public boolean removeBook(int bookId) {
        String deleteIsbnSql = "DELETE FROM book_isbns WHERE book_id = ?";
        String deleteRecordSql = "DELETE FROM borrow_records WHERE book_id = ?";
        String deleteBookSql = "DELETE FROM books WHERE book_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement isbnStmt = conn.prepareStatement(deleteIsbnSql);
             PreparedStatement recordStmt = conn.prepareStatement(deleteRecordSql);
             PreparedStatement bookStmt = conn.prepareStatement(deleteBookSql)) {

            // 先刪 book_isbns
            isbnStmt.setInt(1, bookId);
            isbnStmt.executeUpdate();

            // 再刪 borrow_records
            recordStmt.setInt(1, bookId);
            recordStmt.executeUpdate();

            // 最後刪書
            bookStmt.setInt(1, bookId);
            bookStmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public java.util.List<model.Book> searchBookList(String keyword) {

        java.util.List<model.Book> books = new java.util.ArrayList<>();

        String sql = """
            SELECT b.*,
                   GROUP_CONCAT(all_i.isbn SEPARATOR ', ') AS isbns
            FROM books b
            LEFT JOIN book_isbns all_i ON b.book_id = all_i.book_id
            WHERE b.title LIKE ?
               OR b.authors LIKE ?
               OR b.subjects LIKE ?
               OR b.publisher LIKE ?
               OR b.book_id IN (
                    SELECT book_id
                    FROM book_isbns
                    WHERE isbn LIKE ?
               )
            GROUP BY b.book_id
        """;

        try (
            java.sql.Connection conn = DBConnection.getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            String search = "%" + keyword + "%";

            stmt.setString(1, search);
            stmt.setString(2, search);
            stmt.setString(3, search);
            stmt.setString(4, search);
            stmt.setString(5, search);

            java.sql.ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                model.Book book = new model.Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("authors"),
                        rs.getString("subjects"),
                        rs.getString("publisher"),
                        rs.getString("publish_year"),
                        rs.getString("edition"),
                        rs.getString("format_desc"),
                        rs.getString("source"),
                        rs.getString("note"),
                        rs.getString("status")
                );

                book.setIsbns(rs.getString("isbns"));

                books.add(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }
}