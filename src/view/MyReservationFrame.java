package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MyReservationFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtReservationId;
    private JButton btnCancel;

    public MyReservationFrame() {

        setTitle("我的預約");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {
                "預約ID", "Book ID", "書名", "預約時間", "狀態"
        };

        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(new JLabel("取消預約ID："));

        txtReservationId = new JTextField(10);
        bottomPanel.add(txtReservationId);

        btnCancel = new JButton("取消預約");
        bottomPanel.add(btnCancel);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTextField getTxtReservationId() {
        return txtReservationId;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }
}