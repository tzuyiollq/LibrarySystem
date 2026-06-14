package controller;

import model.User;
import service.BorrowService;
import service.ReminderService;
import service.ReservationService;
import view.ProfileInfoFrame;

public class ProfileInfoController {

    private ProfileInfoFrame frame;
    private User user;

    private BorrowService borrowService;
    private ReservationService reservationService;
    private ReminderService reminderService;

    public ProfileInfoController(ProfileInfoFrame frame, User user) {
        this.frame = frame;
        this.user = user;

        this.borrowService = new BorrowService();
        this.reservationService = new ReservationService();
        this.reminderService = new ReminderService();

        loadData();
    }

    private void loadData() {

        int borrowCount =
                borrowService.countCurrentBorrowedBooks(user.getUserId());

        int reservationCount =
                reservationService.countWaitingReservations(user.getUserId());

        int overdueCount =
                reminderService.countOverdueBooks(user.getUserId());

        frame.setData(
                user.getStudentNo(),
                user.getName(),
                user.getRoleLevel(),
                user.getStatus(),
                borrowCount,
                reservationCount,
                overdueCount
        );
    }
}