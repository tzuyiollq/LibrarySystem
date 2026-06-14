package controller;

import model.User;
import view.UserMainFrame;
import view.panel.BorrowBookPanel;
import view.panel.ReturnBookPanel;
import view.panel.BookSearchPanel;
import view.panel.HotBookPanel;
import view.panel.ReserveBookPanel;
import view.panel.FavoritePanel;
import view.panel.BookReviewPanel;
import view.panel.ProfilePanel;


import javax.swing.*;
import java.awt.*;

public class UserController {

    private UserMainFrame frame;
    private User user;

    public UserController(UserMainFrame frame, User user) {
        this.frame = frame;
        this.user = user;

        initEvents();
    }

    private void initEvents() {

    	frame.getBtnBorrowBook().addActionListener(e -> {

    	    BorrowBookPanel panel =
    	            new BorrowBookPanel(user);

    	    panel.getBtnHome().addActionListener(x -> {

    	        JPanel home = new JPanel(
    	                new BorderLayout()
    	        );

    	        home.add(
    	                new JLabel(
    	                        "歡迎使用，" +
    	                        user.getName(),
    	                        SwingConstants.CENTER
    	                ),
    	                BorderLayout.CENTER
    	        );

    	        frame.setContent(home);

    	    });

    	    frame.setContent(panel);
    	});

    	frame.getBtnReturnBook().addActionListener(e -> {

    	    ReturnBookPanel panel = new ReturnBookPanel(user);

    	    panel.getBtnHome().addActionListener(x -> {
    	        frame.setContent(createHomePanel());
    	    });

    	    frame.setContent(panel);
    	});

    	frame.getBtnSearchBook().addActionListener(e -> {
    	    frame.setContent(new BookSearchPanel(user));
    	});

        frame.getBtnHotBooks().addActionListener(e ->
                frame.setContent(
                        new HotBookPanel()
                )
        );

        frame.getBtnReserveBook().addActionListener(e ->
                frame.setContent(
                        new ReserveBookPanel(user)
                )
        );

        frame.getBtnFavoriteBook().addActionListener(e ->
                frame.setContent(
                        new FavoritePanel(user)
                )
        );

        frame.getBtnBookReview().addActionListener(e ->
                frame.setContent(
                        new BookReviewPanel(user)
                )
        );

        frame.getBtnProfile().addActionListener(e ->
                frame.setContent(
                        new ProfilePanel(user)
                )
        );
        
    }
    private JPanel createHomePanel() {

        JPanel home = new JPanel(new BorderLayout());

        home.add(
                new JLabel(
                        "歡迎使用，" + user.getName(),
                        SwingConstants.CENTER
                ),
                BorderLayout.CENTER
        );

        return home;
    }
}