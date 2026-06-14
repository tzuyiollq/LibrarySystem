package controller;

import javax.swing.JOptionPane;

import model.User;
import service.AuthService;
import view.AdminLoginFrame;
import view.LoginFrame;
import view.RegisterFrame;
import view.UserMainFrame;
import service.ReminderService;

public class LoginController {

    private LoginFrame loginFrame;
    private AuthService authService;

    public LoginController(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
        this.authService = new AuthService();

        initEvents();
    }

    private void initEvents() {

        loginFrame.getBtnLogin().addActionListener(e -> {

            String username = loginFrame.getTxtUsername().getText().trim();
            String password = new String(loginFrame.getTxtPassword().getPassword()).trim();

            User user = authService.loginUser(username, password);

            if (user != null) {
            	ReminderService reminderService = new ReminderService();
                reminderService.suspendLongOverdueUsers();

                if (user.getStatus().equals("SUSPENDED")) {

                    JOptionPane.showMessageDialog(
                            loginFrame,
                            "此帳號已停權，請洽管理員！"
                    );

                    return;
                }

                int overdueCount =
                        reminderService.countOverdueBooks(user.getUserId());

                if (overdueCount > 0) {
                    JOptionPane.showMessageDialog(
                            loginFrame,
                            "您有 " + overdueCount + " 本逾期書籍尚未歸還！"
                    );
                }

                JOptionPane.showMessageDialog(loginFrame, "使用者登入成功！");
                loginFrame.dispose();

                UserMainFrame userMainFrame =
                        new UserMainFrame(user.getName());

                new UserController(userMainFrame, user);

            } else {
                JOptionPane.showMessageDialog(loginFrame, "登入失敗！");
            }
        });

        loginFrame.addPropertyChangeListener("OPEN_ADMIN_LOGIN", e -> {

            AdminLoginFrame adminLoginFrame = new AdminLoginFrame();
            new AdminLoginController(adminLoginFrame);

            adminLoginFrame.addWindowListener(new java.awt.event.WindowAdapter() {

                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    loginFrame.resetAdminLoginOpened();
                }

                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    loginFrame.resetAdminLoginOpened();
                }
            });
        });

        loginFrame.getBtnRegister().addActionListener(e -> {

            RegisterFrame frame = new RegisterFrame();
            new RegisterController(frame);
        });
    }
}