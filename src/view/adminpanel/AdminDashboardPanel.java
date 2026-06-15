package view.adminpanel;

import dao.DBConnection;
import view.components.AdminStyle;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminDashboardPanel extends JPanel {

    public AdminDashboardPanel() {

        setLayout(new BorderLayout(20, 20));
        setBackground(AdminStyle.BG);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = AdminStyle.title("管理首頁");
        add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(2, 2, 20, 20));
        grid.setBackground(AdminStyle.BG);

        grid.add(statCard("總藏書數", count("books")));
        grid.add(statCard("使用者數", count("users")));
        grid.add(statCard("借閱中", countBorrowing()));
        grid.add(statCard("逾期未還", countOverdue()));

        add(grid, BorderLayout.CENTER);
    }

    private JPanel statCard(String title, int value) {

        JPanel card = AdminStyle.card();
        card.setLayout(new GridLayout(2, 1));

        JLabel lblTitle = AdminStyle.label(title);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblValue = AdminStyle.title(String.valueOf(value));
        lblValue.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(lblTitle);
        card.add(lblValue);

        return card;
    }

    private int count(String table) {
        String sql = "SELECT COUNT(*) FROM " + table;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private int countBorrowing() {
        String sql = """
            SELECT COUNT(*)
            FROM borrow_records
            WHERE return_date IS NULL
        """;

        return countBySql(sql);
    }

    private int countOverdue() {
        String sql = """
            SELECT COUNT(*)
            FROM borrow_records
            WHERE return_date IS NULL
              AND due_date < NOW()
        """;

        return countBySql(sql);
    }

    private int countBySql(String sql) {
        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}