package controller;

import javax.swing.JOptionPane;

import view.AdminMainFrame;
import view.AdminUserFrame;
import view.LoginFrame;
import view.AdminUserManageFrame;
import view.AdminBorrowRecordFrame;
import view.AdminStudentRecordFrame;
import view.AdminBookFrame;
import view.AdminAddBookFrame;
import view.AdminRemoveBookFrame;

public class AdminController {

    private AdminMainFrame frame;

    public AdminController(AdminMainFrame frame) {
        this.frame = frame;
        initEvents();
    }

    private void initEvents() {

    	frame.getBtnAllRecords().addActionListener(e -> {

    	    AdminBorrowRecordFrame recordFrame =
    	            new AdminBorrowRecordFrame();

    	    new AdminBorrowRecordController(recordFrame);
    	});

    	frame.getBtnStudentRecords().addActionListener(e -> {

    	    AdminStudentRecordFrame studentFrame =
    	            new AdminStudentRecordFrame();

    	    new AdminStudentRecordController(studentFrame);
    	});

    	frame.getBtnAllBooks().addActionListener(e -> {

    	    AdminBookFrame bookFrame =
    	            new AdminBookFrame();

    	    new AdminBookController(bookFrame);
    	});

    	frame.getBtnAddBook().addActionListener(e -> {

    	    AdminAddBookFrame addFrame =
    	            new AdminAddBookFrame();

    	    new AdminAddBookController(addFrame);
    	});

    	frame.getBtnRemoveBook().addActionListener(e -> {

    	    AdminRemoveBookFrame removeFrame =
    	            new AdminRemoveBookFrame();

    	    new AdminRemoveBookController(removeFrame);
    	});

        frame.getBtnAllUsers().addActionListener(e -> {
            AdminUserFrame userFrame = new AdminUserFrame();
            new AdminUserController(userFrame);
        });

        frame.getBtnSuspendUser()
        .addActionListener(e -> {

            AdminUserManageFrame manageFrame =
                    new AdminUserManageFrame();

            new AdminUserManageController(
                    manageFrame
            );

        });

        frame.getBtnActivateUser()
        .addActionListener(e -> {

            AdminUserManageFrame manageFrame =
                    new AdminUserManageFrame();

            new AdminUserManageController(
                    manageFrame
            );

        });

        frame.getBtnLogout().addActionListener(e -> {
            frame.dispose();

            LoginFrame loginFrame = new LoginFrame();
            new LoginController(loginFrame);
        });
    }
}