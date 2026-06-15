package view.components;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class UIStyle {

    public static final Color BG = new Color(241, 247, 253);
    public static final Color SIDEBAR_BG = new Color(248, 252, 255);
    public static final Color CARD = Color.WHITE;

    public static final Color PRIMARY = new Color(101, 145, 190);
    public static final Color PRIMARY_LIGHT = new Color(224, 238, 252);
    public static final Color TEXT = new Color(38, 63, 91);
    public static final Color SUB_TEXT = new Color(110, 130, 150);
    public static final Color BORDER = new Color(224, 234, 244);

    public static final Font TITLE_FONT =
            new Font("Microsoft JhengHei UI", Font.BOLD, 26);

    public static final Font NORMAL_FONT =
            new Font("Microsoft JhengHei UI", Font.PLAIN, 16);

    public static final Font BUTTON_FONT =
            new Font("Microsoft JhengHei UI", Font.BOLD, 17);

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
                BorderFactory.createEmptyBorder(9, 14, 9, 14)
        ));
        return field;
    }

    public static JTextArea textArea() {
        JTextArea area = new JTextArea();
        area.setFont(NORMAL_FONT);
        area.setForeground(TEXT);
        area.setBackground(Color.WHITE);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        return area;
    }

    public static <T> JComboBox<T> comboBox(T[] items) {
        JComboBox<T> comboBox = new JComboBox<>(items);
        comboBox.setFont(NORMAL_FONT);
        comboBox.setForeground(TEXT);
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createLineBorder(BORDER));
        return comboBox;
    }

    public static JPanel card() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(24, 24, 24, 24)
        ));
        return panel;
    }

    public static JScrollPane scrollPane(Component component) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
        scrollPane.getViewport().setBackground(Color.WHITE);
        return scrollPane;
    }

    public static void applyTable(JTable table) {
        table.setFont(NORMAL_FONT);
        table.setRowHeight(34);
        table.setForeground(TEXT);
        table.setBackground(Color.WHITE);
        table.setGridColor(BORDER);
        table.setSelectionBackground(PRIMARY_LIGHT);
        table.setSelectionForeground(TEXT);
        table.setShowGrid(true);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 15));
        header.setBackground(new Color(237, 244, 251));
        header.setForeground(TEXT);
    }
}