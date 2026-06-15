package view.panel;

import dao.ReminderDAO;
import model.BorrowRecord;
import model.User;
import view.components.ModernButton;
import view.components.UIStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReminderPanel extends JPanel {

    private JButton btnHome;
    private DefaultTableModel tableModel;

    public ReminderPanel(User user) {

        setLayout(new BorderLayout(18, 18));
        setBackground(UIStyle.BG);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        btnHome = new ModernButton("← 上一頁");
        btnHome.setPreferredSize(new Dimension(130, 42));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(UIStyle.BG);
        top.add(btnHome);

        JPanel card = UIStyle.card();
        card.setLayout(new BorderLayout(16, 16));

        card.add(UIStyle.title("到期提醒"), BorderLayout.NORTH);

        String[] columns = {"紀錄ID", "書名", "借出時間", "到期時間"};

        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        UIStyle.applyTable(table);

        card.add(UIStyle.scrollPane(table), BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);
        add(card, BorderLayout.CENTER);

        loadData(user);
    }

    private void loadData(User user) {

        tableModel.setRowCount(0);

        ReminderDAO dao = new ReminderDAO();
        List<BorrowRecord> records =
                dao.getDueSoonBooks(user.getUserId());

        for (BorrowRecord r : records) {
            tableModel.addRow(new Object[]{
                    r.getRecordId(),
                    r.getBookTitle(),
                    r.getBorrowDate(),
                    r.getDueDate()
            });
        }
    }

    public JButton getBtnHome() {
        return btnHome;
    }
}