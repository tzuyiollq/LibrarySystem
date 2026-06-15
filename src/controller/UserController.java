package controller;

import model.User;
import view.LoginFrame;
import view.UserMainFrame;

import view.panel.BorrowBookPanel;
import view.panel.ReturnBookPanel;
import view.panel.BookSearchPanel;
import view.panel.HotBookPanel;
import view.panel.ReserveBookPanel;
import view.panel.FavoritePanel;
import view.panel.BookReviewPanel;
import view.panel.ProfilePanel;
import view.panel.WelcomePanel;
import view.panel.MyReservationPanel;
import view.panel.OverduePanel;
import view.panel.ReminderPanel;
import view.panel.BorrowRecordPanel;

import javax.swing.*;

public class UserController {

    private UserMainFrame frame;
    private User user;

    public UserController(UserMainFrame frame, User user) {
        this.frame = frame;
        this.user = user;

        frame.setContent(createHomePanel());
        initEvents();
    }

    private void initEvents() {

        frame.getBtnBorrowBook().addActionListener(e -> showBorrowBookPanel());
        frame.getBtnReturnBook().addActionListener(e -> showReturnBookPanel());

        frame.getBtnSearchBook().addActionListener(e ->
                frame.setContent(new BookSearchPanel(user))
        );

        frame.getBtnHotBooks().addActionListener(e -> showHotBookPanel());
        frame.getBtnReserveBook().addActionListener(e -> showReserveBookPanel());
        frame.getBtnFavoriteBook().addActionListener(e -> showFavoritePanel());
        frame.getBtnBookReview().addActionListener(e -> showBookReviewPanel());
        frame.getBtnProfile().addActionListener(e -> showProfilePanel());
    }

    private void showBorrowBookPanel() {
        BorrowBookPanel panel = new BorrowBookPanel(user);
        panel.getBtnHome().addActionListener(e ->
                frame.setContent(createHomePanel())
        );
        frame.setContent(panel);
    }

    private void showReturnBookPanel() {
        ReturnBookPanel panel = new ReturnBookPanel(user);
        panel.getBtnHome().addActionListener(e ->
                frame.setContent(createHomePanel())
        );
        frame.setContent(panel);
    }

    private void showHotBookPanel() {
        HotBookPanel panel = new HotBookPanel();
        panel.getBtnHome().addActionListener(e ->
                frame.setContent(createHomePanel())
        );
        frame.setContent(panel);
    }

    private void showReserveBookPanel() {
        ReserveBookPanel panel = new ReserveBookPanel(user);
        panel.getBtnHome().addActionListener(e ->
                frame.setContent(createHomePanel())
        );
        frame.setContent(panel);
    }

    private void showFavoritePanel() {
        FavoritePanel panel = new FavoritePanel(user);
        panel.getBtnHome().addActionListener(e ->
                frame.setContent(createHomePanel())
        );
        frame.setContent(panel);
    }

    private void showBookReviewPanel() {
        BookReviewPanel panel = new BookReviewPanel(user);
        panel.getBtnHome().addActionListener(e ->
                frame.setContent(createHomePanel())
        );
        frame.setContent(panel);
    }

    private void showProfilePanel() {

        ProfilePanel panel = new ProfilePanel(user);

        panel.getBtnProfileInfo().addActionListener(e ->
                showProfilePanel()
        );

        panel.getBtnBorrowRecords().addActionListener(e -> {
            BorrowRecordPanel p = new BorrowRecordPanel(user);
            p.getBtnHome().addActionListener(x -> showProfilePanel());
            frame.setContent(p);
        });

        panel.getBtnReminder().addActionListener(e -> {
            ReminderPanel p = new ReminderPanel(user);
            p.getBtnHome().addActionListener(x -> showProfilePanel());
            frame.setContent(p);
        });

        panel.getBtnOverdue().addActionListener(e -> {
            OverduePanel p = new OverduePanel(user);
            p.getBtnHome().addActionListener(x -> showProfilePanel());
            frame.setContent(p);
        });

        panel.getBtnMyReservations().addActionListener(e -> {
            MyReservationPanel p = new MyReservationPanel(user);
            p.getBtnHome().addActionListener(x -> showProfilePanel());
            frame.setContent(p);
        });

        panel.getBtnLogout().addActionListener(e -> {
            frame.dispose();

            LoginFrame loginFrame = new LoginFrame();
            new LoginController(loginFrame);
        });

        frame.setContent(panel);
    }

    private JPanel createHomePanel() {
        return new WelcomePanel(user);
    }
}