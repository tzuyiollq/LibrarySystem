package controller;

import javax.swing.JOptionPane;
import service.BorrowService;
import view.ReturnBookFrame;

public class ReturnBookController {
    private ReturnBookFrame frame;
    private BorrowService borrowService;
    public ReturnBookController(ReturnBookFrame frame) { this.frame = frame; this.borrowService = new BorrowService(); initEvents(); }
    private void initEvents() {
        frame.getBtnReturn().addActionListener(e -> {
            try { borrowService.returnBook(Integer.parseInt(frame.getTxtRecordId().getText().trim())); JOptionPane.showMessageDialog(frame, "還書完成！"); }
            catch (Exception ex) { JOptionPane.showMessageDialog(frame, "輸入格式錯誤！"); }
        });
    }
}
