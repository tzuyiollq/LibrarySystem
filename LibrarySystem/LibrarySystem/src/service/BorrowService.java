package service;

import dao.BorrowDAO;
import model.User;
import java.util.List;
import model.BorrowRecord;

public class BorrowService {

    private BorrowDAO borrowDAO = new BorrowDAO();

    public void borrowBook(User user, int bookId, int days) {

        if (user == null) {
            System.out.println("請先登入！");
            return;
        }

        if (user.getRoleLevel().equals("NORMAL") && days > 7) {

            System.out.println("NORMAL 使用者最多只能借 7 天！");
            return;
        }

        if (user.getRoleLevel().equals("VIP") && days > 14) {

            System.out.println("VIP 使用者最多只能借 14 天！");
            return;
        }

        borrowDAO.borrowBook(
                user.getUserId(),
                bookId,
                days
        );
    }

    public void returnBook(int recordId) {

        borrowDAO.returnBook(recordId);
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