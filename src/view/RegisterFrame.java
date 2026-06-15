package view;

import view.components.ModernButton;
import view.components.UIStyle;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField txtStudentNo;
    private JTextField txtName;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRoleLevel;

    private JButton btnRegister;
    private JButton btnBack;

    public RegisterFrame() {

        setTitle("會員註冊");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(null);
        main.setBackground(UIStyle.BG);

        btnBack = new ModernButton("← 返回登入");
        btnBack.setBounds(30, 30, 140, 42);
        main.add(btnBack);

        JPanel card = UIStyle.card();
        card.setLayout(new GridBagLayout());
        card.setBounds(340, 120, 500, 500);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 14, 14, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = UIStyle.title("會員註冊");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        txtStudentNo = UIStyle.textField();
        txtName = UIStyle.textField();

        txtPassword = new JPasswordField();
        txtPassword.setFont(UIStyle.NORMAL_FONT);
        txtPassword.setForeground(UIStyle.TEXT);
        txtPassword.setBackground(Color.WHITE);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIStyle.BORDER),
                BorderFactory.createEmptyBorder(9, 14, 9, 14)
        ));

        cmbRoleLevel = UIStyle.comboBox(
                new String[]{"NORMAL", "VIP"}
        );

        btnRegister = new ModernButton("註冊");
        btnRegister.setPreferredSize(new Dimension(160, 46));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        card.add(title, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        card.add(UIStyle.label("學號："), gbc);

        gbc.gridx = 1;
        card.add(txtStudentNo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        card.add(UIStyle.label("姓名："), gbc);

        gbc.gridx = 1;
        card.add(txtName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        card.add(UIStyle.label("密碼："), gbc);

        gbc.gridx = 1;
        card.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        card.add(UIStyle.label("會員等級："), gbc);

        gbc.gridx = 1;
        card.add(cmbRoleLevel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        card.add(btnRegister, gbc);

        main.add(card);
        add(main);

        btnBack.addActionListener(e -> dispose());

        setVisible(true);
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

    public JComboBox<String> getCmbRoleLevel() {
        return cmbRoleLevel;
    }

    public JButton getBtnRegister() {
        return btnRegister;
    }

    public JButton getBtnBack() {
        return btnBack;
    }
}