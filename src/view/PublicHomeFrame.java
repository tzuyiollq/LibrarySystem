package view;

import model.Book;
import model.HotBook;
import service.BookService;
import view.components.ModernButton;
import view.components.UIStyle;
import view.panel.LoginPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PublicHomeFrame extends JFrame {

    private JPanel contentPanel;
    private BookService bookService = new BookService();

    public PublicHomeFrame() {

        setTitle("No.67 Library");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPanel = new JPanel(new BorderLayout());
        add(contentPanel);

        showMainPage();

        setVisible(true);
    }

    private void setContent(JPanel panel) {

        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showMainPage() {

        BackgroundPanel main =
                new BackgroundPanel("assets/images/home_bg.jpeg");

        main.setLayout(null);

        // ===== 標題 =====
        JLabel title = new JLabel("No.67 Library");
        title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 48));
        title.setForeground(new Color(34, 58, 94));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(0, 120, getWidth(), 70);
        main.add(title);

        // ===== 功能介紹 =====
        ModernButton btnIntro = new ModernButton("功能介紹");
        btnIntro.setBounds(430, 250, 220, 55);
        main.add(btnIntro);

        // ===== 書籍查詢 =====
        ModernButton btnSearch = new ModernButton("書籍查詢");
        btnSearch.setBounds(430, 330, 220, 55);
        main.add(btnSearch);

        // ===== 熱門排行 =====
        ModernButton btnHot = new ModernButton("熱門書籍排行");
        btnHot.setBounds(430, 410, 220, 55);
        main.add(btnHot);

        // ===== 登入 =====
        ModernButton btnLogin = new ModernButton("登入");
        btnLogin.setBounds(430, 490, 220, 55);
        main.add(btnLogin);

        btnIntro.addActionListener(e -> showIntroPage());
        btnSearch.addActionListener(e -> showSearchPage());
        btnHot.addActionListener(e -> showHotBookPage());
        btnLogin.addActionListener(e -> showLoginPage());

        setContent(main);
    }

    private void showIntroPage() {

        JPanel main = new JPanel(new BorderLayout(20, 20));
        main.setBackground(UIStyle.BG);
        main.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        ModernButton btnHome = new ModernButton("← 回首頁");
        btnHome.setPreferredSize(new Dimension(130, 42));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(UIStyle.BG);
        top.add(btnHome);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(UIStyle.BG);

        JPanel card = UIStyle.card();
        card.setLayout(new BorderLayout(20, 20));
        card.setPreferredSize(new Dimension(720, 500));

        JLabel title = UIStyle.title("No.67 Library 功能介紹");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(title, BorderLayout.NORTH);

        JPanel listPanel = new JPanel(new GridLayout(7, 1, 0, 12));
        listPanel.setBackground(Color.WHITE);
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        listPanel.add(featureLabel("書籍查詢：可依關鍵字查找館藏書籍，未登入也能使用。"));
        listPanel.add(featureLabel("熱門書籍排行：查看目前最受歡迎的 TOP 10 書籍。"));
        listPanel.add(featureLabel("會員借書：登入後可借閱書籍，依會員等級限制借閱天數與本數。"));
        listPanel.add(featureLabel("預約與收藏：可預約熱門書籍，也能把喜歡的書加入收藏。"));
        listPanel.add(featureLabel("書評系統：會員可新增、查看與刪除自己的書評。"));
        listPanel.add(featureLabel("到期提醒：首頁會提醒 3 天內到期與已逾期的書籍。"));
        listPanel.add(featureLabel("信用分數：準時還書加分，逾期會扣分，扣完會停權。"));

        card.add(listPanel, BorderLayout.CENTER);

        JLabel footer = UIStyle.label("溫馨、簡約、好使用的圖書館借還書系統。");
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(footer, BorderLayout.SOUTH);

        wrapper.add(card);

        main.add(top, BorderLayout.NORTH);
        main.add(wrapper, BorderLayout.CENTER);

        btnHome.addActionListener(e -> showMainPage());

        setContent(main);
    }

    private JLabel featureLabel(String text) {

        JLabel label = UIStyle.label("• " + text);
        label.setOpaque(true);
        label.setBackground(new Color(248, 252, 255));
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIStyle.BORDER),
                BorderFactory.createEmptyBorder(10, 16, 10, 16)
        ));

        return label;
    }

    private void showSearchPage() {

        JPanel main = new JPanel(new BorderLayout(20, 20));
        main.setBackground(UIStyle.BG);
        main.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        ModernButton btnHome = new ModernButton("← 回首頁");
        btnHome.setPreferredSize(new Dimension(130, 42));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(UIStyle.BG);
        top.add(btnHome);

        JPanel card = UIStyle.card();
        card.setLayout(new BorderLayout(16, 16));

        JLabel title = UIStyle.title("書籍查詢");
        card.add(title, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        searchPanel.setBackground(Color.WHITE);

        JTextField txtKeyword = UIStyle.textField();
        txtKeyword.setPreferredSize(new Dimension(320, 42));

        ModernButton btnSearch = new ModernButton("查詢");
        btnSearch.setPreferredSize(new Dimension(120, 42));

        searchPanel.add(UIStyle.label("關鍵字："));
        searchPanel.add(txtKeyword);
        searchPanel.add(btnSearch);

        String[] columns = {
                "Book ID", "書名", "作者", "出版社", "出版年", "狀態"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        UIStyle.applyTable(table);

        btnSearch.addActionListener(e -> {
            model.setRowCount(0);

            List<Book> books =
                    bookService.searchBookList(txtKeyword.getText().trim());

            for (Book b : books) {
                model.addRow(new Object[]{
                        b.getBookId(),
                        b.getTitle(),
                        b.getAuthors(),
                        b.getPublisher(),
                        b.getPublishYear(),
                        b.getStatus()
                });
            }

            if (books.isEmpty()) {
                JOptionPane.showMessageDialog(this, "查無資料");
            }
        });

        card.add(searchPanel, BorderLayout.NORTH);
        card.add(UIStyle.scrollPane(table), BorderLayout.CENTER);

        main.add(top, BorderLayout.NORTH);
        main.add(card, BorderLayout.CENTER);

        btnHome.addActionListener(e -> showMainPage());

        setContent(main);
    }

    private void showHotBookPage() {

        JPanel main = new JPanel(new BorderLayout(20, 20));
        main.setBackground(UIStyle.BG);
        main.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        ModernButton btnHome = new ModernButton("← 回首頁");
        btnHome.setPreferredSize(new Dimension(130, 42));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(UIStyle.BG);
        top.add(btnHome);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(UIStyle.BG);

        JPanel card = UIStyle.card();
        card.setLayout(new BorderLayout(20, 20));
        card.setPreferredSize(new Dimension(650, 560));

        JLabel title = UIStyle.title("熱門書籍 TOP 10");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(title, BorderLayout.NORTH);

        JPanel listPanel = new JPanel(new GridLayout(10, 1, 0, 8));
        listPanel.setBackground(Color.WHITE);

        List<HotBook> books = bookService.getHotBooks();

        for (int i = 0; i < 10; i++) {
            JLabel label;

            if (i < books.size()) {
                HotBook b = books.get(i);

                label = UIStyle.label(
                        (i + 1) + ".  "
                                + b.getTitle()
                                + "　｜　借閱 "
                                + b.getBorrowCount()
                                + " 次"
                );
            } else {
                label = UIStyle.label((i + 1) + ". ");
            }

            label.setBorder(BorderFactory.createMatteBorder(
                    0, 0, 1, 0, UIStyle.BORDER
            ));

            listPanel.add(label);
        }

        card.add(listPanel, BorderLayout.CENTER);

        wrapper.add(card);

        main.add(top, BorderLayout.NORTH);
        main.add(wrapper, BorderLayout.CENTER);

        btnHome.addActionListener(e -> showMainPage());

        setContent(main);
    }

    private void showLoginPage() {

        LoginPanel panel = new LoginPanel();

        panel.getBtnHome().addActionListener(e -> showMainPage());

        panel.getBtnLogin().addActionListener(e -> {

            String username = panel.getTxtUsername().getText().trim();

            String password =
                    new String(
                            panel.getTxtPassword().getPassword()
                    ).trim();

            service.AuthService authService =
                    new service.AuthService();

            model.User user =
                    authService.loginUser(username, password);

            if (user != null) {

                dispose();

                UserMainFrame userMainFrame =
                        new UserMainFrame(user.getName());

                new controller.UserController(
                        userMainFrame,
                        user
                );

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        """
                        登入失敗。

                        請確認：
                        • 學號或姓名是否正確
                        • 密碼是否正確
                        """
                );
            }
        });

        panel.getBtnRegister().addActionListener(e -> {
            RegisterFrame registerFrame = new RegisterFrame();
            new controller.RegisterController(registerFrame);
        });

        setContent(panel);
    }

    private static class BackgroundPanel extends JPanel {

        private Image image;

        public BackgroundPanel(String path) {
            image = new ImageIcon(path).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            g.drawImage(
                    image,
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    this
            );
        }
    }
}