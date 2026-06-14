package service;

import dao.BorrowDAO;
import model.BorrowRecord;
import model.User;
import java.util.List;

public class BorrowService {
    private BorrowDAO borrowDAO = new BorrowDAO();

    public void borrowBook(User user, int bookId, int days) {
        if (user == null) return;
        if ("NORMAL".equals(user.getRoleLevel()) && days > 7) return;
        if ("VIP".equals(user.getRoleLevel()) && days > 14) return;

        int current = borrowDAO.countCurrentBorrowedBooks(user.getUserId());
        int max = "VIP".equals(user.getRoleLevel()) ? 6 : 3;
        if (current >= max) return;

        borrowDAO.borrowBook(user.getUserId(), bookId, days);
    }

    public boolean returnBook(User user, int bookId) {

        if (user == null) {
            return false;
        }

        return borrowDAO.returnBook(user.getUserId(), bookId);
    }
    public void showUserBorrowRecords(int userId) { borrowDAO.showUserBorrowRecords(userId); }
    public void showBookBorrowRecords(int bookId) { borrowDAO.showBookBorrowRecords(bookId); }
    public void showOverdueBooks() { borrowDAO.showOverdueBooks(); }
    public List<BorrowRecord> getUserBorrowRecords(int userId) { return borrowDAO.getUserBorrowRecords(userId); }
    public int countCurrentBorrowedBooks(int userId) { return borrowDAO.countCurrentBorrowedBooks(userId);}
}
