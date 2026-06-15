package view.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ModernTextArea extends JTextArea {

    public ModernTextArea() {

        setFont(
            new Font(
                "微軟正黑體",
                Font.PLAIN,
                18
            )
        );

        setLineWrap(true);

        setWrapStyleWord(true);

        setBorder(
            new EmptyBorder(
                12,
                12,
                12,
                12
            )
        );
    }
}