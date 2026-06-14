package view.panel;

import model.Book;
import model.User;
import service.BookService;
import service.BorrowService;
import service.FavoriteService;
import service.ReservationService;
import service.BookReviewService;

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

    public BookSearchPanel(User user) {

        this.user = user;

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());

        txtKeyword = new JTextField(25);
        btnSearch = new JButton("查詢");

        topPanel.add(new JLabel("關鍵字："));
        topPanel.add(txtKeyword);
        topPanel.add(btnSearch);

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

        JPanel bottomPanel = new JPanel(new FlowLayout());

        btnBorrow = new JButton("借書");
        btnReserve = new JButton("預約");
        btnFavorite = new JButton("收藏");
        btnReview = new JButton("寫書評");

        bottomPanel.add(btnBorrow);
        bottomPanel.add(btnReserve);
        bottomPanel.add(btnFavorite);
        bottomPanel.add(btnReview);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        initEvents();
    }

    private void initEvents() {

        btnSearch.addActionListener(e -> searchBooks());

        btnBorrow.addActionListener(e -> borrowSelectedBook());

        btnReserve.addActionListener(e -> reserveSelectedBook());

        btnFavorite.addActionListener(e -> favoriteSelectedBook());

        btnReview.addActionListener(e -> reviewSelectedBook());
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
                result ? "借書成功！" : "借書失敗，請確認書籍狀態、天數或借閱上限"
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
                result ? "預約成功！" : "預約失敗，可能書籍尚可借、已預約或狀態不允許"
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
                result ? "收藏成功！" : "收藏失敗，可能已收藏過"
        );
    }

    private void reviewSelectedBook() {

        int bookId = getSelectedBookId();

        if (bookId == -1) {
            return;
        }

        JTextField txtComment = new JTextField();

        JComboBox<Integer> cmbRating =
                new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        panel.add(new JLabel("評分："));
        panel.add(cmbRating);

        panel.add(new JLabel("評論："));
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
                result ? "書評新增成功！" : "書評新增失敗，請確認內容"
        );
    }
}