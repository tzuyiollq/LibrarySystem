package view.adminpanel;

import dao.DBConnection;
import view.components.AdminButton;
import view.components.AdminStyle;
import view.components.UIStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminUsersPanel extends JPanel {

    private DefaultTableModel model;

    public AdminUsersPanel() {

        setLayout(new BorderLayout(20, 20));
        setBackground(AdminStyle.BG);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        add(AdminStyle.title("使用者管理"), BorderLayout.NORTH);

        String[] columns = {
                "User ID", "學號", "姓名", "等級", "狀態"
        };

        model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        AdminStyle.applyTable(table);

        JPanel tableCard = AdminStyle.card();
        tableCard.setLayout(new BorderLayout());
        tableCard.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        bottom.setBackground(AdminStyle.BG);

        JTextField txtStudentNo = UIStyle.textField();
        txtStudentNo.setPreferredSize(new Dimension(160, 38));

        JButton btnSuspend = new AdminButton("停權");
        JButton btnActivate = new AdminButton("復權");
        JButton btnRefresh = new AdminButton("重新整理");

        btnSuspend.setPreferredSize(new Dimension(90, 38));
        btnActivate.setPreferredSize(new Dimension(90, 38));
        btnRefresh.setPreferredSize(new Dimension(110, 38));

        bottom.add(AdminStyle.label("學號"));
        bottom.add(txtStudentNo);
        bottom.add(btnSuspend);
        bottom.add(btnActivate);
        bottom.add(btnRefresh);

        add(tableCard, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        loadUsers();

        btnRefresh.addActionListener(e -> loadUsers());

        btnSuspend.addActionListener(e -> {
            updateStatus(txtStudentNo.getText().trim(), "SUSPENDED");
            loadUsers();
        });

        btnActivate.addActionListener(e -> {
            updateStatus(txtStudentNo.getText().trim(), "ACTIVE");
            loadUsers();
        });
    }

    private void loadUsers() {

        model.setRowCount(0);

        String sql = """
            SELECT user_id, student_no, name, role_level, status
            FROM users
            ORDER BY user_id DESC
        """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("user_id"),
                        rs.getString("student_no"),
                        rs.getString("name"),
                        rs.getString("role_level"),
                        rs.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStatus(String studentNo, String status) {

        if (studentNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "請輸入學號");
            return;
        }

        String sql = """
            UPDATE users
            SET status = ?
            WHERE student_no = ?
        """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, status);
            stmt.setString(2, studentNo);

            int count = stmt.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    count > 0 ? "更新成功！" : "查無此學號"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}