package view;

import javax.swing.*;
import java.awt.*;

public class AdminLoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public AdminLoginFrame() {
        setTitle("管理者登入"); setSize(400, 250); setLocationRelativeTo(null); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("管理者帳號：")); txtUsername = new JTextField(); panel.add(txtUsername);
        panel.add(new JLabel("密碼：")); txtPassword = new JPasswordField(); panel.add(txtPassword);
        panel.add(new JLabel()); btnLogin = new JButton("管理者登入"); panel.add(btnLogin);
        add(panel); setVisible(true);
    }
    public JTextField getTxtUsername() { return txtUsername; }
    public JPasswordField getTxtPassword() { return txtPassword; }
    public JButton getBtnLogin() { return btnLogin; }
}
