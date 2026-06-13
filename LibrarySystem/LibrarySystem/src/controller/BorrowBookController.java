package controller;

import javax.swing.JOptionPane;

import model.User;
import service.BorrowService;
import view.BorrowBookFrame;

public class BorrowBookController {

    private BorrowBookFrame frame;
    private BorrowService borrowService;
    private User user;

    public BorrowBookController(
            BorrowBookFrame frame,
            User user
    ) {

        this.frame = frame;
        this.user = user;
        this.borrowService = new BorrowService();

        initEvents();
    }

    private void initEvents() {

        frame.getBtnBorrow().addActionListener(e -> {

            try {

                int bookId =
                        Integer.parseInt(
                                frame.getTxtBookId()
                                     .getText()
                        );

                int days =
                        (Integer) frame
                                .getCmbDays()
                                .getSelectedItem();

                borrowService.borrowBook(
                        user,
                        bookId,
                        days
                );

                JOptionPane.showMessageDialog(
                        frame,
                        "借書完成！"
                );

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        frame,
                        "輸入格式錯誤！"
                );
            }
        });
    }
}