package view.components;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class UIStyle {

    public static final Color BG = new Color(244, 246, 248);
    public static final Color CARD = Color.WHITE;
    public static final Color TEXT = new Color(52, 73, 94);
    public static final Color SUB_TEXT = new Color(120, 130, 140);
    public static final Color BORDER = new Color(235, 238, 242);

    public static final Font TITLE_FONT =
            new Font("Microsoft JhengHei UI", Font.PLAIN, 26);

    public static final Font NORMAL_FONT =
            new Font("Microsoft JhengHei UI", Font.PLAIN, 16);

    public static final Font TABLE_FONT =
            new Font("Microsoft JhengHei UI", Font.PLAIN, 14);

    public static void applyPanel(JPanel panel) {
        panel.setBackground(BG);
    }

    public static JLabel title(String text) {
        JLabel label = new JLabel(text);
        label.setFont(TITLE_FONT);
        label.setForeground(TEXT);
        return label;
    }

    public static JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setFont(NORMAL_FONT);
        label.setForeground(TEXT);
        return label;
    }

    public static JTextField textField() {
        JTextField field = new JTextField();
        field.setFont(NORMAL_FONT);
        field.setForeground(TEXT);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        return field;
    }

    public static JPanel card() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        return panel;
    }

    public static void applyTable(JTable table) {
        table.setFont(TABLE_FONT);
        table.setRowHeight(32);
        table.setForeground(TEXT);
        table.setBackground(Color.WHITE);
        table.setGridColor(BORDER);
        table.setSelectionBackground(new Color(232, 241, 250));
        table.setSelectionForeground(TEXT);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
        header.setBackground(new Color(248, 250, 252));
        header.setForeground(TEXT);
    }
}