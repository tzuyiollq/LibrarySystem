package view.components;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class AdminStyle {

    public static final Color BG =
            new Color(247,244,239);

    public static final Color CARD =
            Color.WHITE;

    public static final Color PRIMARY =
            new Color(150,110,85);

    public static final Color PRIMARY_LIGHT =
            new Color(238,230,221);

    public static final Color TEXT =
            new Color(70,55,45);

    public static final Color BORDER =
            new Color(225,215,205);

    public static JLabel title(String text){

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Microsoft JhengHei UI",
                        Font.BOLD,
                        26
                )
        );

        label.setForeground(TEXT);

        return label;
    }

    public static JLabel label(String text){

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Microsoft JhengHei UI",
                        Font.PLAIN,
                        16
                )
        );

        label.setForeground(TEXT);

        return label;
    }

    public static JPanel card(){

        JPanel panel =
                new JPanel();

        panel.setBackground(CARD);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );

        return panel;
    }

    public static void applyTable(JTable table){

        table.setRowHeight(34);

        table.setFont(
                new Font(
                        "Microsoft JhengHei UI",
                        Font.PLAIN,
                        15
                )
        );

        JTableHeader header =
                table.getTableHeader();

        header.setBackground(
                PRIMARY_LIGHT
        );

        header.setForeground(
                TEXT
        );

        header.setFont(
                new Font(
                        "Microsoft JhengHei UI",
                        Font.BOLD,
                        15
                )
        );
    }
}