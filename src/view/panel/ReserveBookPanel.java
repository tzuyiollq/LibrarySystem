package view.panel;

import model.User;
import service.ReservationService;
import view.components.ModernButton;
import view.components.UIStyle;

import javax.swing.*;
import java.awt.*;

public class ReserveBookPanel extends JPanel {

    private ReservationService reservationService =
            new ReservationService();

    private JButton btnHome;

    public ReserveBookPanel(User user) {

        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);

        //--------------------------------
        // 上方
        //--------------------------------

        JPanel topPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        topPanel.setBackground(UIStyle.BG);

        btnHome =
                new ModernButton(
                        "← 回首頁"
                );

        btnHome.setPreferredSize(
                new Dimension(
                        130,
                        42
                )
        );

        topPanel.add(btnHome);

        add(topPanel, BorderLayout.NORTH);

        //--------------------------------
        // 中央卡片
        //--------------------------------

        JPanel centerPanel =
                new JPanel(
                        new GridBagLayout()
                );

        centerPanel.setBackground(UIStyle.BG);

        JPanel card =
                UIStyle.card();

        card.setPreferredSize(
                new Dimension(
                        650,
                        420
                )
        );

        card.setLayout(null);

        JLabel title =
                UIStyle.title(
                        "預約書籍"
                );

        title.setBounds(
                250,
                30,
                200,
                40
        );

        card.add(title);

        //--------------------------------
        // Book ID
        //--------------------------------

        JLabel lblBookId =
                UIStyle.label(
                        "Book ID"
                );

        lblBookId.setBounds(
                120,
                120,
                120,
                35
        );

        card.add(lblBookId);

        JTextField txtBookId =
                UIStyle.textField();

        txtBookId.setBounds(
                240,
                120,
                250,
                40
        );

        card.add(txtBookId);

        //--------------------------------
        // 提示
        //--------------------------------

        JLabel lblTip =
                new JLabel(
                        "僅可預約目前已被借出的書籍"
                );

        lblTip.setForeground(
                UIStyle.SUB_TEXT
        );

        lblTip.setBounds(
                190,
                200,
                300,
                30
        );

        card.add(lblTip);

        //--------------------------------
        // 按鈕
        //--------------------------------

        ModernButton btnReserve =
                new ModernButton(
                        "預約"
                );

        btnReserve.setBounds(
                250,
                280,
                120,
                48
        );

        card.add(btnReserve);

        centerPanel.add(card);

        add(centerPanel, BorderLayout.CENTER);

        //--------------------------------
        // 事件
        //--------------------------------

        btnReserve.addActionListener(e -> {

            try {

                int bookId =
                        Integer.parseInt(
                                txtBookId.getText().trim()
                        );

                boolean result =
                        reservationService.reserveBook(
                                user,
                                bookId
                        );

                JOptionPane.showMessageDialog(
                        this,
                        result
                        ? "預約成功！書籍歸還後將優先通知您。"
                        : """
                        無法完成預約。

                        可能原因：
                        • 書籍目前可直接借閱
                        • 您已預約過此書
                        • 此書不允許預約
                        """
                );

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "請輸入正確 Book ID"
                );
            }
        });
    }

    public JButton getBtnHome() {
        return btnHome;
    }
}