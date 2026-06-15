package controller;

import javax.swing.JOptionPane;

import model.Admin;
import service.AuthService;
import view.AdminLoginFrame;
import view.AdminMainFrame;

public class AdminLoginController {

    private AdminLoginFrame frame;
    private AuthService authService;

    public AdminLoginController(AdminLoginFrame frame) {
        this.frame = frame;
        this.authService = new AuthService();

        initEvents();
    }

    private void initEvents() {

        frame.getBtnLogin().addActionListener(e -> {

            String username = frame.getTxtUsername().getText().trim();
            String password = new String(frame.getTxtPassword().getPassword()).trim();

            Admin admin = authService.loginAdmin(username, password);

            if (admin != null) {
                JOptionPane.showMessageDialog(frame, "管理者登入成功！");
                frame.dispose();

                AdminMainFrame mainFrame =
                        new AdminMainFrame(admin.getUsername());

                new AdminController(mainFrame);

            } else {
                JOptionPane.showMessageDialog(frame, "管理者登入失敗！");
            }
        });
    }
}