package service;

import dao.BookDAO;
import dao.BorrowDAO;
import dao.UserDAO;
import model.Book;
import model.BorrowRecord;
import model.User;
import java.util.List;

public class AdminService {
    private UserDAO userDAO = new UserDAO();
    private BookDAO bookDAO = new BookDAO();
    private BorrowDAO borrowDAO = new BorrowDAO();

    public List<User> getAllUsers() { return userDAO.getAllUsers(); }
    public boolean suspendUser(String studentNo) { return userDAO.suspendUser(studentNo); }
    public boolean activateUser(String studentNo) { return userDAO.activateUser(studentNo); }
    public List<BorrowRecord> getAllRecords() { return borrowDAO.getAllRecords(); }
    public List<BorrowRecord> getRecordsByStudentNo(String studentNo) { return borrowDAO.getRecordsByStudentNo(studentNo); }
    public List<Book> getAllBooks() { return bookDAO.getAllBooks(); }
    public boolean addBook(Book book, List<String> isbns) { return bookDAO.addBook(book, isbns); }
    public boolean removeBook(int bookId) { return bookDAO.removeBook(bookId); }
}
