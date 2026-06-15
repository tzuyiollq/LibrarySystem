package view;

import controller.LoginController;
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
        setTitle("圖書館借還書系統");
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

        JLabel title = UIStyle.title("圖書館借還書系統");
        title.setBounds(390, 120, 400, 50);
        main.add(title);

        ModernButton btnSearch = new ModernButton("書籍查詢");
        btnSearch.setBounds(430, 230, 220, 55);
        main.add(btnSearch);

        ModernButton btnHot = new ModernButton("熱門書籍排行");
        btnHot.setBounds(430, 310, 220, 55);
        main.add(btnHot);

        ModernButton btnLogin = new ModernButton("登入");
        btnLogin.setBounds(430, 390, 220, 55);
        main.add(btnLogin);

        btnSearch.addActionListener(e -> showSearchPage());
        btnHot.addActionListener(e -> showHotBookPage());
        btnLogin.addActionListener(e -> showLoginPage());

        setContent(main);
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
            String password = new String(panel.getTxtPassword().getPassword()).trim();

            service.AuthService authService = new service.AuthService();
            model.User user = authService.loginUser(username, password);

            if (user != null) {

                dispose();

                UserMainFrame userMainFrame =
                        new UserMainFrame(user.getName());

                new controller.UserController(userMainFrame, user);

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        """
                        登入失敗。

                        請確認：
                        • 學號是否正確
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