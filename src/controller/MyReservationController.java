package controller;

import model.Reservation;
import service.ReservationService;
import view.MyReservationFrame;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MyReservationController {

    private MyReservationFrame frame;
    private ReservationService reservationService;
    private int userId;

    public MyReservationController(MyReservationFrame frame, int userId) {
        this.frame = frame;
        this.userId = userId;
        this.reservationService = new ReservationService();

        loadData();
        initEvents();
    }

    private void loadData() {

        List<Reservation> reservations =
                reservationService.getUserReservations(userId);

        DefaultTableModel model = frame.getTableModel();
        model.setRowCount(0);

        for (Reservation r : reservations) {
            model.addRow(new Object[]{
                    r.getReservationId(),
                    r.getBookId(),
                    r.getBookTitle(),
                    r.getReservedAt(),
                    r.getStatus()
            });
        }
    }

    private void initEvents() {

        frame.getBtnCancel().addActionListener(e -> {

            try {
                int reservationId = Integer.parseInt(
                        frame.getTxtReservationId().getText()
                );

                boolean result =
                        reservationService.cancelReservation(
                                reservationId,
                                userId
                        );

                JOptionPane.showMessageDialog(
                        frame,
                        result ? "取消成功！" : "取消失敗！"
                );

                loadData();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        frame,
                        "請輸入正確的預約ID"
                );
            }
        });
    }
}