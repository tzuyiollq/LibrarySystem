package controller;

import view.AdminMainFrame;
import view.LoginFrame;
import view.adminpanel.*;

public class AdminController {

    private AdminMainFrame frame;

    public AdminController(AdminMainFrame frame) {
        this.frame = frame;

        frame.setContent(new AdminDashboardPanel());

        initEvents();
    }

    private void initEvents() {

        frame.getBtnDashboard().addActionListener(e ->
                frame.setContent(new AdminDashboardPanel())
        );

        frame.getBtnBooks().addActionListener(e ->
                frame.setContent(new AdminBooksPanel())
        );

        frame.getBtnUsers().addActionListener(e ->
                frame.setContent(new AdminUsersPanel())
        );

        frame.getBtnRecords().addActionListener(e ->
                frame.setContent(new AdminRecordsPanel())
        );

        frame.getBtnUpdateRole().addActionListener(e ->
                frame.setContent(new AdminUpdateRolePanel())
        );

        frame.getBtnLogout().addActionListener(e -> {
            frame.dispose();

            LoginFrame loginFrame = new LoginFrame();
            new LoginController(loginFrame);
        });
        frame.getBtnReviews().addActionListener(e ->
        	frame.setContent(new AdminReviewPanel())
        );
    }
}