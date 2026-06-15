package view;

import view.components.AdminButton;
import view.components.AdminStyle;

import javax.swing.*;
import java.awt.*;

public class AdminMainFrame extends JFrame {

    private JButton btnDashboard;
    private JButton btnBooks;
    private JButton btnUsers;
    private JButton btnReviews;
    private JButton btnRecords;
    private JButton btnUpdateRole;
    private JButton btnLogout;

    private JPanel contentPanel;

    public AdminMainFrame(String username) {

        setTitle("圖書館管理後台 - " + username);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(AdminStyle.BG);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(AdminStyle.BG);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(35, 24, 35, 24));
        menuPanel.setPreferredSize(new Dimension(230, 700));

        btnDashboard = menuButton("管理首頁");
        btnBooks = menuButton("書籍管理");
        btnUsers = menuButton("使用者管理");
        btnReviews = menuButton("書評管理");
        btnRecords = menuButton("借閱紀錄");
        btnUpdateRole = menuButton("會員等級");
        btnLogout = menuButton("登出");

        menuPanel.add(btnDashboard);
        menuPanel.add(Box.createVerticalStrut(14));
        menuPanel.add(btnBooks);
        menuPanel.add(Box.createVerticalStrut(14));
        menuPanel.add(btnUsers);
        menuPanel.add(Box.createVerticalStrut(14));
        menuPanel.add(btnReviews);
        menuPanel.add(Box.createVerticalStrut(14));
        menuPanel.add(btnRecords);
        menuPanel.add(Box.createVerticalStrut(14));
        menuPanel.add(btnUpdateRole);
        menuPanel.add(Box.createVerticalStrut(28));
        menuPanel.add(btnLogout);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(AdminStyle.BG);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        mainPanel.add(menuPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JButton menuButton(String text) {
        JButton button = new AdminButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 64));
        button.setPreferredSize(new Dimension(180, 64));
        return button;
    }

    public void setContent(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public JButton getBtnDashboard() {
        return btnDashboard;
    }

    public JButton getBtnBooks() {
        return btnBooks;
    }

    public JButton getBtnUsers() {
        return btnUsers;
    }

    public JButton getBtnReviews() {
        return btnReviews;
    }

    public JButton getBtnRecords() {
        return btnRecords;
    }

    public JButton getBtnUpdateRole() {
        return btnUpdateRole;
    }

    public JButton getBtnLogout() {
        return btnLogout;
    }
}