package controller;

import javax.swing.JOptionPane;

import model.Admin;
import model.User;
import service.AuthService;
import view.LoginFrame;
import view.RegisterFrame;
import view.UserMainFrame;
import view.AdminMainFrame;

public class LoginController {

    private LoginFrame loginFrame;
    private AuthService authService;

    public LoginController(LoginFrame loginFrame) {

        this.loginFrame = loginFrame;
        this.authService = new AuthService();

        initEvents();
    }

    private void initEvents() {

        // 使用者登入
        loginFrame.getBtnLogin().addActionListener(e -> {

            String username =
                    loginFrame.getTxtUsername().getText();

            String password =
                    new String(
                            loginFrame.getTxtPassword().getPassword()
                    );

            User user =
                    authService.loginUser(
                            username,
                            password
                    );

            if (user != null) {
                JOptionPane.showMessageDialog(loginFrame, "使用者登入成功！");
                loginFrame.dispose();

                UserMainFrame userMainFrame = new UserMainFrame(user.getName());
                new UserController(userMainFrame, user);

            } else {
                JOptionPane.showMessageDialog(loginFrame, "登入失敗！");
            }
        });
        
        loginFrame.addPropertyChangeListener("ADMIN_LOGIN_TRIGGERED", e -> {

            String username = loginFrame.getTxtUsername().getText();

            String password = new String(
                    loginFrame.getTxtPassword().getPassword()
            );

            Admin admin = authService.loginAdmin(username, password);

            if (admin != null) {

                JOptionPane.showMessageDialog(
                        loginFrame,
                        "管理者登入成功！"
                );

                loginFrame.dispose();

                AdminMainFrame adminMainFrame =
                        new AdminMainFrame(admin.getUsername());

                new AdminController(adminMainFrame);

            } else {

                JOptionPane.showMessageDialog(
                        loginFrame,
                        "管理者登入失敗！"
                );
            }
        });

        // 管理者登入
        loginFrame.getBtnAdminLogin().addActionListener(e -> {

            String username =
                    loginFrame.getTxtUsername().getText();

            String password =
                    new String(
                            loginFrame.getTxtPassword().getPassword()
                    );

            Admin admin =
                    authService.loginAdmin(
                            username,
                            password
                    );

            if (admin != null) {
                JOptionPane.showMessageDialog(loginFrame, "管理者登入成功！");
                loginFrame.dispose();

                AdminMainFrame adminMainFrame = new AdminMainFrame(admin.getUsername());
                new AdminController(adminMainFrame);

            } else {
                JOptionPane.showMessageDialog(loginFrame, "登入失敗！");
            }
        });

        // 開啟註冊畫面
        loginFrame.getBtnRegister().addActionListener(e -> {

            RegisterFrame frame =
                    new RegisterFrame();

            new RegisterController(frame);

        });
    }
}