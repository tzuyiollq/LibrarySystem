package view.panel;

import model.HotBook;
import service.BookService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HotBookPanel extends JPanel {

    private BookService bookService = new BookService();

    public HotBookPanel() {

        setLayout(new BorderLayout());

        String[] columns = {
                "排名", "Book ID", "書名", "作者", "借閱次數"
        };

        DefaultTableModel model =
                new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        List<HotBook> books =
                bookService.getHotBooks();

        int rank = 1;

        for (HotBook b : books) {
            model.addRow(new Object[]{
                    rank++,
                    b.getBookId(),
                    b.getTitle(),
                    b.getAuthors(),
                    b.getBorrowCount()
            });
        }
    }
}