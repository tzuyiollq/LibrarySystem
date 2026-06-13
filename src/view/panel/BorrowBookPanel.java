package view.panel;

import model.User;
import service.BorrowService;

import javax.swing.*;
import java.awt.*;

public class BorrowBookPanel extends JPanel {

    private BorrowService borrowService = new BorrowService();

    public BorrowBookPanel(User user) {

        setLayout(new GridLayout(4, 2, 10, 10));

        JTextField txtBookId = new JTextField();
        JComboBox<Integer> cmbDays = new JComboBox<>(new Integer[]{1, 3, 7, 14});
        JButton btnBorrow = new JButton("借書");

        add(new JLabel("Book ID："));
        add(txtBookId);

        add(new JLabel("借閱天數："));
        add(cmbDays);

        add(new JLabel());
        add(btnBorrow);

        btnBorrow.addActionListener(e -> {
            try {
                int bookId = Integer.parseInt(txtBookId.getText());
                int days = (Integer) cmbDays.getSelectedItem();

                borrowService.borrowBook(user, bookId, days);

                JOptionPane.showMessageDialog(this, "借書完成！");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "請輸入正確 Book ID");
            }
        });
    }
}