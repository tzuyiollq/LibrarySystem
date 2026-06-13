package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BorrowRecordFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public BorrowRecordFrame() {

        setTitle("個人借閱紀錄");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();

        setVisible(true);
    }

    private void initComponents() {

        String[] columns = {
                "紀錄ID",
                "書名",
                "借出日",
                "應還日",
                "歸還日"
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