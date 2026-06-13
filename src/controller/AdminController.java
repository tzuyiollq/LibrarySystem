package controller;

import view.*;

public class AdminController {
    private AdminMainFrame frame;
    public AdminController(AdminMainFrame frame) { this.frame = frame; initEvents(); }
    private void initEvents() {
        frame.getBtnAllRecords().addActionListener(e -> new AdminBorrowRecordController(new AdminBorrowRecordFrame()));
        frame.getBtnStudentRecords().addActionListener(e -> new AdminStudentRecordController(new AdminStudentRecordFrame()));
        frame.getBtnAllBooks().addActionListener(e -> new AdminBookController(new AdminBookFrame()));
        frame.getBtnAddBook().addActionListener(e -> new AdminAddBookController(new AdminAddBookFrame()));
        frame.getBtnRemoveBook().addActionListener(e -> new AdminRemoveBookController(new AdminRemoveBookFrame()));
        frame.getBtnAllUsers().addActionListener(e -> new AdminUserController(new AdminUserFrame()));
        frame.getBtnSuspendUser().addActionListener(e -> new AdminUserManageController(new AdminUserManageFrame()));
        frame.getBtnActivateUser().addActionListener(e -> new AdminUserManageController(new AdminUserManageFrame()));
        frame.getBtnLogout().addActionListener(e -> { frame.dispose(); LoginFrame login = new LoginFrame(); new LoginController(login); });
    }
}
