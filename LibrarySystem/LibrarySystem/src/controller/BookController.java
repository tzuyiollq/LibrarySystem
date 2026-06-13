package controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Book;
import service.BookService;
import view.BookSearchFrame;

public class BookController {

    private BookSearchFrame frame;
    private BookService bookService;

    public BookController(BookSearchFrame frame) {

        this.frame = frame;
        this.bookService = new BookService();

        initEvents();
    }

    private void initEvents() {

        frame.getBtnSearch().addActionListener(e -> {

            String keyword =
                    frame.getTxtKeyword().getText();

            List<Book> books =
                    bookService.searchBookList(keyword);

            DefaultTableModel model =
                    frame.getTableModel();

            model.setRowCount(0);

            for (Book b : books) {

                model.addRow(new Object[]{
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
                JOptionPane.showMessageDialog(
                        frame,
                        "查無資料"
                );
            }
        });
    }
}