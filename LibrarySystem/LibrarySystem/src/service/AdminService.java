package service;

import java.util.List;

import dao.BookDAO;
import dao.BorrowDAO;
import dao.UserDAO;
import model.Book;
import model.BorrowRecord;
import model.User;

public class AdminService {

    private UserDAO userDAO = new UserDAO();
    private BookDAO bookDAO = new BookDAO();
    private BorrowDAO borrowDAO = new BorrowDAO();

    // 使用者管理

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean suspendUser(String studentNo) {
        return userDAO.suspendUser(studentNo);
    }

    public boolean activateUser(String studentNo) {
        return userDAO.activateUser(studentNo);
    }

    // 借閱紀錄管理

    public List<BorrowRecord> getAllRecords() {
        return borrowDAO.getAllRecords();
    }

    public List<BorrowRecord> getRecordsByStudentNo(String studentNo) {
        return borrowDAO.getRecordsByStudentNo(studentNo);
    }

    // 書籍管理

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public boolean addBook(Book book, List<String> isbns) {
        return bookDAO.addBook(book, isbns);
    }

    public boolean removeBook(int bookId) {
        return bookDAO.removeBook(bookId);
    }
}