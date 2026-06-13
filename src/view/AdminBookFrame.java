package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminBookFrame extends JFrame {
    private DefaultTableModel tableModel;
    public AdminBookFrame() {
        setTitle("所有書籍"); setSize(1000, 500); setLocationRelativeTo(null); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableModel = new DefaultTableModel(new String[]{"Book ID", "書名", "作者", "主題", "出版社", "出版年", "狀態"}, 0);
        add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER); setVisible(true);
    }
    public DefaultTableModel getTableModel() { return tableModel; }
}
