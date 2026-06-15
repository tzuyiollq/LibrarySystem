package view.components;

import javax.swing.*;
import java.awt.*;

public class ModernComboBox<E> extends JComboBox<E> {

    public ModernComboBox(E[] items) {

        super(items);

        setFont(
            new Font(
                "微軟正黑體",
                Font.PLAIN,
                18
            )
        );

        setBackground(Color.WHITE);

        setBorder(
            BorderFactory.createLineBorder(
                new Color(220,225,230)
            )
        );
    }
}