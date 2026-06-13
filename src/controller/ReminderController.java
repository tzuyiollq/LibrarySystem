package controller;

import model.BorrowRecord;
import service.ReminderService;
import view.ReminderFrame;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ReminderController {
    private ReminderFrame frame;
    private ReminderService reminderService;
    public ReminderController(ReminderFrame frame, int userId) { this.frame = frame; this.reminderService = new ReminderService(); loadData(userId); }
    private void loadData(int userId) {
        List<BorrowRecord> records = reminderService.getDueSoonBooks(userId);
        DefaultTableModel m = frame.getTableModel(); m.setRowCount(0);
        for (BorrowRecord r : records) m.addRow(new Object[]{r.getRecordId(), r.getBookTitle(), r.getBorrowDate(), r.getDueDate()});
    }
}
