package controller;

import javax.swing.JOptionPane;
import model.User;
import view.*;

public class UserController {
    private UserMainFrame frame;
    private User user;
    public UserController(UserMainFrame frame, User user) { this.frame = frame; this.user = user; initEvents(); }
    private void initEvents() {
        frame.getBtnSearchBook().addActionListener(e -> new BookController(new BookSearchFrame()));
        frame.getBtnBorrowBook().addActionListener(e -> new BorrowBookController(new BorrowBookFrame(), user));
        frame.getBtnReturnBook().addActionListener(e -> new ReturnBookController(new ReturnBookFrame()));
        frame.getBtnMyRecords().addActionListener(e -> new BorrowController(new BorrowRecordFrame(), user.getUserId()));
        frame.getBtnBookRecords().addActionListener(e -> JOptionPane.showMessageDialog(frame, "請由管理者查詢書籍借還紀錄"));
        frame.getBtnReminder().addActionListener(e -> new ReminderController(new ReminderFrame(), user.getUserId()));
        frame.getBtnOverdue().addActionListener(e -> new OverdueController(new OverdueFrame(), user.getUserId()));
        frame.getBtnLogout().addActionListener(e -> { frame.dispose(); LoginFrame login = new LoginFrame(); new LoginController(login); });
    }
}
