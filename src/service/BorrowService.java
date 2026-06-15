package service;

import dao.BorrowDAO;
import model.User;
import model.BorrowResult;
import model.BorrowRecord;

import java.util.List;

public class BorrowService {

    private BorrowDAO borrowDAO = new BorrowDAO();

    public BorrowResult borrowBookWithResult(User user, int bookId, int days) {

        if (user == null) return BorrowResult.NOT_LOGIN;

        if ("SUSPENDED".equals(user.getStatus())) {
            return BorrowResult.SUSPENDED;
        }

        if ("NORMAL".equals(user.getRoleLevel()) && days > 7) {
            return BorrowResult.NORMAL_DAYS_LIMIT;
        }

        if ("VIP".equals(user.getRoleLevel()) && days > 14) {
            return BorrowResult.VIP_DAYS_LIMIT;
        }

        int currentBorrowed =
                borrowDAO.countCurrentBorrowedBooks(user.getUserId());

        int maxBooks =
                "VIP".equals(user.getRoleLevel()) ? 10 : 5;

        if (currentBorrowed >= maxBooks) {
            return BorrowResult.BORROW_LIMIT;
        }

        if (!borrowDAO.bookExists(bookId)) {
            return BorrowResult.BOOK_NOT_FOUND;
        }

        if (!borrowDAO.isBookAvailable(bookId)) {
            return BorrowResult.BOOK_NOT_AVAILABLE;
        }

        boolean success =
                borrowDAO.borrowBook(
                        user.getUserId(),
                        bookId,
                        days
                );

        return success
                ? BorrowResult.SUCCESS
                : BorrowResult.SYSTEM_ERROR;
    }

    public boolean borrowBook(User user, int bookId, int days) {
        return borrowBookWithResult(user, bookId, days)
                == BorrowResult.SUCCESS;
    }

    public boolean returnBook(User user, int bookId) {
        if (user == null) return false;

        return borrowDAO.returnBookByUserAndBook(
                user.getUserId(),
                bookId
        );
    }

    public boolean returnBook(int recordId) {
        return borrowDAO.returnBook(recordId);
    }

    public int countCurrentBorrowedBooks(int userId) {
        return borrowDAO.countCurrentBorrowedBooks(userId);
    }

    public int countUserTotalBorrowRecords(int userId) {
        return borrowDAO.countUserTotalBorrowRecords(userId);
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