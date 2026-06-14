package controller;

import model.HotBook;
import service.BookService;
import view.HotBookFrame;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class HotBookController {

    private HotBookFrame frame;
    private BookService bookService;

    public HotBookController(HotBookFrame frame) {
        this.frame = frame;
        this.bookService = new BookService();

        loadData();
    }

    private void loadData() {

        List<HotBook> books = bookService.getHotBooks();

        DefaultTableModel model = frame.getTableModel();
        model.setRowCount(0);

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