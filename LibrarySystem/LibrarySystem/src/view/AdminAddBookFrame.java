package view;

import javax.swing.*;
import java.awt.*;

public class AdminAddBookFrame extends JFrame {

    private JTextField txtTitle;
    private JTextField txtAuthors;
    private JTextField txtSubjects;
    private JTextField txtPublisher;
    private JTextField txtPublishYear;
    private JTextField txtEdition;
    private JTextField txtFormatDesc;
    private JTextField txtSource;
    private JTextField txtNote;
    private JTextField txtIsbns;
    private JButton btnAdd;

    public AdminAddBookFrame() {

        setTitle("新增書籍");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(11, 2, 8, 8));

        txtTitle = new JTextField();
        txtAuthors = new JTextField();
        txtSubjects = new JTextField();
        txtPublisher = new JTextField();
        txtPublishYear = new JTextField();
        txtEdition = new JTextField();
        txtFormatDesc = new JTextField();
        txtSource = new JTextField();
        txtNote = new JTextField();
        txtIsbns = new JTextField();
        btnAdd = new JButton("新增");

        panel.add(new JLabel("書名："));
        panel.add(txtTitle);
        panel.add(new JLabel("作者："));
        panel.add(txtAuthors);
        panel.add(new JLabel("主題："));
        panel.add(txtSubjects);
        panel.add(new JLabel("出版社："));
        panel.add(txtPublisher);
        panel.add(new JLabel("出版年："));
        panel.add(txtPublishYear);
        panel.add(new JLabel("版本："));
        panel.add(txtEdition);
        panel.add(new JLabel("格式："));
        panel.add(txtFormatDesc);
        panel.add(new JLabel("資料來源："));
        panel.add(txtSource);
        panel.add(new JLabel("附註："));
        panel.add(txtNote);
        panel.add(new JLabel("ISBN，多個用逗號："));
        panel.add(txtIsbns);
        panel.add(new JLabel());
        panel.add(btnAdd);

        add(panel);
        setVisible(true);
    }

    public JTextField getTxtTitle() { return txtTitle; }
    public JTextField getTxtAuthors() { return txtAuthors; }
    public JTextField getTxtSubjects() { return txtSubjects; }
    public JTextField getTxtPublisher() { return txtPublisher; }
    public JTextField getTxtPublishYear() { return txtPublishYear; }
    public JTextField getTxtEdition() { return txtEdition; }
    public JTextField getTxtFormatDesc() { return txtFormatDesc; }
    public JTextField getTxtSource() { return txtSource; }
    public JTextField getTxtNote() { return txtNote; }
    public JTextField getTxtIsbns() { return txtIsbns; }
    public JButton getBtnAdd() { return btnAdd; }
}