package view;

import javax.swing.*;
import java.awt.*;

public class UserMainFrame extends JFrame {

    private JButton btnBorrowBook;
    private JButton btnReturnBook;
    private JButton btnSearchBook;
    private JButton btnHotBooks;
    private JButton btnReserveBook;
    private JButton btnProfile;

    private JPanel contentPanel;

    public UserMainFrame(String username) {

        setTitle("圖書館借還書系統 - " + username);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel menuPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        menuPanel.setPreferredSize(new Dimension(220, 650));

        btnBorrowBook = new JButton("借書");
        btnReturnBook = new JButton("還書");
        btnSearchBook = new JButton("書籍查詢");
        btnHotBooks = new JButton("熱門書籍");
        btnReserveBook = new JButton("預約書籍");
        btnProfile = new JButton("個人資料");

        menuPanel.add(btnBorrowBook);
        menuPanel.add(btnReturnBook);
        menuPanel.add(btnSearchBook);
        menuPanel.add(btnHotBooks);
        menuPanel.add(btnReserveBook);
        menuPanel.add(btnProfile);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(new JLabel("歡迎使用，" + username, SwingConstants.CENTER), BorderLayout.CENTER);

        mainPanel.add(menuPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    public void setContent(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public JButton getBtnBorrowBook() { return btnBorrowBook; }
    public JButton getBtnReturnBook() { return btnReturnBook; }
    public JButton getBtnSearchBook() { return btnSearchBook; }
    public JButton getBtnHotBooks() { return btnHotBooks; }
    public JButton getBtnReserveBook() { return btnReserveBook; }
    public JButton getBtnProfile() { return btnProfile; }
}