package controller;

import model.Book;
import service.AdminService;
import view.AdminAddBookFrame;

import javax.swing.JOptionPane;
import java.util.Arrays;
import java.util.List;

public class AdminAddBookController {

    private AdminAddBookFrame frame;
    private AdminService adminService;

    public AdminAddBookController(AdminAddBookFrame frame) {
        this.frame = frame;
        this.adminService = new AdminService();

        initEvents();
    }

    private void initEvents() {

        frame.getBtnAdd().addActionListener(e -> {

            Book book = new Book(
                    0,
                    frame.getTxtTitle().getText(),
                    frame.getTxtAuthors().getText(),
                    frame.getTxtSubjects().getText(),
                    frame.getTxtPublisher().getText(),
                    frame.getTxtPublishYear().getText(),
                    frame.getTxtEdition().getText(),
                    frame.getTxtFormatDesc().getText(),
                    frame.getTxtSource().getText(),
                    frame.getTxtNote().getText(),
                    "AVAILABLE"
            );

            List<String> isbns =
                    Arrays.asList(frame.getTxtIsbns().getText().split(","));

            boolean result = adminService.addBook(book, isbns);

            JOptionPane.showMessageDialog(
                    frame,
                    result ? "書籍新增成功！" : "新增失敗！"
            );
        });
    }
}