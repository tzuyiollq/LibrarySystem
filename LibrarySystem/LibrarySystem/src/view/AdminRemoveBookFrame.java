package view;

import javax.swing.*;
import java.awt.*;

public class AdminRemoveBookFrame extends JFrame {

    private JTextField txtBookId;
    private JButton btnRemove;

    public AdminRemoveBookFrame() {

        setTitle("下架書籍");
        setSize(350, 160);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        panel.add(new JLabel("Book ID："));

        txtBookId = new JTextField();
        panel.add(txtBookId);

        panel.add(new JLabel());

        btnRemove = new JButton("下架");
        panel.add(btnRemove);

        add(panel);

        setVisible(true);
    }

    public JTextField getTxtBookId() {
        return txtBookId;
    }

    public JButton getBtnRemove() {
        return btnRemove;
    }
}