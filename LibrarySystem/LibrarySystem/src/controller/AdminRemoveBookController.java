package controller;

import javax.swing.JOptionPane;

import service.AdminService;
import view.AdminRemoveBookFrame;

public class AdminRemoveBookController {

    private AdminRemoveBookFrame frame;
    private AdminService adminService;

    public AdminRemoveBookController(AdminRemoveBookFrame frame) {
        this.frame = frame;
        this.adminService = new AdminService();

        initEvents();
    }

    private void initEvents() {

        frame.getBtnRemove().addActionListener(e -> {

            try {
                int bookId = Integer.parseInt(
                        frame.getTxtBookId().getText()
                );

                boolean result = adminService.removeBook(bookId);

                JOptionPane.showMessageDialog(
                        frame,
                        result ? "書籍下架成功！" : "下架失敗！"
                );

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        frame,
                        "請輸入正確的 Book ID"
                );
            }
        });
    }
}