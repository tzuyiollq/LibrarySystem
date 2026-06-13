package controller;

import model.BorrowRecord;
import service.AdminService;
import view.AdminBorrowRecordFrame;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class AdminBorrowRecordController {
    private AdminBorrowRecordFrame frame;
    private AdminService adminService;
    public AdminBorrowRecordController(AdminBorrowRecordFrame frame) { this.frame = frame; this.adminService = new AdminService(); loadRecords(); }
    private void loadRecords() {
        List<BorrowRecord> records = adminService.getAllRecords();
        DefaultTableModel m = frame.getTableModel(); m.setRowCount(0);
        for (BorrowRecord r : records) m.addRow(new Object[]{r.getRecordId(), r.getStudentNo(), r.getUserName(), r.getBookTitle(), r.getBorrowDate(), r.getDueDate(), r.getReturnDate() == null ? "未歸還" : r.getReturnDate()});
    }
}
