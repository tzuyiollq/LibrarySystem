package view;

import javax.swing.*;
import java.awt.*;

public class AdminAddBookFrame extends JFrame {
    private JTextField txtTitle, txtAuthors, txtSubjects, txtPublisher, txtPublishYear, txtEdition, txtFormatDesc, txtSource, txtNote, txtIsbns;
    private JButton btnAdd;
    public AdminAddBookFrame() {
        setTitle("新增書籍"); setSize(500, 500); setLocationRelativeTo(null); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel p = new JPanel(new GridLayout(11,2,8,8));
        txtTitle=new JTextField(); txtAuthors=new JTextField(); txtSubjects=new JTextField(); txtPublisher=new JTextField(); txtPublishYear=new JTextField();
        txtEdition=new JTextField(); txtFormatDesc=new JTextField(); txtSource=new JTextField(); txtNote=new JTextField(); txtIsbns=new JTextField(); btnAdd=new JButton("新增");
        p.add(new JLabel("書名：")); p.add(txtTitle); p.add(new JLabel("作者：")); p.add(txtAuthors); p.add(new JLabel("主題：")); p.add(txtSubjects);
        p.add(new JLabel("出版社：")); p.add(txtPublisher); p.add(new JLabel("出版年：")); p.add(txtPublishYear); p.add(new JLabel("版本：")); p.add(txtEdition);
        p.add(new JLabel("格式：")); p.add(txtFormatDesc); p.add(new JLabel("資料來源：")); p.add(txtSource); p.add(new JLabel("附註：")); p.add(txtNote);
        p.add(new JLabel("ISBN，多個用逗號：")); p.add(txtIsbns); p.add(new JLabel()); p.add(btnAdd); add(p); setVisible(true);
    }
    public JTextField getTxtTitle(){return txtTitle;} public JTextField getTxtAuthors(){return txtAuthors;} public JTextField getTxtSubjects(){return txtSubjects;}
    public JTextField getTxtPublisher(){return txtPublisher;} public JTextField getTxtPublishYear(){return txtPublishYear;} public JTextField getTxtEdition(){return txtEdition;}
    public JTextField getTxtFormatDesc(){return txtFormatDesc;} public JTextField getTxtSource(){return txtSource;} public JTextField getTxtNote(){return txtNote;}
    public JTextField getTxtIsbns(){return txtIsbns;} public JButton getBtnAdd(){return btnAdd;}
}
