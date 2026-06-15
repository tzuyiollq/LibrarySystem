package view.components;

import javax.swing.*;
import java.awt.*;

public class AdminButton extends JButton {

    public AdminButton(String text){

        super(text);

        setFocusPainted(false);

        setFont(
                new Font(
                        "Microsoft JhengHei UI",
                        Font.BOLD,
                        18
                )
        );

        setForeground(
                AdminStyle.TEXT
        );

        setBackground(
                Color.WHITE
        );

        setContentAreaFilled(false);

        setBorderPainted(false);

        setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }

    @Override
    protected void paintComponent(Graphics g){

        Graphics2D g2 =
                (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        if(getModel().isRollover()){

            g2.setColor(
                    AdminStyle.PRIMARY_LIGHT
            );

        }else{

            g2.setColor(
                    Color.WHITE
            );
        }

        g2.fillRoundRect(
                0,
                0,
                getWidth()-1,
                getHeight()-1,
                25,
                25
        );

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g){

        Graphics2D g2 =
                (Graphics2D) g;

        g2.setColor(
                AdminStyle.BORDER
        );

        g2.drawRoundRect(
                0,
                0,
                getWidth()-1,
                getHeight()-1,
                25,
                25
        );
    }
}