package controller;

import service.AdminService;
import view.AdminUpdateRoleFrame;

import javax.swing.JOptionPane;

public class AdminUpdateRoleController {

    private AdminUpdateRoleFrame frame;
    private AdminService adminService;

    public AdminUpdateRoleController(AdminUpdateRoleFrame frame) {
        this.frame = frame;
        this.adminService = new AdminService();

        initEvents();
    }

    private void initEvents() {

        frame.getBtnUpdate().addActionListener(e -> {

            String studentNo =
                    frame.getTxtStudentNo().getText().trim();

            String role =
                    frame.getCmbRole()
                         .getSelectedItem()
                         .toString();

            if (studentNo.isEmpty()) {
                JOptionPane.showMessageDialog(
                        frame,
                        "請輸入學號"
                );
                return;
            }

            boolean result =
                    adminService.updateUserRole(
                            studentNo,
                            role
                    );

            JOptionPane.showMessageDialog(
                    frame,
                    result ? "會員等級更新成功！" : "更新失敗，查無此學號"
            );
        });
    }
}