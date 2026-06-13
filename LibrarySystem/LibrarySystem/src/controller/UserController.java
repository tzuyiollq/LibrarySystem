package controller;

import model.User;
import view.BookSearchFrame;
import view.BorrowRecordFrame;
import view.LoginFrame;
import view.UserMainFrame;
import view.BorrowBookFrame;
import view.ReturnBookFrame;
import view.ReminderFrame;

public class UserController {

    private UserMainFrame frame;
    private User user;

    public UserController(UserMainFrame frame, User user) {
        this.frame = frame;
        this.user = user;

        initEvents();
    }

    private void initEvents() {

        frame.getBtnSearchBook().addActionListener(e -> {
            BookSearchFrame searchFrame = new BookSearchFrame();
            new BookController(searchFrame);
        });

        frame.getBtnBorrowBook().addActionListener(e -> {

            BorrowBookFrame borrowFrame =
                    new BorrowBookFrame();

            new BorrowBookController(
                    borrowFrame,
                    user
            );
        });

        frame.getBtnReturnBook().addActionListener(e -> {

            ReturnBookFrame returnFrame =
                    new ReturnBookFrame();

            new ReturnBookController(returnFrame);
        });

        frame.getBtnMyRecords().addActionListener(e -> {
            BorrowRecordFrame recordFrame = new BorrowRecordFrame();
            new BorrowController(recordFrame, user.getUserId());
        });

        frame.getBtnReminder().addActionListener(e -> {

            ReminderFrame reminderFrame =
                    new ReminderFrame();

            new ReminderController(
                    reminderFrame,
                    user.getUserId()
            );
        });

        frame.getBtnLogout().addActionListener(e -> {
            frame.dispose();

            LoginFrame loginFrame = new LoginFrame();
            new LoginController(loginFrame);
        });
    }
}