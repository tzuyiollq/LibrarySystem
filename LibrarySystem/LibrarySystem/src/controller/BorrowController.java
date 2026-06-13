package controller;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import model.BorrowRecord;
import service.BorrowService;
import view.BorrowRecordFrame;

public class BorrowController {

    private BorrowRecordFrame frame;
    private BorrowService borrowService;

    public BorrowController(
            BorrowRecordFrame frame,
            int userId
    ) {

        this.frame = frame;
        this.borrowService = new BorrowService();

        loadRecords(userId);
    }

    private void loadRecords(int userId) {

        List<BorrowRecord> records =
                borrowService.getUserBorrowRecords(userId);

        DefaultTableModel model =
                frame.getTableModel();

        model.setRowCount(0);

        for (BorrowRecord r : records) {

            model.addRow(new Object[]{

                    r.getRecordId(),

                    r.getBookTitle(),

                    r.getBorrowDate(),

                    r.getDueDate(),

                    r.getReturnDate() == null
                            ? "未歸還"
                            : r.getReturnDate()

            });
        }
    }
}