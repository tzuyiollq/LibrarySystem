package controller;

import model.BorrowRecord;
import service.BorrowService;
import view.BorrowRecordFrame;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class BorrowController {
    private BorrowRecordFrame frame;
    private BorrowService borrowService;
    public BorrowController(BorrowRecordFrame frame, int userId) { this.frame = frame; this.borrowService = new BorrowService(); loadRecords(userId); }
    private void loadRecords(int userId) {
        List<BorrowRecord> records = borrowService.getUserBorrowRecords(userId);
        DefaultTableModel m = frame.getTableModel(); m.setRowCount(0);
        for (BorrowRecord r : records) m.addRow(new Object[]{r.getRecordId(), r.getBookTitle(), r.getBorrowDate(), r.getDueDate(), r.getReturnDate() == null ? "未歸還" : r.getReturnDate()});
    }
}
