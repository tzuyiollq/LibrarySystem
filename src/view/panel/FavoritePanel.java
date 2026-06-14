package view.panel;

import model.FavoriteBook;
import model.User;
import service.FavoriteService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FavoritePanel extends JPanel {

    private User user;
    private FavoriteService favoriteService = new FavoriteService();

    private DefaultTableModel model;

    public FavoritePanel(User user) {

        this.user = user;

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());

        JTextField txtBookId = new JTextField(10);
        JButton btnAdd = new JButton("新增收藏");

        topPanel.add(new JLabel("Book ID："));
        topPanel.add(txtBookId);
        topPanel.add(btnAdd);

        String[] columns = {
                "收藏ID", "Book ID", "書名", "作者", "收藏時間"
        };

        model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        JPanel bottomPanel = new JPanel(new FlowLayout());

        JTextField txtFavoriteId = new JTextField(10);
        JButton btnRemove = new JButton("取消收藏");

        bottomPanel.add(new JLabel("收藏ID："));
        bottomPanel.add(txtFavoriteId);
        bottomPanel.add(btnRemove);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        loadData();

        btnAdd.addActionListener(e -> {
            try {
                int bookId = Integer.parseInt(txtBookId.getText());

                boolean result =
                        favoriteService.addFavorite(user, bookId);

                JOptionPane.showMessageDialog(
                        this,
                        result ? "收藏成功！" : "收藏失敗，可能已收藏過"
                );

                loadData();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "請輸入正確 Book ID");
            }
        });

        btnRemove.addActionListener(e -> {
            try {
                int favoriteId = Integer.parseInt(txtFavoriteId.getText());

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
}