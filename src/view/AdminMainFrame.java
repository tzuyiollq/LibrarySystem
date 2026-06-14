package view;

import javax.swing.*;
import java.awt.*;

public class AdminMainFrame extends JFrame {

    private JButton btnAllRecords;
    private JButton btnStudentRecords;
    private JButton btnAllBooks;
    private JButton btnAddBook;
    private JButton btnRemoveBook;
    private JButton btnAllUsers;
    private JButton btnSuspendUser;
    private JButton btnActivateUser;
    private JButton btnUpdateRole;
    private JButton btnLogout;

    public AdminMainFrame(String username) {

        setTitle("管理者主畫面 - " + username);
        setSize(500, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(11, 1, 10, 10));

        panel.add(new JLabel("管理者：" + username, SwingConstants.CENTER));

        btnAllRecords = new JButton("查看所有借還紀錄");
        btnStudentRecords = new JButton("依學號查詢借還紀錄");
        btnAllBooks = new JButton("查看所有書籍");
        btnAddBook = new JButton("新增書籍");
        btnRemoveBook = new JButton("下架書籍");
        btnAllUsers = new JButton("查看所有使用者");
        btnSuspendUser = new JButton("停權使用者");
        btnActivateUser = new JButton("復權使用者");
        btnUpdateRole = new JButton("調整會員等級");
        btnLogout = new JButton("登出");

        panel.add(btnAllRecords);
        panel.add(btnStudentRecords);
        panel.add(btnAllBooks);
        panel.add(btnAddBook);
        panel.add(btnRemoveBook);
        panel.add(btnAllUsers);
        panel.add(btnSuspendUser);
        panel.add(btnActivateUser);
        panel.add(btnUpdateRole);
        panel.add(btnLogout);

        add(panel);
        setVisible(true);
    }

    public JButton getBtnAllRecords() { return btnAllRecords; }
    public JButton getBtnStudentRecords() { return btnStudentRecords; }
    public JButton getBtnAllBooks() { return btnAllBooks; }
    public JButton getBtnAddBook() { return btnAddBook; }
    public JButton getBtnRemoveBook() { return btnRemoveBook; }
    public JButton getBtnAllUsers() { return btnAllUsers; }
    public JButton getBtnSuspendUser() { return btnSuspendUser; }
    public JButton getBtnActivateUser() { return btnActivateUser; }
    public JButton getBtnUpdateRole() { return btnUpdateRole; }
    public JButton getBtnLogout() { return btnLogout; }
}