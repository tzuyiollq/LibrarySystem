package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminStudentRecordFrame extends JFrame {

    private JTextField txtStudentNo;
    private JButton btnSearch;
    private JTable table;
    private DefaultTableModel tableModel;

    public AdminStudentRecordFrame() {

        setTitle("依學號查詢借還紀錄");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel(new FlowLayout());

        topPanel.add(new JLabel("學號："));

        txtStudentNo = new JTextField(20);
        topPanel.add(txtStudentNo);

        btnSearch = new JButton("查詢");
        topPanel.add(btnSearch);

        String[] columns = {
                "紀錄ID",
                "書名",
                "借出日",
                "應還日",
                "歸還日"
        };

        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        setVisible(true);
    }

    public JTextField getTxtStudentNo() {
        return txtStudentNo;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}