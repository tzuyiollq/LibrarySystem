package view.panel;

import model.User;
import service.BorrowService;

import javax.swing.*;
import java.awt.*;

public class ReturnBookPanel extends JPanel {

    private User user;
    private JTextField txtBookId;
    private JButton btnReturn;
    private JButton btnHome;

    public ReturnBookPanel(User user) {

        this.user = user;

        setLayout(null);
        setBackground(Color.WHITE);

        btnHome = new JButton("← 回首頁");
        btnHome.setBounds(20, 20, 120, 35);
        add(btnHome);

        JLabel lblBookId = new JLabel("Book ID：");
        lblBookId.setFont(new Font("微軟正黑體", Font.BOLD, 28));
        lblBookId.setBounds(300, 200, 150, 45);
        add(lblBookId);

        txtBookId = new JTextField();
        txtBookId.setFont(new Font("微軟正黑體", Font.PLAIN, 22));
        txtBookId.setBounds(450, 200, 300, 45);
        add(txtBookId);

        btnReturn = new JButton("還書");
        btnReturn.setFont(new Font("微軟正黑體", Font.BOLD, 24));
        btnReturn.setBounds(430, 340, 140, 55);
        add(btnReturn);

        btnReturn.addActionListener(e -> {
            try {
                int bookId =
                        Integer.parseInt(
                                txtBookId.getText().trim()
                        );

                BorrowService borrowService =
                        new BorrowService();

                boolean success =
                        borrowService.returnBook(
                                user,
                                bookId
                        );

                JOptionPane.showMessageDialog(
                        this,
                        success
                                ? "還書成功！"
                                : "還書失敗，這本書不是你借的或尚未借出"
                );

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "請輸入正確的 Book ID"
                );
            }
        });
    }

    public JButton getBtnHome() {
        return btnHome;
    }
}