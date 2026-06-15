package view.adminpanel;

import model.BookReview;
import service.BookReviewService;
import view.components.AdminButton;
import view.components.AdminStyle;
import view.components.UIStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminReviewPanel extends JPanel {

    private BookReviewService bookReviewService =
            new BookReviewService();

    private DefaultTableModel model;

    public AdminReviewPanel() {

        setLayout(new BorderLayout(20, 20));
        setBackground(AdminStyle.BG);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel top = AdminStyle.card();
        top.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 8));

        JTextField txtReviewId = UIStyle.textField();
        txtReviewId.setPreferredSize(new Dimension(140, 38));

        JButton btnDelete = new AdminButton("刪除書評");
        JButton btnRefresh = new AdminButton("重新整理");

        btnDelete.setPreferredSize(new Dimension(120, 38));
        btnRefresh.setPreferredSize(new Dimension(120, 38));

        top.add(AdminStyle.title("書評管理"));
        top.add(AdminStyle.label("書評ID："));
        top.add(txtReviewId);
        top.add(btnDelete);
        top.add(btnRefresh);

        String[] columns = {
                "書評ID", "Book ID", "書名", "使用者", "評分", "評論", "時間"
        };

        model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        AdminStyle.applyTable(table);

        JPanel tableCard = AdminStyle.card();
        tableCard.setLayout(new BorderLayout());
        tableCard.add(new JScrollPane(table), BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);
        add(tableCard, BorderLayout.CENTER);

        loadReviews();

        btnRefresh.addActionListener(e -> loadReviews());

        btnDelete.addActionListener(e -> {

            try {
                int reviewId =
                        Integer.parseInt(
                                txtReviewId.getText().trim()
                        );

                boolean result =
                        bookReviewService.deleteReviewByAdmin(
                                reviewId
                        );

                JOptionPane.showMessageDialog(
                        this,
                        result
                                ? "書評已刪除。"
                                : "刪除失敗，查無此書評ID。"
                );

                loadReviews();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "請輸入正確書評ID"
                );
            }
        });
    }

    private void loadReviews() {

        model.setRowCount(0);

        List<BookReview> reviews =
                bookReviewService.getAllReviews();

        for (BookReview r : reviews) {
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
    }
}