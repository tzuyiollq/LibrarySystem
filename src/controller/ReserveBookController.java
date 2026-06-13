package controller;

import javax.swing.JOptionPane;

import model.User;
import service.ReservationService;
import view.ReserveBookFrame;

public class ReserveBookController {

    private ReserveBookFrame frame;
    private User user;
    private ReservationService reservationService;

    public ReserveBookController(ReserveBookFrame frame, User user) {
        this.frame = frame;
        this.user = user;
        this.reservationService = new ReservationService();

        initEvents();
    }

    private void initEvents() {

        frame.getBtnReserve().addActionListener(e -> {

            try {
                int bookId = Integer.parseInt(
                        frame.getTxtBookId().getText()
                );

                boolean result =
                        reservationService.reserveBook(user, bookId);

                JOptionPane.showMessageDialog(
                        frame,
                        result ? "預約成功！" : "預約失敗，請確認書籍狀態"
                );

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        frame,
                        "請輸入正確的 Book ID"
                );
            }
        });
    }
}