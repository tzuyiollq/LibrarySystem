package controller;

import model.Book;
import service.BookService;
import view.BookSearchFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class BookController {
    private BookSearchFrame frame;
    private BookService bookService;
    public BookController(BookSearchFrame frame) { this.frame = frame; this.bookService = new BookService(); initEvents(); }
    private void initEvents() {
        frame.getBtnSearch().addActionListener(e -> {
            List<Book> books = bookService.searchBookList(frame.getTxtKeyword().getText().trim());
            DefaultTableModel m = frame.getTableModel(); m.setRowCount(0);
            for (Book b : books) m.addRow(new Object[]{b.getBookId(), b.getTitle(), b.getAuthors(), b.getSubjects(), b.getPublisher(), b.getPublishYear(), b.getIsbns(), b.getStatus()});
            if (books.isEmpty()) JOptionPane.showMessageDialog(frame, "查無資料");
        });
    }
}
