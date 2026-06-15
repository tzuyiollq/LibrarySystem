package view.adminpanel;

import dao.DBConnection;
import view.components.AdminButton;
import view.components.AdminStyle;
import view.components.UIStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminRecordsPanel extends JPanel {

    private DefaultTableModel model;
    private JTextField txtStudentNo;

    public AdminRecordsPanel() {

        setLayout(new BorderLayout(20, 20));
        setBackground(AdminStyle.BG);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel top = AdminStyle.card();
        top.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 8));

        txtStudentNo = UIStyle.textField();
        txtStudentNo.setPreferredSize(new Dimension(180, 38));

        JButton btnSearch = new AdminButton("依學號查詢");
        JButton btnAll = new AdminButton("全部紀錄");

        btnSearch.setPreferredSize(new Dimension(130, 38));
        btnAll.setPreferredSize(new Dimension(110, 38));

        top.add(AdminStyle.title("借閱紀錄"));
        top.add(AdminStyle.label("學號"));
        top.add(txtStudentNo);
        top.add(btnSearch);
        top.add(btnAll);

        String[] columns = {
                "紀錄ID", "學號", "姓名", "書名", "借出日", "到期日", "歸還日", "天數"
        };

        model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        AdminStyle.applyTable(table);

        JPanel tableCard = AdminStyle.card();
        tableCard.setLayout(new BorderLayout());
        tableCard.add(new JScrollPane(table), BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);
        add(tableCard, BorderLayout.CENTER);

        loadAllRecords();

        btnAll.addActionListener(e -> loadAllRecords());

        btnSearch.addActionListener(e ->
                loadByStudentNo(txtStudentNo.getText().trim())
        );
    }

    private void loadAllRecords() {
        String sql = """
            SELECT br.record_id,
                   u.student_no,
                   u.name,
                   b.title,
                   br.borrow_date,
                   br.due_date,
                   br.return_date,
                   br.borrow_days
            FROM borrow_records br
            JOIN users u ON br.user_id = u.user_id
            JOIN books b ON br.book_id = b.book_id
            ORDER BY br.record_id DESC
        """;

        load(sql, null);
    }

    private void loadByStudentNo(String studentNo) {
        String sql = """
            SELECT br.record_id,
                   u.student_no,
                   u.name,
                   b.title,
                   br.borrow_date,
                   br.due_date,
                   br.return_date,
                   br.borrow_days
            FROM borrow_records br
            JOIN users u ON br.user_id = u.user_id
            JOIN books b ON br.book_id = b.book_id
            WHERE u.student_no = ?
            ORDER BY br.record_id DESC
        """;

        load(sql, studentNo);
    }

    private void load(String sql, String studentNo) {

        model.setRowCount(0);

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            if (studentNo != null) {
                stmt.setString(1, studentNo);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("record_id"),
                        rs.getString("student_no"),
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getString("borrow_date"),
                        rs.getString("due_date"),
                        rs.getString("return_date"),
                        rs.getInt("borrow_days")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}