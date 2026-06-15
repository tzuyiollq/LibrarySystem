package view.panel;

import model.BookReview;
import model.User;
import service.BookReviewService;
import view.components.ModernButton;
import view.components.UIStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookReviewPanel extends JPanel {

    private User user;
    private BookReviewService bookReviewService = new BookReviewService();

    private DefaultTableModel model;
    private JTextField txtBookId;
    private JTextField txtComment;
    private JComboBox<Integer> cmbRating;

    private JLabel lblAverage;
    private JLabel lblCount;

    private JButton btnHome;

    public BookReviewPanel(User user) {

        this.user = user;

        setLayout(new BorderLayout(18, 18));
        setBackground(UIStyle.BG);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(UIStyle.BG);

        btnHome = new ModernButton("← 回首頁");
        btnHome.setPreferredSize(new Dimension(130, 42));
        topPanel.add(btnHome);

        JPanel formCard = UIStyle.card();
        formCard.setLayout(new BorderLayout(12, 12));

        JLabel title = UIStyle.title("書評系統");
        formCard.add(title, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        inputPanel.setBackground(Color.WHITE);

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        row1.setBackground(Color.WHITE);

        txtBookId = UIStyle.textField();
        txtBookId.setPreferredSize(new Dimension(120, 40));

        cmbRating = UIStyle.comboBox(new Integer[]{1, 2, 3, 4, 5});
        cmbRating.setPreferredSize(new Dimension(90, 40));

        txtComment = UIStyle.textField();
        txtComment.setPreferredSize(new Dimension(250, 40));

        row1.add(UIStyle.label("Book ID："));
        row1.add(txtBookId);
        row1.add(UIStyle.label("評分："));
        row1.add(cmbRating);
        row1.add(UIStyle.label("評論："));
        row1.add(txtComment);

        JPanel rowAvg = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 2));
        rowAvg.setBackground(Color.WHITE);

        lblAverage = UIStyle.label("平均評分：請輸入 Book ID 後查詢");
        lblAverage.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 16));

        lblCount = UIStyle.label("共 0 則書評");
        lblCount.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 15));

        rowAvg.add(lblAverage);
        rowAvg.add(lblCount);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 4));
        row2.setBackground(Color.WHITE);

        JButton btnAdd = new ModernButton("新增書評");
        JButton btnSearch = new ModernButton("查詢此書");
        JButton btnAll = new ModernButton("全部書評");
        JButton btnDelete = new ModernButton("刪除我的書評");

        btnAdd.setPreferredSize(new Dimension(130, 40));
        btnSearch.setPreferredSize(new Dimension(120, 40));
        btnAll.setPreferredSize(new Dimension(120, 40));
        btnDelete.setPreferredSize(new Dimension(150, 40));

        row2.add(btnAdd);
        row2.add(btnSearch);
        row2.add(btnAll);
        row2.add(btnDelete);

        inputPanel.add(row1);
        inputPanel.add(rowAvg);
        inputPanel.add(row2);

        formCard.add(inputPanel, BorderLayout.CENTER);

        JPanel northWrapper = new JPanel(new BorderLayout(0, 14));
        northWrapper.setBackground(UIStyle.BG);
        northWrapper.add(topPanel, BorderLayout.NORTH);
        northWrapper.add(formCard, BorderLayout.CENTER);

        String[] columns = {
                "書評ID", "Book ID", "書名", "使用者", "評分", "評論", "時間"
        };

        model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        UIStyle.applyTable(table);

        JPanel tableCard = UIStyle.card();
        tableCard.setLayout(new BorderLayout());
        tableCard.add(UIStyle.scrollPane(table), BorderLayout.CENTER);

        add(northWrapper, BorderLayout.NORTH);
        add(tableCard, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addReview());
        btnSearch.addActionListener(e -> searchByBookId());
        btnAll.addActionListener(e -> {
            loadAllReviews();
            lblAverage.setText("平均評分：請輸入 Book ID 後查詢");
            lblCount.setText("共 0 則書評");
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "請先選擇要刪除的書評");
                return;
            }

            int reviewId = Integer.parseInt(
                    table.getValueAt(row, 0).toString()
            );

            boolean result =
                    bookReviewService.deleteReviewByUser(reviewId, user);

            JOptionPane.showMessageDialog(
                    this,
                    result ? "已刪除您的書評。" : "刪除失敗，只能刪除自己的書評。"
            );

            searchByBookIdIfPossible();
        });

        loadAllReviews();
    }

    private void addReview() {

        try {
            int bookId = Integer.parseInt(txtBookId.getText().trim());
            int rating = (Integer) cmbRating.getSelectedItem();
            String comment = txtComment.getText().trim();

            boolean result =
                    bookReviewService.addReview(user, bookId, rating, comment);

            JOptionPane.showMessageDialog(
                    this,
                    result ? "感謝您的分享，書評已送出。" : "無法送出書評，請確認內容是否完整。"
            );

            txtComment.setText("");

            loadReviewsByBook(bookId);
            updateAverage(bookId);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "請確認 Book ID 與評論內容");
        }
    }

    private void searchByBookId() {

        try {
            int bookId = Integer.parseInt(txtBookId.getText().trim());

            loadReviewsByBook(bookId);
            updateAverage(bookId);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "請輸入正確 Book ID");
        }
    }

    private void searchByBookIdIfPossible() {
        try {
            int bookId = Integer.parseInt(txtBookId.getText().trim());

            loadReviewsByBook(bookId);
            updateAverage(bookId);

        } catch (Exception ex) {
            loadAllReviews();
            lblAverage.setText("平均評分：請輸入 Book ID 後查詢");
            lblCount.setText("共 0 則書評");
        }
    }

    private void updateAverage(int bookId) {

        List<BookReview> reviews =
                bookReviewService.getReviewsByBookId(bookId);

        if (reviews.isEmpty()) {
            lblAverage.setText("平均評分：尚無評分");
            lblCount.setText("共 0 則書評");
            return;
        }

        double sum = 0;

        for (BookReview r : reviews) {
            sum += r.getRating();
        }

        double avg = sum / reviews.size();

        lblAverage.setText(
                "平均評分：" +
                        getStars(avg) +
                        " " +
                        String.format("%.1f", avg) +
                        " / 5.0"
        );

        lblCount.setText(
                "共 " + reviews.size() + " 則書評"
        );
    }

    private String getStars(double avg) {

        int stars = (int) Math.round(avg);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < stars; i++) {
            sb.append("★");
        }

        for (int i = stars; i < 5; i++) {
            sb.append("☆");
        }

        return sb.toString();
    }

    private void loadAllReviews() {
        model.setRowCount(0);

        List<BookReview> reviews =
                bookReviewService.getAllReviews();

        for (BookReview r : reviews) {
            addRow(r);
        }
    }

    private void loadReviewsByBook(int bookId) {
        model.setRowCount(0);

        List<BookReview> reviews =
                bookReviewService.getReviewsByBookId(bookId);

        for (BookReview r : reviews) {
            addRow(r);
        }
    }

    private void addRow(BookReview r) {
        model.addRow(new Object[]{
                r.getReviewId(),
                r.getBookId(),
                r.getBookTitle(),
                r.getUserName(),
                r.getRating(),
                r.getComment(),
                r.getCreatedAt()
        });
    }

    public JButton getBtnHome() {
        return btnHome;
    }
}