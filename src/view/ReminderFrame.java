package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReminderFrame extends JFrame {
    private DefaultTableModel tableModel;
    public ReminderFrame() {
        setTitle("到期提醒"); setSize(800, 400); setLocationRelativeTo(null); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableModel = new DefaultTableModel(new String[]{"紀錄ID", "書名", "借出時間", "到期時間"}, 0);
        add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER); setVisible(true);
    }
    public DefaultTableModel getTableModel() { return tableModel; }
}
