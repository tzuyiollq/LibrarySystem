package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BookSearchFrame extends JFrame {
    private JTextField txtKeyword;
    private JButton btnSearch;
    private JTable table;
    private DefaultTableModel tableModel;
    public BookSearchFrame() {
        setTitle("查詢書籍"); setSize(900, 500); setLocationRelativeTo(null); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel top = new JPanel(new FlowLayout()); top.add(new JLabel("關鍵字：")); txtKeyword = new JTextField(30); top.add(txtKeyword); btnSearch = new JButton("查詢"); top.add(btnSearch);
        tableModel = new DefaultTableModel(new String[]{"ID", "書名", "作者", "主題", "出版社", "出版年", "ISBN", "狀態"}, 0);
        table = new JTable(tableModel); add(top, BorderLayout.NORTH); add(new JScrollPane(table), BorderLayout.CENTER); setVisible(true);
    }
    public JTextField getTxtKeyword() { return txtKeyword; }
    public JButton getBtnSearch() { return btnSearch; }
    public DefaultTableModel getTableModel() { return tableModel; }
}
