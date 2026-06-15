package view.adminpanel;

import dao.DBConnection;
import view.components.AdminButton;
import view.components.AdminStyle;
import view.components.UIStyle;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminUpdateRolePanel extends JPanel {

    public AdminUpdateRolePanel() {

        setLayout(new BorderLayout());
        setBackground(AdminStyle.BG);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel card = AdminStyle.card();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(520, 320));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 14, 14, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = AdminStyle.title("調整會員等級");

        JTextField txtStudentNo = UIStyle.textField();
        JComboBox<String> cmbRole =
                new JComboBox<>(new String[]{"NORMAL", "VIP"});

        JButton btnUpdate = new AdminButton("更新等級");
        btnUpdate.setPreferredSize(new Dimension(150, 42));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        card.add(title, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        card.add(AdminStyle.label("學號："), gbc);

        gbc.gridx = 1;
        card.add(txtStudentNo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        card.add(AdminStyle.label("會員等級："), gbc);

        gbc.gridx = 1;
        card.add(cmbRole, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        card.add(btnUpdate, gbc);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(AdminStyle.BG);
        wrapper.add(card);

        add(wrapper, BorderLayout.CENTER);

        btnUpdate.addActionListener(e -> {

            String studentNo = txtStudentNo.getText().trim();
            String role = cmbRole.getSelectedItem().toString();

            updateRole(studentNo, role);
        });
    }

    private void updateRole(String studentNo, String role) {

        if (studentNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "請輸入學號");
            return;
        }

        String sql = """
            UPDATE users
            SET role_level = ?
            WHERE student_no = ?
        """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, role);
            stmt.setString(2, studentNo);

            int count = stmt.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    count > 0 ? "會員等級更新成功！" : "查無此學號"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}