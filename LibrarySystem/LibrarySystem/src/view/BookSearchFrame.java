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

        setTitle("查詢書籍");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();

        setVisible(true);
    }

    private void initComponents() {

        JPanel topPanel = new JPanel(new FlowLayout());

        topPanel.add(new JLabel("關鍵字："));

        txtKeyword = new JTextField(30);
        topPanel.add(txtKeyword);

        btnSearch = new JButton("查詢");
        topPanel.add(btnSearch);

        String[] columns = {
                "ID", "書名", "作者", "主題", "出版社", "出版年", "ISBN", "狀態"
        };

        tableModel = new DefaultTableModel(columns, 0);

        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JTextField getTxtKeyword() {
        return txtKeyword;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}