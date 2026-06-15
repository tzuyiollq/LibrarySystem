package view.adminpanel;

import dao.DBConnection;
import view.components.AdminButton;
import view.components.AdminStyle;
import view.components.UIStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminBooksPanel extends JPanel {

    private DefaultTableModel model;

    public AdminBooksPanel() {

        setLayout(new BorderLayout(20, 20));
        setBackground(AdminStyle.BG);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = AdminStyle.title("書籍管理");
        add(title, BorderLayout.NORTH);

        String[] columns = {
                "Book ID", "書名", "作者", "出版社", "出版年", "狀態"
        };

        model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        AdminStyle.applyTable(table);

        JPanel tableCard = AdminStyle.card();
        tableCard.setLayout(new BorderLayout());
        tableCard.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new GridLayout(2, 1, 0, 10));
        bottom.setBackground(AdminStyle.BG);

        JTextField txtTitle = UIStyle.textField();
        JTextField txtAuthor = UIStyle.textField();
        JTextField txtPublisher = UIStyle.textField();
        JTextField txtYear = UIStyle.textField();
        JTextField txtRemoveId = UIStyle.textField();

        txtTitle.setPreferredSize(new Dimension(150, 38));
        txtAuthor.setPreferredSize(new Dimension(130, 38));
        txtPublisher.setPreferredSize(new Dimension(130, 38));
        txtYear.setPreferredSize(new Dimension(90, 38));
        txtRemoveId.setPreferredSize(new Dimension(120, 38));

        JButton btnAdd = new AdminButton("新增");
        JButton btnRemove = new AdminButton("下架");
        JButton btnRefresh = new AdminButton("重新整理");

        btnAdd.setPreferredSize(new Dimension(90, 38));
        btnRemove.setPreferredSize(new Dimension(90, 38));
        btnRefresh.setPreferredSize(new Dimension(110, 38));

        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 5));
        addPanel.setBackground(AdminStyle.BG);

        addPanel.add(AdminStyle.label("書名"));
        addPanel.add(txtTitle);
        addPanel.add(AdminStyle.label("作者"));
        addPanel.add(txtAuthor);
        addPanel.add(AdminStyle.label("出版社"));
        addPanel.add(txtPublisher);
        addPanel.add(AdminStyle.label("年份"));
        addPanel.add(txtYear);
        addPanel.add(btnAdd);

        JPanel removePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 5));
        removePanel.setBackground(AdminStyle.BG);

        removePanel.add(AdminStyle.label("Book ID"));
        removePanel.add(txtRemoveId);
        removePanel.add(btnRemove);
        removePanel.add(btnRefresh);

        bottom.add(addPanel);
        bottom.add(removePanel);

        add(tableCard, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        loadBooks();

        btnRefresh.addActionListener(e -> loadBooks());

        btnAdd.addActionListener(e -> {
            addBook(
                    txtTitle.getText().trim(),
                    txtAuthor.getText().trim(),
                    txtPublisher.getText().trim(),
                    txtYear.getText().trim()
            );

            txtTitle.setText("");
            txtAuthor.setText("");
            txtPublisher.setText("");
            txtYear.setText("");

            loadBooks();
        });

        btnRemove.addActionListener(e -> {
            try {
                int bookId = Integer.parseInt(
                        txtRemoveId.getText().trim()
                );

                removeBook(bookId);
                txtRemoveId.setText("");
                loadBooks();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "請輸入正確 Book ID"
                );
            }
        });
    }

    private void loadBooks() {

        model.setRowCount(0);

        String sql = """
            SELECT book_id, title, authors, publisher, publish_year, status
            FROM books
            ORDER BY book_id DESC
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("authors"),
                        rs.getString("publisher"),
                        rs.getString("publish_year"),
                        rs.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addBook(
            String title,
            String authors,
            String publisher,
            String year
    ) {
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "書名不可空白");
            return;
        }

        String sql = """
            INSERT INTO books
            (title, authors, publisher, publish_year, status)
            VALUES (?, ?, ?, ?, 'AVAILABLE')
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, title);
            stmt.setString(2, authors);
            stmt.setString(3, publisher);
            stmt.setString(4, year);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "新增成功！");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "新增失敗！");
        }
    }

    private void removeBook(int bookId) {

        String sql = """
            UPDATE books
            SET status = 'REMOVED'
            WHERE book_id = ?
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, bookId);

            int count = stmt.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    count > 0 ? "下架成功！" : "查無此書"
            );

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "下架失敗！");
        }
    }
}