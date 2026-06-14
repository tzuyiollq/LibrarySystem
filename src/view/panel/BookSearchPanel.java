package view.panel;

import model.Book;
import service.BookService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookSearchPanel extends JPanel {

    private JTextField txtKeyword;
    private JButton btnSearch;
    private JButton btnHome;
    private JTable table;
    private DefaultTableModel tableModel;

    public BookSearchPanel() {

        setLayout(null);
        setBackground(Color.WHITE);

        btnHome = new JButton("← 回首頁");
        btnHome.setBounds(20, 20, 120, 35);
        add(btnHome);

        JLabel lblKeyword = new JLabel("關鍵字：");
        lblKeyword.setFont(new Font("微軟正黑體", Font.BOLD, 26));
        lblKeyword.setBounds(260, 80, 130, 40);
        add(lblKeyword);

        txtKeyword = new JTextField();
        txtKeyword.setFont(new Font("微軟正黑體", Font.PLAIN, 22));
        txtKeyword.setBounds(390, 80, 360, 40);
        add(txtKeyword);

        btnSearch = new JButton("🔍");
        btnSearch.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        btnSearch.setBounds(760, 80, 60, 40);
        add(btnSearch);

        String[] columns = {
                "Book ID", "書名", "作者", "主題", "出版社", "出版年", "ISBN", "狀態"
        };

        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(28);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 160, 820, 420);
        add(scrollPane);

        btnSearch.addActionListener(e -> loadBooks());
    }

    private void loadBooks() {

        String keyword = txtKeyword.getText().trim();

        BookService bookService = new BookService();
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

    public JButton getBtnHome() {
        return btnHome;
    }
}