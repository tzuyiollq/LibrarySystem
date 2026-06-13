package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminUserFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public AdminUserFrame() {

        setTitle("所有使用者");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {
                "User ID",
                "學號",
                "姓名",
                "權限",
                "狀態"
        };

        tableModel = new DefaultTableModel(columns, 0);

        table = new JTable(tableModel);

        add(new JScrollPane(table), BorderLayout.CENTER);

        setVisible(true);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}