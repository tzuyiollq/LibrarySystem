package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OverdueFrame extends JFrame {
    private DefaultTableModel tableModel;
    public OverdueFrame() {
        setTitle("逾期處理"); setSize(850, 400); setLocationRelativeTo(null); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableModel = new DefaultTableModel(new String[]{"紀錄ID", "書名", "借出時間", "到期時間", "逾期天數"}, 0);
        add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER); setVisible(true);
    }
    public DefaultTableModel getTableModel() { return tableModel; }
}
