package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminBorrowRecordFrame extends JFrame {
    private DefaultTableModel tableModel;
    public AdminBorrowRecordFrame() {
        setTitle("所有借還紀錄"); setSize(1000, 500); setLocationRelativeTo(null); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableModel = new DefaultTableModel(new String[]{"紀錄ID", "學號", "姓名", "書名", "借出日", "應還日", "歸還日"}, 0);
        add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER); setVisible(true);
    }
    public DefaultTableModel getTableModel() { return tableModel; }
}
