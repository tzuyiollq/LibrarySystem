package view;

import javax.swing.*;
import java.awt.*;

public class AdminUpdateRoleFrame extends JFrame {

    private JTextField txtStudentNo;
    private JComboBox<String> cmbRole;
    private JButton btnUpdate;

    public AdminUpdateRoleFrame() {

        setTitle("調整會員等級");
        setSize(400, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        txtStudentNo = new JTextField();
        cmbRole = new JComboBox<>(new String[]{"NORMAL", "VIP"});
        btnUpdate = new JButton("更新等級");

        panel.add(new JLabel("學號："));
        panel.add(txtStudentNo);

        panel.add(new JLabel("會員等級："));
        panel.add(cmbRole);

        panel.add(new JLabel());
        panel.add(btnUpdate);

        add(panel);
        setVisible(true);
    }

    public JTextField getTxtStudentNo() {
        return txtStudentNo;
    }

    public JComboBox<String> getCmbRole() {
        return cmbRole;
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }
}