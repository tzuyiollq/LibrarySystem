package view.panel;

import model.FavoriteBook;
import model.User;
import service.FavoriteService;
import service.BorrowService;
import service.ReservationService;
import view.components.ModernButton;
import view.components.UIStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FavoritePanel extends JPanel {

    private User user;

    private FavoriteService favoriteService = new FavoriteService();
    private BorrowService borrowService = new BorrowService();
    private ReservationService reservationService = new ReservationService();

    private DefaultTableModel model;
    private JTable table;

    private JButton btnHome;

    public FavoritePanel(User user) {

        this.user = user;

        setLayout(new BorderLayout(20, 20));
        setBackground(UIStyle.BG);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(UIStyle.BG);

        btnHome = new ModernButton("← 回首頁");
        btnHome.setPreferredSize(new Dimension(130, 42));
        topPanel.add(btnHome);

        JPanel formCard = UIStyle.card();
        formCard.setLayout(new BorderLayout(16, 16));

        JLabel title = UIStyle.title("收藏書籍");
        formCard.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        formPanel.setBackground(Color.WHITE);

        JTextField txtBookId = UIStyle.textField();
        txtBookId.setPreferredSize(new Dimension(200, 42));

        JButton btnAdd = new ModernButton("新增收藏");
        btnAdd.setPreferredSize(new Dimension(140, 42));

        formPanel.add(UIStyle.label("Book ID："));
        formPanel.add(txtBookId);
        formPanel.add(btnAdd);

        formCard.add(formPanel, BorderLayout.CENTER);

        JPanel northWrapper = new JPanel(new BorderLayout(0, 16));
        northWrapper.setBackground(UIStyle.BG);
        northWrapper.add(topPanel, BorderLayout.NORTH);
        northWrapper.add(formCard, BorderLayout.CENTER);

        String[] columns = {
                "收藏ID", "Book ID", "書名", "作者", "收藏時間"
        };

        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        UIStyle.applyTable(table);

        JPanel tableCard = UIStyle.card();
        tableCard.setLayout(new BorderLayout());
        tableCard.add(UIStyle.scrollPane(table), BorderLayout.CENTER);

        JPanel bottomCard = UIStyle.card();
        bottomCard.setLayout(new GridLayout(2, 1, 0, 12));

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        row1.setBackground(Color.WHITE);

        JTextField txtFavoriteId = UIStyle.textField();
        txtFavoriteId.setPreferredSize(new Dimension(160, 42));

        JButton btnRemove = new ModernButton("取消收藏");
        btnRemove.setPreferredSize(new Dimension(140, 42));

        row1.add(UIStyle.label("收藏ID："));
        row1.add(txtFavoriteId);
        row1.add(btnRemove);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 4));
        row2.setBackground(Color.WHITE);

        JButton btnBorrow = new ModernButton("借這本書");
        JButton btnReserve = new ModernButton("預約這本書");

        btnBorrow.setPreferredSize(new Dimension(140, 42));
        btnReserve.setPreferredSize(new Dimension(150, 42));

        row2.add(btnBorrow);
        row2.add(btnReserve);

        bottomCard.add(row1);
        bottomCard.add(row2);

        add(northWrapper, BorderLayout.NORTH);
        add(tableCard, BorderLayout.CENTER);
        add(bottomCard, BorderLayout.SOUTH);

        loadData();

        btnAdd.addActionListener(e -> {
            try {
                int bookId = Integer.parseInt(txtBookId.getText().trim());

                boolean result =
                        favoriteService.addFavorite(user, bookId);

                JOptionPane.showMessageDialog(
                        this,
                        result
                                ? "已加入我的收藏。"
                                : "此書已在您的收藏清單中。"
                );

                loadData();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "請輸入正確 Book ID");
            }
        });

        btnRemove.addActionListener(e -> {
            try {
                int favoriteId =
                        Integer.parseInt(txtFavoriteId.getText().trim());

                boolean result =
                        favoriteService.removeFavorite(
                                favoriteId,
                                user.getUserId()
                        );

                JOptionPane.showMessageDialog(
                        this,
                        result ? "取消收藏成功！" : "取消收藏失敗"
                );

                loadData();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "請輸入正確收藏ID");
            }
        });

        btnBorrow.addActionListener(e -> borrowSelectedBook());

        btnReserve.addActionListener(e -> reserveSelectedBook());
    }

    private int getSelectedBookId() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "請先選擇一本收藏書籍");
            return -1;
        }

        return Integer.parseInt(
                table.getValueAt(row, 1).toString()
        );
    }

    private void borrowSelectedBook() {

        int bookId = getSelectedBookId();

        if (bookId == -1) {
            return;
        }

        Integer[] daysOptions = {1, 3, 7, 14};

        Integer days = (Integer) JOptionPane.showInputDialog(
                this,
                "請選擇借閱天數：",
                "借書",
                JOptionPane.QUESTION_MESSAGE,
                null,
                daysOptions,
                7
        );

        if (days == null) {
            return;
        }

        boolean result =
                borrowService.borrowBook(user, bookId, days);

        JOptionPane.showMessageDialog(
                this,
                result
                        ? "借閱成功，祝您閱讀愉快！"
                        : "借書失敗，請確認書籍狀態、天數或借閱上限"
        );

        loadData();
    }

    private void reserveSelectedBook() {

        int bookId = getSelectedBookId();

        if (bookId == -1) {
            return;
        }

        boolean result =
                reservationService.reserveBook(user, bookId);

        JOptionPane.showMessageDialog(
                this,
                result
                        ? "預約成功！書籍歸還後將優先通知您。"
                        : "預約失敗，可能書籍尚可借、已預約或狀態不允許"
        );

        loadData();
    }

    private void loadData() {

        model.setRowCount(0);

        List<FavoriteBook> favorites =
                favoriteService.getUserFavorites(user.getUserId());

        for (FavoriteBook f : favorites) {
            model.addRow(new Object[]{
                    f.getFavoriteId(),
                    f.getBookId(),
                    f.getTitle(),
                    f.getAuthors(),
                    f.getCreatedAt()
            });
        }
    }

    public JButton getBtnHome() {
        return btnHome;
    }
}