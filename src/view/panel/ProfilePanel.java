package view.panel;

import controller.LoginController;
import model.BorrowRecord;
import model.Reservation;
import model.User;
import service.BorrowService;
import service.ReminderService;
import service.ReservationService;
import view.LoginFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProfilePanel extends JPanel {

    private User user;

    private BorrowService borrowService = new BorrowService();
    private ReminderService reminderService = new ReminderService();
    private ReservationService reservationService = new ReservationService();

    private JPanel contentPanel;

    public ProfilePanel(User user) {
        this.user = user;

        setLayout(null);

        JPanel menuPanel = new JPanel(new GridLayout(6, 1, 0, 10));
        menuPanel.setBounds(20, 20, 180, 520);

        JButton btnInfo = new JButton("個人資料");
        JButton btnRecords = new JButton("借閱紀錄");
        JButton btnReminder = new JButton("到期提醒");
        JButton btnOverdue = new JButton("逾期處理");
        JButton btnReservations = new JButton("我的預約");
        JButton btnLogout = new JButton("登出");

        menuPanel.add(btnInfo);
        menuPanel.add(btnRecords);
        menuPanel.add(btnReminder);
        menuPanel.add(btnOverdue);
        menuPanel.add(btnReservations);
        menuPanel.add(btnLogout);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBounds(230, 20, 700, 520);

        add(menuPanel);
        add(contentPanel);

        btnInfo.addActionListener(e -> showInfo());
        btnRecords.addActionListener(e -> showBorrowRecords());
        btnReminder.addActionListener(e -> showReminders());
        btnOverdue.addActionListener(e -> showOverdue());
        btnReservations.addActionListener(e -> showReservations());

        btnLogout.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).dispose();
            LoginFrame loginFrame = new LoginFrame();
            new LoginController(loginFrame);
        });

        showInfo();
    }

    private void setContent(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showInfo() {
        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));

        int borrowCount = borrowService.countCurrentBorrowedBooks(user.getUserId());
        int reservationCount = reservationService.countWaitingReservations(user.getUserId());
        int overdueCount = reminderService.countOverdueBooks(user.getUserId());

        panel.add(new JLabel("學號：" + user.getStudentNo()));
        panel.add(new JLabel("姓名：" + user.getName()));
        panel.add(new JLabel("會員等級：" + user.getRoleLevel()));
        panel.add(new JLabel("帳號狀態：" + user.getStatus()));
        panel.add(new JLabel("目前借閱中：" + borrowCount + " 本"));
        panel.add(new JLabel("目前預約中：" + reservationCount + " 本"));
        panel.add(new JLabel("逾期未還：" + overdueCount + " 本"));

        setContent(panel);
    }

    private void showBorrowRecords() {
        String[] columns = {
                "紀錄ID", "書名", "借出時間", "到期時間", "歸還時間", "借閱天數"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        List<BorrowRecord> records =
                borrowService.getUserBorrowRecords(user.getUserId());

        for (BorrowRecord r : records) {
            model.addRow(new Object[]{
                    r.getRecordId(),
                    r.getBookTitle(),
                    r.getBorrowDate(),
                    r.getDueDate(),
                    r.getReturnDate(),
                    r.getBorrowDays()
            });
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        setContent(panel);
    }

    private void showReminders() {
        String[] columns = {
                "紀錄ID", "書名", "借出時間", "到期時間"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        List<BorrowRecord> records =
                reminderService.getDueSoonBooks(user.getUserId());

        for (BorrowRecord r : records) {
            model.addRow(new Object[]{
                    r.getRecordId(),
                    r.getBookTitle(),
                    r.getBorrowDate(),
                    r.getDueDate()
            });
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        setContent(panel);
    }

    private void showOverdue() {
        String[] columns = {
                "紀錄ID", "書名", "借出時間", "到期時間", "逾期天數"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        List<BorrowRecord> records =
                reminderService.getOverdueRecords(user.getUserId());

        for (BorrowRecord r : records) {
            model.addRow(new Object[]{
                    r.getRecordId(),
                    r.getBookTitle(),
                    r.getBorrowDate(),
                    r.getDueDate(),
                    r.getBorrowDays()
            });
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        setContent(panel);
    }

    private void showReservations() {
        String[] columns = {
                "預約ID", "Book ID", "書名", "預約時間", "狀態"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        List<Reservation> reservations =
                reservationService.getUserReservations(user.getUserId());

        for (Reservation r : reservations) {
            model.addRow(new Object[]{
                    r.getReservationId(),
                    r.getBookId(),
                    r.getBookTitle(),
                    r.getReservedAt(),
                    r.getStatus()
            });
        }

        JPanel bottomPanel = new JPanel(new FlowLayout());

        JTextField txtReservationId = new JTextField(10);
        JButton btnCancel = new JButton("取消預約");

        bottomPanel.add(new JLabel("預約ID："));
        bottomPanel.add(txtReservationId);
        bottomPanel.add(btnCancel);

        btnCancel.addActionListener(e -> {
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
                        result ? "取消成功！" : "取消失敗！"
                );

                showReservations();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "請輸入正確預約ID");
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        setContent(panel);
    }
}