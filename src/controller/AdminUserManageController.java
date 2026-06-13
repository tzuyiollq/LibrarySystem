package controller;

import javax.swing.JOptionPane;
import service.AdminService;
import view.AdminUserManageFrame;

public class AdminUserManageController {
    private AdminUserManageFrame frame;
    private AdminService adminService;
    public AdminUserManageController(AdminUserManageFrame frame) { this.frame = frame; this.adminService = new AdminService(); initEvents(); }
    private void initEvents() {
        frame.getBtnSuspend().addActionListener(e -> JOptionPane.showMessageDialog(frame, adminService.suspendUser(frame.getTxtStudentNo().getText().trim()) ? "停權成功" : "停權失敗"));
        frame.getBtnActivate().addActionListener(e -> JOptionPane.showMessageDialog(frame, adminService.activateUser(frame.getTxtStudentNo().getText().trim()) ? "復權成功" : "復權失敗"));
    }
}
