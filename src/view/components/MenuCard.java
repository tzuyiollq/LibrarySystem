package view.components;

import javax.swing.*;
import java.awt.*;

public class MenuCard extends JPanel {

    public MenuCard(String title) {

        setLayout(
                new BorderLayout()
        );

        setBackground(Color.WHITE);

        setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(230,230,230)
                        ),
                        BorderFactory.createEmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );

        JLabel label =
                new JLabel(
                        title,
                        SwingConstants.CENTER
                );

        label.setFont(
                new Font(
                        "微軟正黑體",
                        Font.BOLD,
                        22
                )
        );

        add(label);
    }
}