package view;

import javax.swing.*;
import java.awt.*;

public class BorrowBookFrame extends JFrame {
    private JTextField txtBookId;
    private JComboBox<Integer> cmbDays;
    private JButton btnBorrow;
    public BorrowBookFrame() {
        setTitle("借書"); setSize(350, 200); setLocationRelativeTo(null); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Book ID：")); txtBookId = new JTextField(); panel.add(txtBookId);
        panel.add(new JLabel("借閱天數：")); cmbDays = new JComboBox<>(new Integer[]{1,3,7,14}); panel.add(cmbDays);
        panel.add(new JLabel()); btnBorrow = new JButton("借書"); panel.add(btnBorrow); add(panel); setVisible(true);
    }
    public JTextField getTxtBookId() { return txtBookId; }
    public JComboBox<Integer> getCmbDays() { return cmbDays; }
    public JButton getBtnBorrow() { return btnBorrow; }
}
