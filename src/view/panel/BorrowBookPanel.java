package view.panel;

import model.User;
import service.BorrowService;

import javax.swing.*;
import java.awt.*;

public class BorrowBookPanel extends JPanel {

    private User user;

    private JTextField txtBookId;
    private JComboBox<Integer> cmbDays;

    private JLabel lblRemain;

    private JButton btnBorrow;
    private JButton btnHome;

    public BorrowBookPanel(User user) {

        this.user = user;

        setLayout(null);

        //--------------------------------
        // 回首頁
        //--------------------------------

        btnHome = new JButton("← 回首頁");
        btnHome.setBounds(20,20,120,35);
        add(btnHome);

        //--------------------------------
        // Book ID
        //--------------------------------

        JLabel lblBookId = new JLabel("Book ID :");
        lblBookId.setBounds(300,180,120,40);
        lblBookId.setFont(new Font("微軟正黑體",Font.BOLD,24));

        add(lblBookId);

        txtBookId = new JTextField();

        txtBookId.setBounds(
                430,
                180,
                250,
                40
        );

        add(txtBookId);

        //--------------------------------
        // 天數
        //--------------------------------

        JLabel lblDays = new JLabel("天數 :");

        lblDays.setBounds(
                300,
                260,
                120,
                40
        );

        lblDays.setFont(
                new Font("微軟正黑體",
                        Font.BOLD,
                        24)
        );

        add(lblDays);

        cmbDays =
                new JComboBox<>(
                        new Integer[]{
                                7,
                                14,
                                21,
                                30
                        }
                );

        cmbDays.setBounds(
                430,
                260,
                250,
                40
        );

        add(cmbDays);

        //--------------------------------
        // 可借數量
        //--------------------------------

        lblRemain =
                new JLabel(
                        getRemainText(),
                        SwingConstants.CENTER
                );

        lblRemain.setBounds(
                330,
                340,
                300,
                40
        );

        lblRemain.setFont(
                new Font(
                        "微軟正黑體",
                        Font.BOLD,
                        20
                )
        );

        add(lblRemain);

        //--------------------------------
        // 借書按鈕
        //--------------------------------

        btnBorrow =
                new JButton("借書");

        btnBorrow.setBounds(
                420,
                450,
                120,
                50
        );

        btnBorrow.setFont(
                new Font(
                        "微軟正黑體",
                        Font.BOLD,
                        20
                )
        );

        add(btnBorrow);

        //--------------------------------
        // 事件
        //--------------------------------

        btnBorrow.addActionListener(e -> {

            try {
                int bookId =
                        Integer.parseInt(
                                txtBookId.getText().trim()
                        );

                int days =
                        (Integer) cmbDays.getSelectedItem();

                BorrowService service =
                        new BorrowService();

                boolean success =
                        service.borrowBook(user, bookId, days);

                JOptionPane.showMessageDialog(
                        this,
                        success ? "借書成功！" : "借書失敗，請確認書籍狀態、天數或借閱上限"
                );

                lblRemain.setText(getRemainText());

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Book ID格式錯誤"
                );
            }
        });
    }

    private String getRemainText(){

        BorrowService service =
                new BorrowService();

        int borrowed =
        		service.countCurrentBorrowedBooks(
        		        user.getUserId()
        		);

        int limit =
                user.getRoleLevel().equals("VIP")
                        ? 10
                        : 5;

        int remain =
                limit - borrowed;

        return "尚可借 " + remain + " 本書";
    }

    public JButton getBtnHome() {
        return btnHome;
    }
}