package controller;

import model.User;
import view.BorrowRecordFrame;
import view.LoginFrame;
import view.OverdueFrame;
import view.ProfileFrame;
import view.ReminderFrame;
import view.MyReservationFrame;

public class ProfileController {

    private ProfileFrame frame;
    private User user;

    public ProfileController(ProfileFrame frame, User user) {
        this.frame = frame;
        this.user = user;

        initEvents();
    }

    private void initEvents() {

        frame.getBtnMyRecords().addActionListener(e -> {
            BorrowRecordFrame recordFrame = new BorrowRecordFrame();
            new BorrowController(recordFrame, user.getUserId());
        });

        frame.getBtnReminder().addActionListener(e -> {
            ReminderFrame reminderFrame = new ReminderFrame();
            new ReminderController(reminderFrame, user.getUserId());
        });

        frame.getBtnOverdue().addActionListener(e -> {
            OverdueFrame overdueFrame = new OverdueFrame();
            new OverdueController(overdueFrame, user.getUserId());
        });

        frame.getBtnMyReservations().addActionListener(e -> {
            MyReservationFrame myReservationFrame =
                    new MyReservationFrame();

            new MyReservationController(
                    myReservationFrame,
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