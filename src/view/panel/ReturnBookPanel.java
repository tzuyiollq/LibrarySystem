package view.panel;

import model.User;
import service.BorrowService;
import view.components.ModernButton;
import view.components.UIStyle;

import javax.swing.*;
import java.awt.*;

public class ReturnBookPanel extends JPanel {

    private User user;

    private JTextField txtBookId;
    private JButton btnReturn;
    private JButton btnHome;

    public ReturnBookPanel(User user) {
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
        card.setPreferredSize(new Dimension(520, 360));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 14, 14, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = UIStyle.title("還書");
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

        btnReturn = new ModernButton("還書");
        btnReturn.setPreferredSize(new Dimension(180, 48));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        card.add(btnReturn, gbc);

        centerWrapper.add(card);

        add(topPanel, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);

        btnReturn.addActionListener(e -> returnBook());
    }

    private void returnBook() {
        try {
            int bookId = Integer.parseInt(
                    txtBookId.getText().trim()
            );

            BorrowService borrowService = new BorrowService();

            boolean success =
                    borrowService.returnBook(user, bookId);

            JOptionPane.showMessageDialog(
                    this,
                    success
                    ? "感謝您準時歸還書籍！"
                    : """
                    找不到可歸還的借閱紀錄。

                    請確認：
                    • Book ID 是否正確
                    • 此書是否由您借閱
                    • 是否已完成歸還
                    """
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "請輸入正確的 Book ID"
            );
        }
    }

    public JButton getBtnHome() {
        return btnHome;
    }
}