package view.panel;

import model.Reservation;
import model.User;
import service.ReservationService;
import view.components.ModernButton;
import view.components.UIStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MyReservationPanel extends JPanel {

    private User user;
    private ReservationService reservationService = new ReservationService();

    private JButton btnHome;
    private DefaultTableModel tableModel;
    private JTextField txtReservationId;

    public MyReservationPanel(User user) {

        this.user = user;

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

        card.add(UIStyle.title("我的預約"), BorderLayout.NORTH);

        String[] columns = {"預約ID", "Book ID", "書名", "預約時間", "狀態"};

        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        UIStyle.applyTable(table);

        card.add(UIStyle.scrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        bottom.setBackground(Color.WHITE);

        txtReservationId = UIStyle.textField();
        txtReservationId.setPreferredSize(new Dimension(130, 40));

        JButton btnCancel = new ModernButton("取消預約");
        btnCancel.setPreferredSize(new Dimension(130, 40));

        bottom.add(UIStyle.label("取消預約ID："));
        bottom.add(txtReservationId);
        bottom.add(btnCancel);

        card.add(bottom, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(card, BorderLayout.CENTER);

        btnCancel.addActionListener(e -> cancelReservation());

        loadData();
    }

    private void loadData() {

        tableModel.setRowCount(0);

        List<Reservation> list =
                reservationService.getUserReservations(user.getUserId());

        for (Reservation r : list) {
            tableModel.addRow(new Object[]{
                    r.getReservationId(),
                    r.getBookId(),
                    r.getBookTitle(),
                    r.getReservedAt(),
                    r.getStatus()
            });
        }
    }

    private void cancelReservation() {

        try {
            int reservationId =
                    Integer.parseInt(txtReservationId.getText().trim());

            boolean result =
                    reservationService.cancelReservation(
                            reservationId,
                            user.getUserId()
                    );

            JOptionPane.showMessageDialog(
                    this,
                    result ? "取消預約成功。" : "取消失敗，請確認預約ID。"
            );

            loadData();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "請輸入正確預約ID");
        }
    }

    public JButton getBtnHome() {
        return btnHome;
    }
}