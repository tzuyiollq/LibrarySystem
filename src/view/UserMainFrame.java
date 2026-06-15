package view;

import javax.swing.*;
import java.awt.*;
import view.components.ModernButton;

public class UserMainFrame extends JFrame {

    private JButton btnBorrowBook;
    private JButton btnReturnBook;
    private JButton btnSearchBook;
    private JButton btnHotBooks;
    private JButton btnReserveBook;
    private JButton btnFavoriteBook;
    private JButton btnBookReview;
    private JButton btnProfile;
    

    private JPanel contentPanel;

    public UserMainFrame(String username) {

        setTitle("圖書館借還書系統 - " + username);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(
                new Color(244,246,248)
        );

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        JPanel menuPanel =
                new JPanel(new GridLayout(8, 1, 10, 10));

        menuPanel.setPreferredSize(
                new Dimension(220, 700)
        );

        btnBorrowBook = new ModernButton("借書");
        btnReturnBook = new ModernButton("還書");
        btnSearchBook = new ModernButton("書籍查詢");
        btnHotBooks = new ModernButton("熱門書籍");
        btnReserveBook = new ModernButton("預約書籍");
        btnFavoriteBook = new ModernButton("收藏書籍");
        btnBookReview = new ModernButton("書評系統");
        btnProfile = new ModernButton("個人資料");

        menuPanel.add(btnBorrowBook);
        menuPanel.add(btnReturnBook);
        menuPanel.add(btnSearchBook);
        menuPanel.add(btnHotBooks);
        menuPanel.add(btnReserveBook);
        menuPanel.add(btnFavoriteBook);
        menuPanel.add(btnBookReview);
        menuPanel.add(btnProfile);

        contentPanel =
                new JPanel(new BorderLayout());

        contentPanel.add(
                new JLabel(
                        "歡迎使用，" + username,
                        SwingConstants.CENTER
                ),
                BorderLayout.CENTER
        );

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
    public JButton getBtnFavoriteBook() { return btnFavoriteBook; }
    public JButton getBtnBookReview() { return btnBookReview; }
    public JButton getBtnProfile() { return btnProfile; }
}