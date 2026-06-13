package controller;

import model.User;
import view.UserMainFrame;
import view.panel.*;

public class UserController {

    private UserMainFrame frame;
    private User user;

    public UserController(UserMainFrame frame, User user) {
        this.frame = frame;
        this.user = user;
        initEvents();
    }

    private void initEvents() {

        frame.getBtnBorrowBook().addActionListener(e ->
                frame.setContent(new BorrowBookPanel(user))
        );

        frame.getBtnReturnBook().addActionListener(e ->
                frame.setContent(new ReturnBookPanel())
        );

        frame.getBtnSearchBook().addActionListener(e ->
                frame.setContent(new BookSearchPanel())
        );

        frame.getBtnHotBooks().addActionListener(e ->
                frame.setContent(new HotBookPanel())
        );

        frame.getBtnReserveBook().addActionListener(e ->
                frame.setContent(new ReserveBookPanel(user))
        );

        frame.getBtnProfile().addActionListener(e ->
                frame.setContent(new ProfilePanel(user))
        );
    }
}