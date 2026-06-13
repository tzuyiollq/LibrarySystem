package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReminderFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public ReminderFrame() {

        setTitle("到期提醒");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();

        setVisible(true);
    }

    private void initComponents() {

        String[] columns = {
                "紀錄ID",
                "書名",
                "借出時間",
                "到期時間"
        };

        tableModel = new DefaultTableModel(columns, 0);

        table = new JTable(tableModel);

        JScrollPane scrollPane =
                new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}