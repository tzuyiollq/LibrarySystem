package view;

import javax.swing.*;
import java.awt.*;

public class ProfileFrame extends JFrame {

    private JButton btnMyRecords;
    private JButton btnReminder;
    private JButton btnOverdue;
    private JButton btnMyReservations;
    private JButton btnLogout;

    public ProfileFrame(String username) {

        setTitle("個人資料 - " + username);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));

        btnMyRecords = new JButton("個人借閱紀錄");
        btnReminder = new JButton("到期提醒");
        btnOverdue = new JButton("逾期處理");
        btnMyReservations = new JButton("我的預約");
        btnLogout = new JButton("登出");

        panel.add(btnMyRecords);
        panel.add(btnReminder);
        panel.add(btnOverdue);
        panel.add(btnMyReservations);
        panel.add(btnLogout);

        add(panel);
        setVisible(true);
    }

    public JButton getBtnMyRecords() { return btnMyRecords; }
    public JButton getBtnReminder() { return btnReminder; }
    public JButton getBtnOverdue() { return btnOverdue; }
    public JButton getBtnMyReservations() { return btnMyReservations; }
    public JButton getBtnLogout() { return btnLogout; }
}