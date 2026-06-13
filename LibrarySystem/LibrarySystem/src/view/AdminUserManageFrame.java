package view;

import javax.swing.*;
import java.awt.*;

public class AdminUserManageFrame extends JFrame {

    private JTextField txtStudentNo;
    private JButton btnSuspend;
    private JButton btnActivate;

    public AdminUserManageFrame() {

        setTitle("使用者管理");
        setSize(400, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(
                new GridLayout(2, 2, 10, 10));

        panel.add(new JLabel("學號："));

        txtStudentNo = new JTextField();

        panel.add(txtStudentNo);

        btnSuspend = new JButton("停權");

        btnActivate = new JButton("復權");

        panel.add(btnSuspend);
        panel.add(btnActivate);

        add(panel);

        setVisible(true);
    }

    public JTextField getTxtStudentNo() {
        return txtStudentNo;
    }

    public JButton getBtnSuspend() {
        return btnSuspend;
    }

    public JButton getBtnActivate() {
        return btnActivate;
    }
}