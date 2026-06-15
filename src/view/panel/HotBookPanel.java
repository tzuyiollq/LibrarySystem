package view.panel;

import model.HotBook;
import service.BookService;
import view.components.ModernButton;
import view.components.UIStyle;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HotBookPanel extends JPanel {

    private BookService bookService = new BookService();
    private JButton btnHome;

    public HotBookPanel() {

        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(UIStyle.BG);

        btnHome = new ModernButton("← 回首頁");
        btnHome.setPreferredSize(new Dimension(130, 42));
        topPanel.add(btnHome);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(UIStyle.BG);

        JPanel card = UIStyle.card();
        card.setLayout(new BorderLayout(20, 20));
        card.setPreferredSize(new Dimension(620, 560));

        JLabel title = UIStyle.title("熱門書籍 TOP 10");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(title, BorderLayout.NORTH);

        JPanel listPanel = new JPanel(new GridLayout(10, 1, 0, 8));
        listPanel.setBackground(Color.WHITE);

        List<HotBook> books = bookService.getHotBooks();

        for (int i = 0; i < 10; i++) {

            JPanel row = new JPanel(new BorderLayout());
            row.setBackground(Color.WHITE);
            row.setBorder(BorderFactory.createMatteBorder(
                    0, 0, 1, 0, UIStyle.BORDER
            ));

            JLabel rank = UIStyle.label(String.valueOf(i + 1));
            rank.setHorizontalAlignment(SwingConstants.CENTER);
            rank.setPreferredSize(new Dimension(60, 36));

            JLabel bookInfo = UIStyle.label("");

            if (i < books.size()) {
                HotBook b = books.get(i);

                String medal = "";
                if (i == 0) medal = "① ";
                else if (i == 1) medal = "② ";
                else if (i == 2) medal = "③ ";

                bookInfo.setText(
                        medal + b.getTitle()
                                + "　｜　"
                                + b.getAuthors()
                                + "　｜　借閱 "
                                + b.getBorrowCount()
                                + " 次"
                );
            } else {
                bookInfo.setText("");
            }

            row.add(rank, BorderLayout.WEST);
            row.add(bookInfo, BorderLayout.CENTER);

            listPanel.add(row);
        }

        card.add(listPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        bottomPanel.setBackground(Color.WHITE);

        ModernButton btnThisMonth = new ModernButton("當月");
        ModernButton btnThisYear = new ModernButton("當年");

        btnThisMonth.setPreferredSize(new Dimension(120, 44));
        btnThisYear.setPreferredSize(new Dimension(120, 44));

        bottomPanel.add(btnThisMonth);
        bottomPanel.add(btnThisYear);

        card.add(bottomPanel, BorderLayout.SOUTH);

        centerWrapper.add(card);

        add(topPanel, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);
    }

    public JButton getBtnHome() {
        return btnHome;
    }
}