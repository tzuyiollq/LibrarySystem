package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminStudentRecordFrame extends JFrame {
    private JTextField txtStudentNo;
    private JButton btnSearch;
    private DefaultTableModel tableModel;
    public AdminStudentRecordFrame() {
        setTitle("依學號查詢借還紀錄"); setSize(1000, 500); setLocationRelativeTo(null); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel top = new JPanel(new FlowLayout()); top.add(new JLabel("學號：")); txtStudentNo = new JTextField(20); top.add(txtStudentNo); btnSearch = new JButton("查詢"); top.add(btnSearch);
        tableModel = new DefaultTableModel(new String[]{"紀錄ID", "書名", "借出日", "應還日", "歸還日"}, 0);
        add(top, BorderLayout.NORTH); add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER); setVisible(true);
    }
    public JTextField getTxtStudentNo() { return txtStudentNo; }
    public JButton getBtnSearch() { return btnSearch; }
    public DefaultTableModel getTableModel() { return tableModel; }
}
