package controller;

import javax.swing.JOptionPane;
import model.Admin;
import service.AuthService;
import view.*;

public class AdminLoginController {
    private AdminLoginFrame frame;
    private AuthService authService;
    public AdminLoginController(AdminLoginFrame frame) { this.frame = frame; this.authService = new AuthService(); initEvents(); }
    private void initEvents() {
        frame.getBtnLogin().addActionListener(e -> {
            Admin admin = authService.loginAdmin(frame.getTxtUsername().getText().trim(), new String(frame.getTxtPassword().getPassword()).trim());
            if (admin != null) { JOptionPane.showMessageDialog(frame, "管理者登入成功！"); frame.dispose(); new AdminController(new AdminMainFrame(admin.getUsername())); }
            else JOptionPane.showMessageDialog(frame, "管理者登入失敗！");
        });
    }
}
