package controller;

import model.BorrowRecord;
import service.AdminService;
import view.AdminStudentRecordFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class AdminStudentRecordController {
    private AdminStudentRecordFrame frame;
    private AdminService adminService;
    public AdminStudentRecordController(AdminStudentRecordFrame frame) { this.frame = frame; this.adminService = new AdminService(); initEvents(); }
    private void initEvents() {
        frame.getBtnSearch().addActionListener(e -> {
            List<BorrowRecord> records = adminService.getRecordsByStudentNo(frame.getTxtStudentNo().getText().trim());
            DefaultTableModel m = frame.getTableModel(); m.setRowCount(0);
            for (BorrowRecord r : records) m.addRow(new Object[]{r.getRecordId(), r.getBookTitle(), r.getBorrowDate(), r.getDueDate(), r.getReturnDate() == null ? "未歸還" : r.getReturnDate()});
            if (records.isEmpty()) JOptionPane.showMessageDialog(frame, "查無此學號的借還紀錄");
        });
    }
}
