package controller;

import model.Book;
import service.AdminService;
import view.AdminBookFrame;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class AdminBookController {
    private AdminBookFrame frame;
    private AdminService adminService;
    public AdminBookController(AdminBookFrame frame) { this.frame = frame; this.adminService = new AdminService(); loadBooks(); }
    private void loadBooks() {
        List<Book> books = adminService.getAllBooks();
        DefaultTableModel m = frame.getTableModel(); m.setRowCount(0);
        for (Book b : books) m.addRow(new Object[]{b.getBookId(), b.getTitle(), b.getAuthors(), b.getSubjects(), b.getPublisher(), b.getPublishYear(), b.getStatus()});
    }
}
