package controller;

import javax.swing.JOptionPane;

import service.AdminService;
import view.AdminUserManageFrame;

public class AdminUserManageController {

    private AdminUserManageFrame frame;
    private AdminService adminService;

    public AdminUserManageController(
            AdminUserManageFrame frame
    ) {

        this.frame = frame;
        this.adminService =
                new AdminService();

        initEvents();
    }

    private void initEvents() {

        frame.getBtnSuspend()
             .addActionListener(e -> {

                 String studentNo =
                         frame.getTxtStudentNo()
                              .getText();

                 boolean result =
                         adminService
                         .suspendUser(studentNo);

                 JOptionPane.showMessageDialog(
                         frame,
                         result
                         ? "停權成功"
                         : "停權失敗"
                 );
             });

        frame.getBtnActivate()
             .addActionListener(e -> {

                 String studentNo =
                         frame.getTxtStudentNo()
                              .getText();

                 boolean result =
                         adminService
                         .activateUser(studentNo);

                 JOptionPane.showMessageDialog(
                         frame,
                         result
                         ? "復權成功"
                         : "復權失敗"
                 );
             });
    }
}