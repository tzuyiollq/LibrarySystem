package view.panel;

import dao.DBConnection;
import model.User;
import view.components.UIStyle;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WelcomePanel extends JPanel {

    private User user;
    private ConfettiPanel confettiPanel;

    public WelcomePanel(User user) {

        this.user = user;

        setLayout(new GridBagLayout());
        setBackground(UIStyle.BG);

        JPanel card = UIStyle.card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(560, 430));

        JLabel title = UIStyle.title("歡迎回來，" + user.getName());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = UIStyle.label("今天也適合讀一本好書。");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel role = UIStyle.label("會員等級：" + user.getRoleLevel());
        role.setAlignmentX(Component.CENTER_ALIGNMENT);

        int maxCredit =
                "VIP".equals(user.getRoleLevel()) ? 150 : 100;

        JLabel credit = UIStyle.label(
                "信用分數：" + user.getCreditScore() + " / " + maxCredit
        );
        credit.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalStrut(35));
        card.add(title);
        card.add(Box.createVerticalStrut(18));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(18));
        card.add(role);
        card.add(Box.createVerticalStrut(8));
        card.add(credit);
        card.add(Box.createVerticalStrut(26));

        JPanel noticePanel = new JPanel();
        noticePanel.setLayout(new BoxLayout(noticePanel, BoxLayout.Y_AXIS));
        noticePanel.setBackground(Color.WHITE);
        noticePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        List<String> notices = getNotices();

        if (notices.isEmpty()) {
            noticePanel.add(
                    noticeLabel(
                            "目前沒有即將到期或逾期的書籍，狀態很棒！",
                            new Color(225, 240, 230),
                            new Color(60, 115, 80)
                    )
            );
        } else {
            for (String notice : notices) {

                if (notice.contains("已逾期")) {
                    noticePanel.add(
                            noticeLabel(
                                    notice,
                                    new Color(248, 226, 222),
                                    new Color(145, 70, 65)
                            )
                    );
                } else {
                    noticePanel.add(
                            noticeLabel(
                                    notice,
                                    new Color(250, 240, 215),
                                    new Color(135, 105, 45)
                            )
                    );
                }

                noticePanel.add(Box.createVerticalStrut(8));
            }
        }

        card.add(noticePanel);
        add(card);

        if ("VIP".equals(user.getRoleLevel())) {
            confettiPanel = new ConfettiPanel();
            setGlassPaneEffect();
        }
    }

    private JLabel noticeLabel(String text, Color bg, Color fg) {

        JLabel label = new JLabel(text);
        label.setOpaque(true);
        label.setBackground(bg);
        label.setForeground(fg);
        label.setFont(UIStyle.NORMAL_FONT);
        label.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setMaximumSize(new Dimension(460, 42));

        return label;
    }

    private List<String> getNotices() {

        List<String> notices = new ArrayList<>();

        String sql = """
            SELECT b.title,
                   DATEDIFF(br.due_date, NOW()) AS remain_days
            FROM borrow_records br
            JOIN books b ON br.book_id = b.book_id
            WHERE br.user_id = ?
              AND br.return_date IS NULL
            ORDER BY br.due_date ASC
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, user.getUserId());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String title = rs.getString("title");
                int remainDays = rs.getInt("remain_days");

                if (remainDays < 0) {
                    notices.add(
                            "《" + title + "》已逾期 " +
                                    Math.abs(remainDays) +
                                    " 天，請盡快歸還。"
                    );
                } else if (remainDays <= 3) {
                    notices.add(
                            "《" + title + "》剩 " +
                                    remainDays +
                                    " 天到期，記得準時還書。"
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return notices;
    }

    private void setGlassPaneEffect() {

        SwingUtilities.invokeLater(() -> {

            Window window =
                    SwingUtilities.getWindowAncestor(this);

            if (window instanceof JFrame frame) {
                frame.setGlassPane(confettiPanel);
                confettiPanel.setVisible(true);
                confettiPanel.start();
            }
        });
    }

    private static class ConfettiPanel extends JComponent {

        private Random random = new Random();

        private int[] x = new int[80];
        private int[] y = new int[80];
        private int[] speed = new int[80];
        private Color[] colors = new Color[80];

        private Timer timer;

        public ConfettiPanel() {

            setOpaque(false);

            Color[] palette = {
                    new Color(101, 145, 190),
                    new Color(224, 238, 252),
                    new Color(255, 213, 128),
                    new Color(130, 200, 180),
                    new Color(240, 150, 170)
            };

            for (int i = 0; i < x.length; i++) {
                x[i] = random.nextInt(1200);
                y[i] = -random.nextInt(600);
                speed[i] = 2 + random.nextInt(5);
                colors[i] = palette[random.nextInt(palette.length)];
            }
        }

        public void start() {

            timer = new Timer(25, e -> {

                for (int i = 0; i < y.length; i++) {
                    y[i] += speed[i];

                    if (y[i] > getHeight()) {
                        y[i] = -20;
                        x[i] = random.nextInt(
                                Math.max(getWidth(), 1)
                        );
                    }
                }

                repaint();
            });

            timer.start();

            Timer stopTimer = new Timer(3500, e -> {
                timer.stop();
                setVisible(false);
            });

            stopTimer.setRepeats(false);
            stopTimer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            for (int i = 0; i < x.length; i++) {
                g2.setColor(colors[i]);
                g2.fillRoundRect(
                        x[i],
                        y[i],
                        8,
                        12,
                        4,
                        4
                );
            }

            g2.dispose();
        }
    }
}