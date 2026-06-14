package service;

import dao.BorrowDAO;
import model.User;
import java.util.List;
import model.BorrowRecord;

public class BorrowService {

    private BorrowDAO borrowDAO = new BorrowDAO();

    public boolean borrowBook(User user, int bookId, int days) {

        if (user == null) {
            System.out.println("請先登入！");
            return false;
        }

        if ("SUSPENDED".equals(user.getStatus())) {
            System.out.println("停權使用者不能借書！");
            return false;
        }

        if (user.getRoleLevel().equals("NORMAL") && days > 7) {
            System.out.println("NORMAL 使用者最多只能借 7 天！");
            return false;
        }

        if (user.getRoleLevel().equals("VIP") && days > 14) {
            System.out.println("VIP 使用者最多只能借 14 天！");
            return false;
        }

        int currentBorrowed =
                borrowDAO.countCurrentBorrowedBooks(user.getUserId());

        int maxBooks =
                user.getRoleLevel().equals("VIP") ? 10 : 5;

        if (currentBorrowed >= maxBooks) {
            System.out.println("已達借閱上限！");
            return false;
        }

        return borrowDAO.borrowBook(
                user.getUserId(),
                bookId,
                days
        );
    }

    public boolean returnBook(int recordId) {
        return borrowDAO.returnBook(recordId);
    }

    public boolean returnBook(User user, int bookId) {

        if (user == null) {
            System.out.println("請先登入！");
            return false;
        }

        return borrowDAO.returnBookByUserAndBook(
                user.getUserId(),
                bookId
        );
    }

    public int countCurrentBorrowedBooks(int userId) {
        return borrowDAO.countCurrentBorrowedBooks(userId);
    }

    public void showUserBorrowRecords(int userId) {
        borrowDAO.showUserBorrowRecords(userId);
    }

    public void showBookBorrowRecords(int bookId) {
        borrowDAO.showBookBorrowRecords(bookId);
    }

    public void showOverdueBooks() {
        borrowDAO.showOverdueBooks();
    }

    public List<BorrowRecord> getUserBorrowRecords(int userId) {
        return borrowDAO.getUserBorrowRecords(userId);
    }
}