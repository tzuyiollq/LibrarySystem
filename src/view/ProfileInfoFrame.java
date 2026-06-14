package view;

import javax.swing.*;
import java.awt.*;

public class ProfileInfoFrame extends JFrame {

    private JLabel lblStudentNo;
    private JLabel lblName;
    private JLabel lblRole;
    private JLabel lblStatus;
    private JLabel lblBorrowCount;
    private JLabel lblReservationCount;
    private JLabel lblOverdueCount;

    public ProfileInfoFrame() {
        setTitle("個人資料");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));

        lblStudentNo = new JLabel();
        lblName = new JLabel();
        lblRole = new JLabel();
        lblStatus = new JLabel();
        lblBorrowCount = new JLabel();
        lblReservationCount = new JLabel();
        lblOverdueCount = new JLabel();

        panel.add(lblStudentNo);
        panel.add(lblName);
        panel.add(lblRole);
        panel.add(lblStatus);
        panel.add(lblBorrowCount);
        panel.add(lblReservationCount);
        panel.add(lblOverdueCount);

        add(panel);
        setVisible(true);
    }

    public void setData(
            String studentNo,
            String name,
            String role,
            String status,
            int borrowCount,
            int reservationCount,
            int overdueCount
    ) {
        lblStudentNo.setText("學號：" + studentNo);
        lblName.setText("姓名：" + name);
        lblRole.setText("會員等級：" + role);
        lblStatus.setText("帳號狀態：" + status);
        lblBorrowCount.setText("目前借閱中：" + borrowCount + " 本");
        lblReservationCount.setText("目前預約中：" + reservationCount + " 本");
        lblOverdueCount.setText("逾期未還：" + overdueCount + " 本");
    }
}