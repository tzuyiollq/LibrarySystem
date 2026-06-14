package view.panel;

import model.BookReview;
import model.User;
import service.BookReviewService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookReviewPanel extends JPanel {

    private User user;
    private BookReviewService bookReviewService =
            new BookReviewService();

    private DefaultTableModel model;
    private JTextField txtBookId;

    public BookReviewPanel(User user) {

        this.user = user;

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());

        txtBookId = new JTextField(8);
        JComboBox<Integer> cmbRating =
                new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        JTextField txtComment = new JTextField(25);

        JButton btnAdd = new JButton("新增書評");
        JButton btnSearch = new JButton("查看書評");

        topPanel.add(new JLabel("Book ID："));
        topPanel.add(txtBookId);
        topPanel.add(new JLabel("評分："));
        topPanel.add(cmbRating);
        topPanel.add(new JLabel("評論："));
        topPanel.add(txtComment);
        topPanel.add(btnAdd);
        topPanel.add(btnSearch);

        String[] columns = {
                "書評ID", "Book ID", "書名", "使用者", "評分", "評論", "時間"
        };

        model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> {
            try {
                int bookId = Integer.parseInt(txtBookId.getText());
                int rating = (Integer) cmbRating.getSelectedItem();
                String comment = txtComment.getText();

                boolean result =
                        bookReviewService.addReview(
                                user,
                                bookId,
                                rating,
                                comment
                        );

                JOptionPane.showMessageDialog(
                        this,
                        result ? "書評新增成功！" : "書評新增失敗"
                );

                loadReviewsByBook(bookId);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "請確認 Book ID 與評論內容");
            }
        });

        btnSearch.addActionListener(e -> {
            try {
                int bookId = Integer.parseInt(txtBookId.getText());
                loadReviewsByBook(bookId);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "請輸入正確 Book ID");
            }
        });
    }

    private void loadReviewsByBook(int bookId) {

        model.setRowCount(0);

        List<BookReview> reviews =
                bookReviewService.getReviewsByBookId(bookId);

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