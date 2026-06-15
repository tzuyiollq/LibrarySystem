package view.components;

import javax.swing.*;
import java.awt.*;

public class ModernButton extends JButton {

    private static final int ARC = 34;

    public ModernButton(String text) {
        super(text);

        setFont(UIStyle.BUTTON_FONT);
        setForeground(UIStyle.TEXT);

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(new Color(0, 0, 0, 16));
        g2.fillRoundRect(
                4,
                5,
                getWidth() - 8,
                getHeight() - 8,
                ARC,
                ARC
        );

        if (getModel().isPressed()) {
            g2.setColor(new Color(216, 232, 248));
        } else if (getModel().isRollover()) {
            g2.setColor(UIStyle.PRIMARY_LIGHT);
        } else {
            g2.setColor(Color.WHITE);
        }

        g2.fillRoundRect(
                0,
                0,
                getWidth() - 8,
                getHeight() - 8,
                ARC,
                ARC
        );

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(UIStyle.BORDER);

        g2.drawRoundRect(
                0,
                0,
                getWidth() - 9,
                getHeight() - 9,
                ARC,
                ARC
        );

        g2.dispose();
    }
}