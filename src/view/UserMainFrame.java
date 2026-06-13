package view;

import javax.swing.*;
import java.awt.*;

public class UserMainFrame extends JFrame {
    private JButton btnSearchBook, btnBorrowBook, btnReturnBook, btnMyRecords, btnBookRecords, btnReminder, btnOverdue, btnLogout;
    public UserMainFrame(String username) {
        setTitle("使用者主畫面 - " + username); setSize(500, 460); setLocationRelativeTo(null); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(9, 1, 10, 10));
        panel.add(new JLabel("歡迎使用：" + username, SwingConstants.CENTER));
        btnSearchBook = new JButton("查詢書籍"); btnBorrowBook = new JButton("借書"); btnReturnBook = new JButton("還書");
        btnMyRecords = new JButton("個人借還紀錄"); btnBookRecords = new JButton("書籍借還紀錄"); btnReminder = new JButton("到期提醒");
        btnOverdue = new JButton("逾期處理"); btnLogout = new JButton("登出");
        panel.add(btnSearchBook); panel.add(btnBorrowBook); panel.add(btnReturnBook); panel.add(btnMyRecords); panel.add(btnBookRecords); panel.add(btnReminder); panel.add(btnOverdue); panel.add(btnLogout);
        add(panel); setVisible(true);
    }
    public JButton getBtnSearchBook() { return btnSearchBook; }
    public JButton getBtnBorrowBook() { return btnBorrowBook; }
    public JButton getBtnReturnBook() { return btnReturnBook; }
    public JButton getBtnMyRecords() { return btnMyRecords; }
    public JButton getBtnBookRecords() { return btnBookRecords; }
    public JButton getBtnReminder() { return btnReminder; }
    public JButton getBtnOverdue() { return btnOverdue; }
    public JButton getBtnLogout() { return btnLogout; }
}
