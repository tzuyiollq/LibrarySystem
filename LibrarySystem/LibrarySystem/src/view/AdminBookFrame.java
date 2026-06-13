package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminBookFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public AdminBookFrame() {

        setTitle("所有書籍");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {
                "Book ID",
                "書名",
                "作者",
                "主題",
                "出版社",
                "出版年",
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