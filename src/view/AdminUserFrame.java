package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminUserFrame extends JFrame {
    private DefaultTableModel tableModel;
    public AdminUserFrame() {
        setTitle("所有使用者"); setSize(800, 400); setLocationRelativeTo(null); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableModel = new DefaultTableModel(new String[]{"User ID", "學號", "姓名", "權限", "狀態"}, 0);
        add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER); setVisible(true);
    }
    public DefaultTableModel getTableModel() { return tableModel; }
}
