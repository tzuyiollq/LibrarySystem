package view.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ModernTextField extends JTextField {

    public ModernTextField() {

        setFont(new Font("微軟正黑體", Font.PLAIN, 18));

        setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(220,225,230),
                    1
                ),
                new EmptyBorder(8,15,8,15)
            )
        );

        setBackground(Color.WHITE);
    }
}