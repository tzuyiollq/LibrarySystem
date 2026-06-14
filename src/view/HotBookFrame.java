package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HotBookFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public HotBookFrame() {
        setTitle("熱門書籍排行榜");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {
                "排名", "Book ID", "書名", "作者", "借閱次數"
        };

        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        add(new JScrollPane(table), BorderLayout.CENTER);
        setVisible(true);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}