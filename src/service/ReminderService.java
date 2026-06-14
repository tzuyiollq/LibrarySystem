package service;

import dao.ReminderDAO;
import model.BorrowRecord;
import java.util.List;

public class ReminderService {
    private ReminderDAO reminderDAO = new ReminderDAO();
    public void showDueSoonReminders(int userId) { reminderDAO.showDueSoonReminders(userId); }
    public List<BorrowRecord> getDueSoonBooks(int userId) { return reminderDAO.getDueSoonBooks(userId); }
    public List<BorrowRecord> getOverdueRecords(int userId) { return reminderDAO.getOverdueRecords(userId); }
//    public void suspendLongOverdueUsers() { reminderDAO.suspendLongOverdueUsers(); }
    public int countOverdueBooks(int userId) { return reminderDAO.countOverdueBooks(userId); }
    public void suspendLongOverdueUsers() { reminderDAO.suspendLongOverdueUsers(); }
}
