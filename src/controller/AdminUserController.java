package controller;

import model.User;
import service.AdminService;
import view.AdminUserFrame;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class AdminUserController {
    private AdminUserFrame frame;
    private AdminService adminService;
    public AdminUserController(AdminUserFrame frame) { this.frame = frame; this.adminService = new AdminService(); loadUsers(); }
    private void loadUsers() {
        List<User> users = adminService.getAllUsers();
        DefaultTableModel m = frame.getTableModel(); m.setRowCount(0);
        for (User u : users) m.addRow(new Object[]{u.getUserId(), u.getStudentNo(), u.getName(), u.getRoleLevel(), u.getStatus()});
    }
}
