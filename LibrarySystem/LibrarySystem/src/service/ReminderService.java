package service;

import dao.ReminderDAO;
import model.BorrowRecord;

import java.util.List;

public class ReminderService {

    private ReminderDAO reminderDAO = new ReminderDAO();

    // Console 版：直接印出提醒
    public void showDueSoonReminders(int userId) {
        reminderDAO.showDueSoonReminders(userId);
    }

    // GUI 版：回傳資料給 JTable
    public List<BorrowRecord> getDueSoonBooks(int userId) {
        return reminderDAO.getDueSoonBooks(userId);
    }
}