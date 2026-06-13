package controller;

import javax.swing.JOptionPane;
import model.User;
import service.AuthService;
import view.*;

public class LoginController {
    private LoginFrame loginFrame;
    private AuthService authService;
    public LoginController(LoginFrame loginFrame) { this.loginFrame = loginFrame; this.authService = new AuthService(); initEvents(); }
    private void initEvents() {
        loginFrame.getBtnLogin().addActionListener(e -> {
            String username = loginFrame.getTxtUsername().getText().trim();
            String password = new String(loginFrame.getTxtPassword().getPassword()).trim();
            User user = authService.loginUser(username, password);
            if (user != null) { JOptionPane.showMessageDialog(loginFrame, "使用者登入成功！"); loginFrame.dispose(); new UserController(new UserMainFrame(user.getName()), user); }
            else JOptionPane.showMessageDialog(loginFrame, "登入失敗或帳號已停權！");
        });
        loginFrame.addPropertyChangeListener("OPEN_ADMIN_LOGIN", e -> new AdminLoginController(new AdminLoginFrame()));
        loginFrame.getBtnRegister().addActionListener(e -> new RegisterController(new RegisterFrame()));
    }
}
