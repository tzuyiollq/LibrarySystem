package view;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField txtStudentNo;
    private JTextField txtName;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRole;

    private JButton btnRegister;
    private JButton btnBack;

    public RegisterFrame() {

        setTitle("使用者註冊");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();

        setVisible(true);
    }

    private void initComponents() {

        JPanel panel = new JPanel(
                new GridLayout(6, 2, 10, 10));

        panel.add(new JLabel("學號："));

        txtStudentNo = new JTextField();
        panel.add(txtStudentNo);

        panel.add(new JLabel("姓名："));

        txtName = new JTextField();
        panel.add(txtName);

        panel.add(new JLabel("密碼："));

        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        panel.add(new JLabel("權限："));

        cmbRole = new JComboBox<>(
                new String[]{"NORMAL", "VIP"}
        );

        panel.add(cmbRole);

        btnRegister = new JButton("註冊");
        panel.add(btnRegister);

        btnBack = new JButton("返回登入");
        panel.add(btnBack);

        add(panel);
    }

    public JTextField getTxtStudentNo() {
        return txtStudentNo;
    }

    public JTextField getTxtName() {
        return txtName;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public JComboBox<String> getCmbRole() {
        return cmbRole;
    }

    public JButton getBtnRegister() {
        return btnRegister;
    }

    public JButton getBtnBack() {
        return btnBack;
    }
}