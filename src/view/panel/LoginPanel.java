package view.panel;

import controller.AdminLoginController;
import view.AdminLoginFrame;
import view.components.ModernButton;
import view.components.UIStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class LoginPanel extends JPanel {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private JButton btnLogin;
    private JButton btnRegister;
    private JButton btnHome;

    private JLabel lblKey;
    private JLabel lblBook;
    private JLabel lblEffect;

    private int mouseX;
    private int mouseY;
    private boolean adminOpening = false;

    public LoginPanel() {

        setLayout(null);
        setBackground(UIStyle.BG);

        btnHome = new ModernButton("← 回首頁");
        btnHome.setBounds(30, 30, 130, 42);
        add(btnHome);

        JPanel card = UIStyle.card();
        card.setLayout(new GridBagLayout());
        card.setBounds(360, 160, 460, 420);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 14, 14, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = UIStyle.title("會員登入");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        txtUsername = UIStyle.textField();

        txtPassword = new JPasswordField();
        txtPassword.setFont(UIStyle.NORMAL_FONT);
        txtPassword.setForeground(UIStyle.TEXT);
        txtPassword.setBackground(Color.WHITE);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIStyle.BORDER),
                BorderFactory.createEmptyBorder(9, 14, 9, 14)
        ));

        btnLogin = new ModernButton("登入");
        btnRegister = new ModernButton("註冊");

        btnLogin.setPreferredSize(new Dimension(160, 46));
        btnRegister.setPreferredSize(new Dimension(110, 40));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        card.add(title, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        card.add(UIStyle.label("學號 / 帳號："), gbc);

        gbc.gridx = 1;
        card.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        card.add(UIStyle.label("密碼："), gbc);

        gbc.gridx = 1;
        card.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        card.add(btnLogin, gbc);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        bottom.setBackground(Color.WHITE);
        bottom.add(UIStyle.label("還沒有帳號？"));
        bottom.add(btnRegister);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        card.add(bottom, gbc);

        add(card);

        lblKey = new JLabel("🔑");
        lblKey.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 45));
        lblKey.setBounds(830, 520, 70, 70);
        lblKey.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(lblKey);

        lblBook = new JLabel("📕");
        lblBook.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        lblBook.setBounds(910, 510, 90, 90);
        add(lblBook);

        lblEffect = new JLabel("✨");
        lblEffect.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
        lblEffect.setBounds(885, 475, 140, 120);
        lblEffect.setVisible(false);
        add(lblEffect);

        initSecretAdminDrag();
    }

    private void initSecretAdminDrag() {

        lblKey.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                checkUnlock();
            }
        });

        lblKey.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {

                if (adminOpening) {
                    return;
                }

                int x = lblKey.getX() + e.getX() - mouseX;
                int y = lblKey.getY() + e.getY() - mouseY;

                lblKey.setLocation(x, y);
            }
        });
    }

    private void checkUnlock() {

        if (adminOpening) {
            return;
        }

        Rectangle keyRect = lblKey.getBounds();
        Rectangle bookRect = lblBook.getBounds();

        if (keyRect.intersects(bookRect)) {

            adminOpening = true;

            lblKey.setVisible(false);
            lblBook.setText("📖");
            lblBook.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 78));
            lblBook.setBounds(900, 490, 120, 110);

            lblEffect.setVisible(true);

            Timer flashTimer = new Timer(120, e -> {
                lblEffect.setVisible(!lblEffect.isVisible());
            });
            flashTimer.start();

            Timer openTimer = new Timer(900, e -> {
                flashTimer.stop();

                AdminLoginFrame adminLoginFrame =
                        new AdminLoginFrame();

                new AdminLoginController(adminLoginFrame);

                Window window =
                        SwingUtilities.getWindowAncestor(this);

                if (window != null) {
                    window.dispose();
                }
            });

            openTimer.setRepeats(false);
            openTimer.start();
        }
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

    public JButton getBtnHome() {
        return btnHome;
    }
}