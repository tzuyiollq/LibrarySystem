package view.panel;

import model.User;
import service.BorrowService;
import view.components.ModernButton;
import view.components.UIStyle;

import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends JPanel {

    private JButton btnProfileInfo;
    private JButton btnBorrowRecords;
    private JButton btnReminder;
    private JButton btnOverdue;
    private JButton btnMyReservations;
    private JButton btnLogout;

    public ProfilePanel(User user) {

        setLayout(new BorderLayout(24, 24));
        setBackground(UIStyle.BG);
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JPanel card = UIStyle.card();
        card.setLayout(new BorderLayout(35, 20));

        JLabel title = UIStyle.title("個人資料");
        card.add(title, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel(new GridLayout(5, 1, 0, 18));
        leftPanel.setBackground(Color.WHITE);

        btnProfileInfo = smallMenuButton("個人資料");
        btnBorrowRecords = smallMenuButton("借閱紀錄");
        btnReminder = smallMenuButton("到期提醒");
        btnOverdue = smallMenuButton("逾期處理");
        btnMyReservations = smallMenuButton("我的預約");

        leftPanel.add(btnProfileInfo);
        leftPanel.add(btnBorrowRecords);
        leftPanel.add(btnReminder);
        leftPanel.add(btnOverdue);
        leftPanel.add(btnMyReservations);

        BorrowService borrowService = new BorrowService();

        int borrowed =
                borrowService.countCurrentBorrowedBooks(
                        user.getUserId()
                );

        int limit =
                "VIP".equals(user.getRoleLevel()) ? 10 : 5;

        int remain =
                limit - borrowed;

        int maxCredit =
                "VIP".equals(user.getRoleLevel()) ? 150 : 100;

        JPanel infoPanel = new JPanel(new GridLayout(7, 1, 10, 18));
        infoPanel.setBackground(Color.WHITE);

        infoPanel.add(UIStyle.label("學號：" + user.getStudentNo()));
        infoPanel.add(UIStyle.label("姓名：" + user.getName()));
        infoPanel.add(UIStyle.label("會員等級：" + user.getRoleLevel()));
        infoPanel.add(UIStyle.label("帳號狀態：" + user.getStatus()));
        infoPanel.add(UIStyle.label("信用分數：" + user.getCreditScore() + " / " + maxCredit));
        infoPanel.add(UIStyle.label("目前借閱中：" + borrowed + " 本"));
        infoPanel.add(UIStyle.label("還可以借：" + remain + " 本"));

        btnLogout = new ModernButton("登出");
        btnLogout.setPreferredSize(new Dimension(90, 34));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(btnLogout);

        card.add(leftPanel, BorderLayout.WEST);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(bottomPanel, BorderLayout.SOUTH);

        add(card, BorderLayout.CENTER);
    }

    private JButton smallMenuButton(String text) {
        JButton button = new ModernButton(text);
        button.setPreferredSize(new Dimension(105, 52));
        return button;
    }

    public JButton getBtnProfileInfo() {
        return btnProfileInfo;
    }

    public JButton getBtnBorrowRecords() {
        return btnBorrowRecords;
    }

    public JButton getBtnReminder() {
        return btnReminder;
    }

    public JButton getBtnOverdue() {
        return btnOverdue;
    }

    public JButton getBtnMyReservations() {
        return btnMyReservations;
    }

    public JButton getBtnLogout() {
        return btnLogout;
    }
}