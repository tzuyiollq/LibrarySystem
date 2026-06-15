package view.panel;

import model.User;
import service.BorrowService;
import view.components.ModernButton;
import view.components.UIStyle;
import model.BorrowResult;

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

        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(UIStyle.BG);

        btnHome = new ModernButton("← 回首頁");
        btnHome.setPreferredSize(new Dimension(130, 42));
        topPanel.add(btnHome);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(UIStyle.BG);

        JPanel card = UIStyle.card();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(520, 420));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 14, 14, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = UIStyle.title("借書");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        card.add(title, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        card.add(UIStyle.label("Book ID："), gbc);

        txtBookId = UIStyle.textField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        card.add(txtBookId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        card.add(UIStyle.label("借閱天數："), gbc);

        cmbDays = new JComboBox<>(new Integer[]{1, 3, 7, 14});
        cmbDays.setFont(UIStyle.NORMAL_FONT);
        cmbDays.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 2;
        card.add(cmbDays, gbc);

        lblRemain = UIStyle.label(getRemainText());
        lblRemain.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        card.add(lblRemain, gbc);

        btnBorrow = new ModernButton("借書");
        btnBorrow.setPreferredSize(new Dimension(180, 48));

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        card.add(btnBorrow, gbc);

        centerWrapper.add(card);

        add(topPanel, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);

        btnBorrow.addActionListener(e -> borrowBook());
    }

    private void borrowBook() {
        try {
            int bookId = Integer.parseInt(txtBookId.getText().trim());
            int days = (Integer) cmbDays.getSelectedItem();

            BorrowService service = new BorrowService();

            BorrowResult result =
                    service.borrowBookWithResult(user, bookId, days);

            if (result == BorrowResult.SUCCESS) {

                int totalBorrow =
                        service.countUserTotalBorrowRecords(
                                user.getUserId()
                        );

                if (totalBorrow == 67) {
                    show67Effect();
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            result.getMessage()
                    );
                }

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        result.getMessage()
                );
            }
            lblRemain.setText(getRemainText());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Book ID 格式錯誤");
        }
    }

    private String getRemainText() {
        BorrowService service = new BorrowService();

        int borrowed =
                service.countCurrentBorrowedBooks(user.getUserId());

        int limit =
                user.getRoleLevel().equals("VIP") ? 10 : 5;

        int remain = limit - borrowed;

        return "目前已借 " + borrowed + " 本，尚可借 " + remain + " 本";
    }

    public JButton getBtnHome() {
        return btnHome;
    }
    private void show67Effect() {

        JDialog dialog = new JDialog(
                SwingUtilities.getWindowAncestor(this),
                "67 彩蛋",
                Dialog.ModalityType.APPLICATION_MODAL
        );

        dialog.setSize(520, 360);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(255, 248, 220));

        JLabel msg = new JLabel(
                "恭喜！第 67 次借書達成！",
                SwingConstants.CENTER
        );
        msg.setFont(new Font("微軟正黑體", Font.BOLD, 22));
        msg.setBounds(40, 30, 440, 40);

        JLabel lbl6 = new JLabel("6", SwingConstants.CENTER);
        lbl6.setFont(new Font("Arial Black", Font.BOLD, 120));
        lbl6.setForeground(new Color(255, 120, 0));
        lbl6.setBounds(145, 110, 110, 130);

        JLabel lbl7 = new JLabel("7", SwingConstants.CENTER);
        lbl7.setFont(new Font("Arial Black", Font.BOLD, 120));
        lbl7.setForeground(new Color(80, 140, 220));
        lbl7.setBounds(255, 110, 110, 130);

        panel.add(msg);
        panel.add(lbl6);
        panel.add(lbl7);

        dialog.setContentPane(panel);

        Timer timer = new Timer(45, null);

        timer.addActionListener(new java.awt.event.ActionListener() {

            int frame = 0;

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {

                frame++;

                int offset =
                        frame % 2 == 0 ? 28 : -28;

                lbl6.setLocation(145, 110 + offset);
                lbl7.setLocation(255, 110 - offset);

                if (frame >= 70) {
                    timer.stop();
                    dialog.dispose();
                }
            }
        });

        timer.start();
        dialog.setVisible(true);
    }
}