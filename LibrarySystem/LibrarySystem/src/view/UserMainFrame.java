package view;

import javax.swing.*;
import java.awt.*;

public class UserMainFrame extends JFrame {

    private JButton btnSearchBook;
    private JButton btnBorrowBook;
    private JButton btnReturnBook;
    private JButton btnMyRecords;
    private JButton btnBookRecords;
    private JButton btnReminder;
    private JButton btnLogout;

    public UserMainFrame(String username) {
        setTitle("使用者主畫面 - " + username);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));

        btnSearchBook = new JButton("查詢書籍");
        btnBorrowBook = new JButton("借書");
        btnReturnBook = new JButton("還書");
        btnMyRecords = new JButton("個人借還紀錄");
        btnBookRecords = new JButton("書籍借還紀錄");
        btnReminder = new JButton("到期提醒");
        btnLogout = new JButton("登出");

        panel.add(btnSearchBook);
        panel.add(btnBorrowBook);
        panel.add(btnReturnBook);
        panel.add(btnMyRecords);
        panel.add(btnBookRecords);
        panel.add(btnReminder);
        panel.add(btnLogout);

        add(panel);
        setVisible(true);
    }

    public JButton getBtnSearchBook() { return btnSearchBook; }
    public JButton getBtnBorrowBook() { return btnBorrowBook; }
    public JButton getBtnReturnBook() { return btnReturnBook; }
    public JButton getBtnMyRecords() { return btnMyRecords; }
    public JButton getBtnBookRecords() { return btnBookRecords; }
    public JButton getBtnReminder() { return btnReminder; }
    public JButton getBtnLogout() { return btnLogout; }
}