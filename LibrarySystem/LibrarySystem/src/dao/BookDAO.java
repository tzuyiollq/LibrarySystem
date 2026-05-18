package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}