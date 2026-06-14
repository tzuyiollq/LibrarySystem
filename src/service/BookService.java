package service;

import dao.BookDAO;
import model.Book;
import java.util.List;

public class BookService {
    private BookDAO bookDAO = new BookDAO();
    public void searchBooks(String keyword) { bookDAO.searchBooks(keyword); }
    public void searchByISBN(String isbn) { bookDAO.searchBooks(isbn); }
    public List<Book> searchBookList(String keyword) { return bookDAO.searchBookList(keyword); }
    public List<Book> getAllBooks() { return bookDAO.getAllBooks(); }
    public boolean addBook(Book book, List<String> isbns) { return bookDAO.addBook(book, isbns); }
    public boolean removeBook(int bookId) { return bookDAO.removeBook(bookId); }
    public java.util.List<model.HotBook> getHotBooks() { return bookDAO.getHotBooks(); }
}
