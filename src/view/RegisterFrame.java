package view;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private JTextField txtStudentNo, txtName;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRole;
    private JButton btnRegister, btnBack;

    public RegisterFrame() {
        setTitle("使用者註冊"); setSize(450, 300); setLocationRelativeTo(null); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        txtStudentNo = new JTextField(); txtName = new JTextField(); txtPassword = new JPasswordField(); cmbRole = new JComboBox<>(new String[]{"NORMAL", "VIP"});
        btnRegister = new JButton("註冊"); btnBack = new JButton("返回登入");
        panel.add(new JLabel("學號：")); panel.add(txtStudentNo); panel.add(new JLabel("姓名：")); panel.add(txtName);
        panel.add(new JLabel("密碼：")); panel.add(txtPassword); panel.add(new JLabel("權限：")); panel.add(cmbRole);
        panel.add(btnRegister); panel.add(btnBack); add(panel); setVisible(true);
    }
    public JTextField getTxtStudentNo() { return txtStudentNo; }
    public JTextField getTxtName() { return txtName; }
    public JPasswordField getTxtPassword() { return txtPassword; }
    public JComboBox<String> getCmbRole() { return cmbRole; }
    public JButton getBtnRegister() { return btnRegister; }
    public JButton getBtnBack() { return btnBack; }
}
