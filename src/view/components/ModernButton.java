package view.components;

import javax.swing.*;
import java.awt.*;

public class ModernButton extends JButton {

    private static final Color TEXT_COLOR =
            new Color(52, 73, 94);

    private static final Color BORDER_COLOR =
            new Color(235, 238, 242);

    private static final Color HOVER_COLOR =
            new Color(240, 246, 251);

    private static final Color NORMAL_COLOR =
            Color.WHITE;

    private static final Color SHADOW_COLOR =
            new Color(0, 0, 0, 15);

    private static final int ARC = 35;

    public ModernButton(String text) {

        super(text);

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);

        setForeground(TEXT_COLOR);

        setFont(
                new Font(
                        "Microsoft JhengHei UI",
                        Font.PLAIN,
                        18
                )
        );

        setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 =
                (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // 陰影
        g2.setColor(SHADOW_COLOR);
        g2.fillRoundRect(
                3,
                4,
                getWidth() - 6,
                getHeight() - 6,
                ARC,
                ARC
        );

        // 按鈕底色
        if (getModel().isRollover()) {
            g2.setColor(HOVER_COLOR);
        } else {
            g2.setColor(NORMAL_COLOR);
        }

        g2.fillRoundRect(
                0,
                0,
                getWidth() - 6,
                getHeight() - 6,
                ARC,
                ARC
        );

        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {

        Graphics2D g2 =
                (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(BORDER_COLOR);

        g2.drawRoundRect(
                0,
                0,
                getWidth() - 7,
                getHeight() - 7,
                ARC,
                ARC
        );

        g2.dispose();
    }
}