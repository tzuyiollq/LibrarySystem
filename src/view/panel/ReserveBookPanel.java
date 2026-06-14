package view.panel;

import model.User;
import service.ReservationService;

import javax.swing.*;
import java.awt.*;

public class ReserveBookPanel extends JPanel {

    private ReservationService reservationService =
            new ReservationService();

    public ReserveBookPanel(User user) {

        setLayout(new GridLayout(3, 2, 10, 10));

        JTextField txtBookId = new JTextField();
        JButton btnReserve = new JButton("預約");

        add(new JLabel("Book ID："));
        add(txtBookId);

        add(new JLabel());
        add(btnReserve);

        btnReserve.addActionListener(e -> {

            try {
                int bookId =
                        Integer.parseInt(txtBookId.getText());

                boolean result =
                        reservationService.reserveBook(user, bookId);

                JOptionPane.showMessageDialog(
                        this,
                        result ? "預約成功！" : "預約失敗，請確認書籍狀態"
                );

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "請輸入正確 Book ID");
            }
        });
    }
}