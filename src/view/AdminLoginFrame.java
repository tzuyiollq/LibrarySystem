package view;

import view.components.AdminButton;
import view.components.AdminStyle;
import view.components.UIStyle;

import javax.swing.*;
import java.awt.*;

public class AdminLoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private JButton btnLogin;
    private JButton btnHome;

    public AdminLoginFrame() {

        setTitle("管理者登入");
        setSize(800, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel background = new JPanel(null);
        background.setBackground(AdminStyle.BG);

        btnHome = new AdminButton("← 回首頁");
        btnHome.setBounds(28, 28, 130, 42);
        background.add(btnHome);

        JPanel card = AdminStyle.card();
        card.setLayout(new GridBagLayout());
        card.setBounds(200, 90, 400, 380);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel icon = new JLabel("👑", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 46));

        JLabel title = AdminStyle.title("管理者登入");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        txtUsername = UIStyle.textField();
        txtUsername.setPreferredSize(new Dimension(280, 42));

        txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(new Dimension(280, 42));
        txtPassword.setFont(UIStyle.NORMAL_FONT);
        txtPassword.setForeground(AdminStyle.TEXT);
        txtPassword.setBackground(Color.WHITE);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AdminStyle.BORDER),
                BorderFactory.createEmptyBorder(9, 14, 9, 14)
        ));

        btnLogin = new AdminButton("登入後台");
        btnLogin.setPreferredSize(new Dimension(180, 44));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        card.add(icon, gbc);

        gbc.gridy = 1;
        card.add(title, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 2;
        card.add(AdminStyle.label("管理者帳號："), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        card.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        card.add(AdminStyle.label("密碼："), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        card.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        card.add(btnLogin, gbc);

        background.add(card);
        add(background);

        btnHome.addActionListener(e -> {
            dispose();
            new PublicHomeFrame();
        });

        getRootPane().setDefaultButton(btnLogin);

        setVisible(true);
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public JButton getBtnHome() {
        return btnHome;
    }
}