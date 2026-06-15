package view.panel;

import model.Book;
import model.BookReview;
import model.User;
import service.BookService;
import service.BorrowService;
import service.FavoriteService;
import service.ReservationService;
import service.BookReviewService;
import view.components.ModernButton;
import view.components.UIStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookSearchPanel extends JPanel {

    private User user;

    private BookService bookService = new BookService();
    private BorrowService borrowService = new BorrowService();
    private FavoriteService favoriteService = new FavoriteService();
    private ReservationService reservationService = new ReservationService();
    private BookReviewService bookReviewService = new BookReviewService();

    private JTextField txtKeyword;
    private JTable table;
    private DefaultTableModel tableModel;

    private JButton btnSearch;
    private JButton btnBorrow;
    private JButton btnReserve;
    private JButton btnFavorite;
    private JButton btnReview;
    private JButton btnViewReview;

    public BookSearchPanel(User user) {

        this.user = user;

        setLayout(new BorderLayout(20, 20));
        setBackground(UIStyle.BG);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JPanel searchCard = UIStyle.card();
        searchCard.setLayout(new BorderLayout(15, 15));

        JLabel title = UIStyle.title("書籍查詢");
        searchCard.add(title, BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        topPanel.setBackground(Color.WHITE);

        txtKeyword = UIStyle.textField();
        txtKeyword.setPreferredSize(new Dimension(320, 42));

        btnSearch = new ModernButton("查詢");
        btnSearch.setPreferredSize(new Dimension(120, 42));

        topPanel.add(UIStyle.label("關鍵字："));
        topPanel.add(txtKeyword);
        topPanel.add(btnSearch);

        searchCard.add(topPanel, BorderLayout.CENTER);

        String[] columns = {
                "Book ID",
                "書名",
                "作者",
                "主題",
                "出版社",
                "出版年",
                "ISBN",
                "狀態"
        };

        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        UIStyle.applyTable(table);

        JPanel tableCard = UIStyle.card();
        tableCard.setLayout(new BorderLayout());
        tableCard.add(UIStyle.scrollPane(table), BorderLayout.CENTER);

        JPanel bottomCard = UIStyle.card();
        bottomCard.setLayout(new FlowLayout(FlowLayout.CENTER, 18, 8));

        btnBorrow = new ModernButton("借書");
        btnReserve = new ModernButton("預約");
        btnFavorite = new ModernButton("收藏");
        btnReview = new ModernButton("寫書評");
        btnViewReview = new ModernButton("查看書評");

        btnBorrow.setPreferredSize(new Dimension(120, 42));
        btnReserve.setPreferredSize(new Dimension(120, 42));
        btnFavorite.setPreferredSize(new Dimension(120, 42));
        btnReview.setPreferredSize(new Dimension(120, 42));
        btnViewReview.setPreferredSize(new Dimension(140, 42));

        bottomCard.add(btnBorrow);
        bottomCard.add(btnReserve);
        bottomCard.add(btnFavorite);
        bottomCard.add(btnReview);
        bottomCard.add(btnViewReview);

        add(searchCard, BorderLayout.NORTH);
        add(tableCard, BorderLayout.CENTER);
        add(bottomCard, BorderLayout.SOUTH);

        initEvents();
    }

    private void initEvents() {

        btnSearch.addActionListener(e -> searchBooks());

        btnBorrow.addActionListener(e -> borrowSelectedBook());

        btnReserve.addActionListener(e -> reserveSelectedBook());

        btnFavorite.addActionListener(e -> favoriteSelectedBook());

        btnReview.addActionListener(e -> reviewSelectedBook());

        btnViewReview.addActionListener(e -> viewSelectedBookReviews());
    }

    private void searchBooks() {

        String keyword = txtKeyword.getText().trim();

        List<Book> books = bookService.searchBookList(keyword);

        tableModel.setRowCount(0);

        for (Book b : books) {
            tableModel.addRow(new Object[]{
                    b.getBookId(),
                    b.getTitle(),
                    b.getAuthors(),
                    b.getSubjects(),
                    b.getPublisher(),
                    b.getPublishYear(),
                    b.getIsbns(),
                    b.getStatus()
            });
        }

        if (books.isEmpty()) {
            JOptionPane.showMessageDialog(this, "查無資料");
        }
    }

    private int getSelectedBookId() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "請先選擇一本書");
            return -1;
        }

        return Integer.parseInt(
                table.getValueAt(row, 0).toString()
        );
    }

    private String getSelectedBookTitle() {

        int row = table.getSelectedRow();

        if (row == -1) {
            return "";
        }

        return table.getValueAt(row, 1).toString();
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

        searchBooks();
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

        searchBooks();
    }

    private void favoriteSelectedBook() {

        int bookId = getSelectedBookId();

        if (bookId == -1) {
            return;
        }

        boolean result =
                favoriteService.addFavorite(user, bookId);

        JOptionPane.showMessageDialog(
                this,
                result
                        ? "已加入我的收藏。"
                        : "此書已在您的收藏清單中。"
        );
    }

    private void reviewSelectedBook() {

        int bookId = getSelectedBookId();

        if (bookId == -1) {
            return;
        }

        JTextField txtComment = UIStyle.textField();

        JComboBox<Integer> cmbRating =
                UIStyle.comboBox(new Integer[]{1, 2, 3, 4, 5});

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBackground(Color.WHITE);

        panel.add(UIStyle.label("評分："));
        panel.add(cmbRating);

        panel.add(UIStyle.label("評論："));
        panel.add(txtComment);

        int option = JOptionPane.showConfirmDialog(
                this,
                panel,
                "新增書評",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (option != JOptionPane.OK_OPTION) {
            return;
        }

        int rating =
                (Integer) cmbRating.getSelectedItem();

        String comment =
                txtComment.getText().trim();

        boolean result =
                bookReviewService.addReview(
                        user,
                        bookId,
                        rating,
                        comment
                );

        JOptionPane.showMessageDialog(
                this,
                result
                        ? "感謝您的分享，書評已送出。"
                        : "書評新增失敗，請確認內容"
        );
    }

    private void viewSelectedBookReviews() {

        int bookId = getSelectedBookId();

        if (bookId == -1) {
            return;
        }

        String bookTitle = getSelectedBookTitle();

        List<BookReview> reviews =
                bookReviewService.getReviewsByBookId(bookId);

        JDialog dialog =
                new JDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "《" + bookTitle + "》書評",
                        Dialog.ModalityType.APPLICATION_MODAL
                );

        dialog.setSize(800, 500);
        dialog.setLocationRelativeTo(this);

        JPanel main = new JPanel(new BorderLayout(16, 16));
        main.setBackground(UIStyle.BG);
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        double avgRating =
                bookReviewService.getAverageRating(bookId);

        JLabel title =
                UIStyle.title("《" + bookTitle + "》書評");

        JLabel lblAverage =
                new JLabel(
                        "平均評分：" +
                        getStars(avgRating) +
                        " (" +
                        String.format("%.1f", avgRating) +
                        " / 5.0)"
                );

        lblAverage.setFont(
                new Font("微軟正黑體", Font.BOLD, 18)
        );

        JLabel lblCount =
                new JLabel(
                        "共 " + reviews.size() + " 則書評"
                );

        lblCount.setFont(
                new Font("微軟正黑體", Font.PLAIN, 16)
        );

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(
                new BoxLayout(
                        headerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        headerPanel.setBackground(UIStyle.BG);

        headerPanel.add(title);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(lblAverage);
        headerPanel.add(lblCount);

        main.add(headerPanel, BorderLayout.NORTH);

        String[] columns = {
                "書評ID",
                "使用者",
                "評分",
                "評論",
                "時間"
        };

        DefaultTableModel reviewModel =
                new DefaultTableModel(columns, 0);

        JTable reviewTable =
                new JTable(reviewModel);

        UIStyle.applyTable(reviewTable);

        for (BookReview r : reviews) {
            reviewModel.addRow(new Object[]{
                    r.getReviewId(),
                    r.getUserName(),
                    r.getRating(),
                    r.getComment(),
                    r.getCreatedAt()
            });
        }

        if (reviews.isEmpty()) {
            reviewModel.addRow(new Object[]{
                    "",
                    "",
                    "",
                    "目前還沒有書評",
                    ""
            });
        }

        JPanel tableCard = UIStyle.card();
        tableCard.setLayout(new BorderLayout());
        tableCard.add(UIStyle.scrollPane(reviewTable), BorderLayout.CENTER);

        ModernButton btnClose = new ModernButton("關閉");
        btnClose.setPreferredSize(new Dimension(120, 42));
        btnClose.addActionListener(e -> dialog.dispose());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(UIStyle.BG);
        bottom.add(btnClose);

        main.add(tableCard, BorderLayout.CENTER);
        main.add(bottom, BorderLayout.SOUTH);

        dialog.setContentPane(main);
        dialog.setVisible(true);
    }
    private String getStars(double avg) {

        int stars =
                (int)Math.round(avg);

        StringBuilder sb =
                new StringBuilder();

        for(int i=0;i<stars;i++) {
            sb.append("★");
        }

        for(int i=stars;i<5;i++) {
            sb.append("☆");
        }

        return sb.toString();
    }
}