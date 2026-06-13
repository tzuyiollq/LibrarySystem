package view;

import javax.swing.*;
import java.awt.*;

public class ReserveBookFrame extends JFrame {

    private JTextField txtBookId;
    private JButton btnReserve;

    public ReserveBookFrame() {

        setTitle("預約書籍");
        setSize(350, 160);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        panel.add(new JLabel("Book ID："));

        txtBookId = new JTextField();
        panel.add(txtBookId);

        panel.add(new JLabel());

        btnReserve = new JButton("預約");
        panel.add(btnReserve);

        add(panel);
        setVisible(true);
    }

    public JTextField getTxtBookId() {
        return txtBookId;
    }

    public JButton getBtnReserve() {
        return btnReserve;
    }
}