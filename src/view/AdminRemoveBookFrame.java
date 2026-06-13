package view;

import javax.swing.*;
import java.awt.*;

public class AdminRemoveBookFrame extends JFrame {
    private JTextField txtBookId;
    private JButton btnRemove;
    public AdminRemoveBookFrame() {
        setTitle("下架書籍"); setSize(350, 160); setLocationRelativeTo(null); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel p = new JPanel(new GridLayout(2,2,10,10)); p.add(new JLabel("Book ID：")); txtBookId = new JTextField(); p.add(txtBookId);
        p.add(new JLabel()); btnRemove = new JButton("下架"); p.add(btnRemove); add(p); setVisible(true);
    }
    public JTextField getTxtBookId(){return txtBookId;} public JButton getBtnRemove(){return btnRemove;}
}
