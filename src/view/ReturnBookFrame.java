package view;

import javax.swing.*;
import java.awt.*;

public class ReturnBookFrame extends JFrame {
    private JTextField txtRecordId;
    private JButton btnReturn;
    public ReturnBookFrame() {
        setTitle("還書"); setSize(350, 160); setLocationRelativeTo(null); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.add(new JLabel("Record ID：")); txtRecordId = new JTextField(); panel.add(txtRecordId);
        panel.add(new JLabel()); btnReturn = new JButton("還書"); panel.add(btnReturn); add(panel); setVisible(true);
    }
    public JTextField getTxtRecordId() { return txtRecordId; }
    public JButton getBtnReturn() { return btnReturn; }
}
