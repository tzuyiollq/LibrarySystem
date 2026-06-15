package controller;

import javax.swing.JOptionPane;

import model.User;
import service.AuthService;
import service.ReminderService;
import view.LoginFrame;
import view.RegisterFrame;
import view.UserMainFrame;

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

            String username =
                    loginFrame.getTxtUsername().getText().trim();

            String password =
                    new String(
                            loginFrame.getTxtPassword().getPassword()
                    ).trim();

            User user =
                    authService.loginUser(username, password);

            if (user != null) {

                ReminderService reminderService =
                        new ReminderService();

                reminderService.suspendLongOverdueUsers();

                if ("SUSPENDED".equals(user.getStatus())) {

                    JOptionPane.showMessageDialog(
                            loginFrame,
                            """
                            您的帳號目前無法使用借閱功能。

                            可能原因：
                            • 逾期未歸還超過限制天數

                            如需恢復權限，請聯繫圖書館管理員。
                            """
                    );

                    return;
                }

                int overdueCount =
                        reminderService.countOverdueBooks(
                                user.getUserId()
                        );

                if (overdueCount > 0) {
                    JOptionPane.showMessageDialog(
                            loginFrame,
                            "您有 " + overdueCount + " 本逾期書籍尚未歸還！"
                    );
                }

                JOptionPane.showMessageDialog(
                        loginFrame,
                        "歡迎回來，" + user.getName() + "！"
                );

                loginFrame.dispose();

                UserMainFrame userMainFrame =
                        new UserMainFrame(
                                user.getName()
                        );

                new UserController(
                        userMainFrame,
                        user
                );

            } else {

                JOptionPane.showMessageDialog(
                        loginFrame,
                        """
                        登入失敗。

                        請確認：
                        • 學號是否正確
                        • 密碼是否正確
                        """
                );
            }
        });

        loginFrame.getBtnRegister().addActionListener(e -> {

            RegisterFrame frame =
                    new RegisterFrame();

            new RegisterController(frame);
        });
    }
}