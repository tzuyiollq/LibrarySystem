package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;

    private JLabel lblKey;
    private JLabel lblBook;

    private int keyStartX = 720;
    private int keyStartY = 520;

    private boolean adminLoginOpened = false;

    public LoginFrame() {
        setTitle("圖書館借還書系統");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        initDragEvent();

        setVisible(true);
    }

    private void initComponents() {

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);

        JLabel lblName = new JLabel("名稱");
        lblName.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 28));
        lblName.setBounds(300, 190, 100, 40);

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 22));
        txtUsername.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(150, 80, 20)));
        txtUsername.setBounds(390, 190, 260, 40);

        JLabel lblPassword = new JLabel("密碼");
        lblPassword.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 28));
        lblPassword.setBounds(300, 270, 100, 40);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 22));
        txtPassword.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(150, 80, 20)));
        txtPassword.setBounds(390, 270, 260, 40);

        btnLogin = new JButton("登入");
        btnLogin.setFont(new Font("Microsoft JhengHei", Font.BOLD, 26));
        btnLogin.setBounds(390, 360, 160, 60);
        btnLogin.setFocusPainted(false);

        JLabel lblNoAccount = new JLabel("還沒有？");
        lblNoAccount.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
        lblNoAccount.setBounds(330, 470, 100, 35);

        btnRegister = new JButton("註冊");
        btnRegister.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
        btnRegister.setBounds(430, 470, 100, 35);
        btnRegister.setFocusPainted(false);

        lblKey = new JLabel("🔑");
        lblKey.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 45));
        lblKey.setBounds(keyStartX, keyStartY, 70, 70);
        lblKey.setCursor(new Cursor(Cursor.HAND_CURSOR));

        lblBook = new JLabel("📕");
        lblBook.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        lblBook.setBounds(790, 500, 80, 90);

        mainPanel.add(lblName);
        mainPanel.add(txtUsername);
        mainPanel.add(lblPassword);
        mainPanel.add(txtPassword);
        mainPanel.add(btnLogin);
        mainPanel.add(lblNoAccount);
        mainPanel.add(btnRegister);
        mainPanel.add(lblKey);
        mainPanel.add(lblBook);

        add(mainPanel);
    }

    private void initDragEvent() {

        MouseAdapter dragListener = new MouseAdapter() {

            private int offsetX;
            private int offsetY;

            @Override
            public void mousePressed(MouseEvent e) {
                offsetX = e.getX();
                offsetY = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {

                int newX = lblKey.getX() + e.getX() - offsetX;
                int newY = lblKey.getY() + e.getY() - offsetY;

                lblKey.setLocation(newX, newY);

                if (!adminLoginOpened && lblKey.getBounds().intersects(lblBook.getBounds())) {

                    adminLoginOpened = true;

                    lblKey.setLocation(keyStartX, keyStartY);

                    firePropertyChange("OPEN_ADMIN_LOGIN", false, true);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                lblKey.setLocation(keyStartX, keyStartY);
            }
        };

        lblKey.addMouseListener(dragListener);
        lblKey.addMouseMotionListener(dragListener);
    }

    public void resetAdminLoginOpened() {
        adminLoginOpened = false;
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

    public JButton getBtnRegister() {
        return btnRegister;
    }
}