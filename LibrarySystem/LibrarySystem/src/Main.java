import java.util.List;
import java.util.Scanner;

//import dao.AdminDAO;
//import dao.BookDAO;
//import dao.BorrowDAO;
//import dao.ReminderDAO;
//import dao.UserDAO;
import model.Admin;
import model.Book;
import model.BorrowRecord;
import model.User;
import service.AuthService;
import service.BorrowService;
import service.BookService;
import service.ReminderService;
import service.AdminService;

public class Main {

    public static void main(String[] args) {
    	
    	//DataImporter.importBorrowRecords();

        Scanner sc = new Scanner(System.in);
        
        AdminService adminService = new AdminService();
        ReminderService reminderService = new ReminderService();
        BookService bookService = new BookService();
        BorrowService borrowService = new BorrowService();
        AuthService authService = new AuthService();
//        UserDAO userDAO = new UserDAO();
//        BookDAO bookDAO = new BookDAO();
//        BorrowDAO borrowDAO = new BorrowDAO();
//        ReminderDAO reminderDAO = new ReminderDAO();
//        AdminDAO adminDAO = new AdminDAO();  // 新增

        int homeChoice;

        do {

            System.out.println();
            System.out.println("===== 圖書館借還書系統 =====");
            System.out.println("1. 註冊");
            System.out.println("2. 登入");
            System.out.println("3. 管理者登入");  // 新增
            System.out.println("0. 離開");

            System.out.print("請選擇：");

            homeChoice = sc.nextInt();
            sc.nextLine();

            switch (homeChoice) {

                case 1:

                    System.out.print("請輸入學號：");
                    String studentNo = sc.nextLine();

                    System.out.print("請輸入姓名：");
                    String registerName = sc.nextLine();

                    System.out.print("請輸入密碼：");
                    String registerPassword = sc.nextLine();

                    System.out.print("請輸入權限 (NORMAL / VIP)：");
                    String roleLevel = sc.nextLine();

                    authService.registerUser(studentNo, registerName, registerPassword, roleLevel);

                    break;

                case 2:

                    System.out.print("請輸入姓名：");
                    String loginName = sc.nextLine();

                    System.out.print("請輸入密碼：");
                    String loginPassword = sc.nextLine();

                    User user = authService.loginUser(loginName, loginPassword);
                    
                    if (user == null) {
                        System.out.println("登入失敗！");
                        break;
                    }

                    System.out.println("登入成功！");
                    System.out.println("歡迎：" + user.getName());

                    int choice;

                    do {

                        System.out.println();
                        System.out.println("===== 使用者選單 =====");
                        System.out.println("1. 查詢書籍");
                        System.out.println("2. 借書");
                        System.out.println("3. 還書");
                        System.out.println("4. 個人借還紀錄");
                        System.out.println("5. 書籍借還紀錄");
                        System.out.println("6. 到期提醒");
                        System.out.println("7. 查看逾期名單");
                        System.out.println("0. 登出");

                        System.out.print("請選擇功能：");

                        choice = sc.nextInt();
                        sc.nextLine();

                        switch (choice) {

                            case 1:
                                System.out.print("請輸入關鍵字：");
                                String keyword = sc.nextLine();
                                bookService.searchBooks(keyword);
                                break;

                            case 2:
                                System.out.print("請輸入 book_id：");
                                int borrowBookId = sc.nextInt();
                                System.out.print("請輸入借閱天數 (1 / 3 / 7 / 14)：");
                                int days = sc.nextInt();
                                sc.nextLine();
                                borrowService.borrowBook(
                                        user,
                                        borrowBookId,
                                        days
                                );
//                                if (user.getRoleLevel().equals("NORMAL") && days > 7) {
//                                    System.out.println("NORMAL 使用者最多只能借 7 天！");
//                                    break;
//                                }
//                                if (user.getRoleLevel().equals("VIP") && days > 14) {
//                                    System.out.println("VIP 使用者最多只能借 14 天！");
//                                    break;
//                                }

//                                borrowDAO.borrowBook(user.getUserId(), borrowBookId, days);
                                break;

                            case 3:
                                System.out.print("請輸入 record_id：");
                                int recordId = sc.nextInt();
                                sc.nextLine();
                                borrowService.returnBook(recordId);
                                break;

                            case 4:
                            	borrowService.showUserBorrowRecords(user.getUserId());
                            	break;

                            case 5:
                                System.out.print("請輸入 book_id：");
                                int historyBookId = sc.nextInt();
                                sc.nextLine();
                                borrowService.showBookBorrowRecords(historyBookId);                                
                                break;

                            case 6:
                            	reminderService.showDueSoonReminders(user.getUserId());
                            	break;

                            case 7:
                            	borrowService.showOverdueBooks();
                            	break;

                            case 0:
                                System.out.println("已登出");
                                break;

                            default:
                                System.out.println("無效選項");
                        }

                    } while (choice != 0);

                    break;

                // ↓ 以下全部都是新增的管理者區塊 ↓
                case 3:

                    System.out.print("請輸入管理者帳號：");
                    String adminUsername = sc.nextLine();

                    System.out.print("請輸入密碼：");
                    String adminPassword = sc.nextLine();

                    Admin admin = authService.loginAdmin(adminUsername, adminPassword);
                    
                    if (admin == null) {
                        System.out.println("管理者登入失敗！");
                        break;
                    }

                    System.out.println("管理者登入成功！");
                    System.out.println("歡迎：" + admin.getUsername());

                    int adminChoice;

                    do {

                        System.out.println();
                        System.out.println("===== 管理者選單 =====");
                        System.out.println("1. 查看所有借還紀錄");
                        System.out.println("2. 依學號查詢借還紀錄");
                        System.out.println("3. 查看所有書籍");
                        System.out.println("4. 新增書籍");
                        System.out.println("5. 下架書籍");
                        System.out.println("6. 查看所有使用者");
                        System.out.println("7. 停權使用者");
                        System.out.println("8. 復權使用者");
                        System.out.println("0. 登出");

                        System.out.print("請選擇功能：");

                        adminChoice = sc.nextInt();
                        sc.nextLine();

                        switch (adminChoice) {

                            case 1:
                                List<BorrowRecord> allRecords = adminService.getAllRecords();
                                System.out.println();
                                System.out.printf("%-6s %-12s %-10s %-20s %-12s %-12s %-12s%n",
                                    "ID", "學號", "姓名", "書名", "借出日", "應還日", "歸還日");
                                System.out.println("-".repeat(90));
                                for (BorrowRecord r : allRecords) {
                                    System.out.printf("%-6d %-12s %-10s %-20s %-12s %-12s %-12s%n",
                                        r.getRecordId(),
                                        r.getStudentNo(),
                                        r.getUserName(),
                                        r.getBookTitle(),
                                        r.getBorrowDate(),
                                        r.getDueDate(),
                                        r.getReturnDate() == null ? "未歸還" : r.getReturnDate()
                                    );
                                }
                                break;

                            case 2:
                                System.out.print("請輸入學號：");
                                String searchStudentNo = sc.nextLine();
                                List<BorrowRecord> studentRecords = adminService.getRecordsByStudentNo(searchStudentNo);
                                if (studentRecords.isEmpty()) {
                                    System.out.println("查無此學號的借還紀錄");
                                    break;
                                }
                                System.out.println();
                                System.out.printf("%-6s %-20s %-12s %-12s %-12s%n",
                                    "ID", "書名", "借出日", "應還日", "歸還日");
                                System.out.println("-".repeat(70));
                                for (BorrowRecord r : studentRecords) {
                                    System.out.printf("%-6d %-20s %-12s %-12s %-12s%n",
                                        r.getRecordId(),
                                        r.getBookTitle(),
                                        r.getBorrowDate(),
                                        r.getDueDate(),
                                        r.getReturnDate() == null ? "未歸還" : r.getReturnDate()
                                    );
                                }
                                break;

                            case 3:
                            	List<Book> books = adminService.getAllBooks();
                            	System.out.println();
                                System.out.printf("%-6s %-30s %-15s %-10s%n",
                                    "ID", "書名", "作者", "狀態");
                                System.out.println("-".repeat(65));
                                for (Book b : books) {
                                    System.out.printf("%-6d %-30s %-15s %-10s%n",
                                        b.getBookId(),
                                        b.getTitle(),
                                        b.getAuthors(),
                                        b.getStatus()
                                    );
                                }
                                break;

                            case 4:
                                System.out.print("書名：");
                                String title = sc.nextLine();
                                System.out.print("作者：");
                                String authors = sc.nextLine();
                                System.out.print("主題：");
                                String subjects = sc.nextLine();
                                System.out.print("出版社：");
                                String publisher = sc.nextLine();
                                System.out.print("出版年：");
                                String publishYear = sc.nextLine();
                                System.out.print("版本：");
                                String edition = sc.nextLine();
                                System.out.print("格式：");
                                String formatDesc = sc.nextLine();
                                System.out.print("資料來源：");
                                String source = sc.nextLine();
                                System.out.print("附註：");
                                String note = sc.nextLine();
                                System.out.print("ISBN（多個請用逗號分隔）：");
                                String isbnInput = sc.nextLine();

                                List<String> isbns = List.of(isbnInput.split(","));
                                Book newBook = new Book(0, title, authors, subjects,
                                    publisher, publishYear, edition, formatDesc, source, note, "AVAILABLE");

                                boolean added = adminService.addBook(newBook, isbns);
                                System.out.println(added ? "書籍新增成功！" : "新增失敗！");
                                break;

                            case 5:
                                System.out.print("請輸入要下架的 book_id：");
                                int removeBookId = sc.nextInt();
                                sc.nextLine();
                                boolean removed = adminService.removeBook(removeBookId);
                                System.out.println(removed ? "書籍已下架！" : "下架失敗！");
                                break;

                            case 6:
                            	List<User> users = adminService.getAllUsers();
                            	System.out.println();
                                System.out.printf("%-6s %-12s %-10s %-10s %-10s%n",
                                    "ID", "學號", "姓名", "權限", "狀態");
                                System.out.println("-".repeat(52));
                                for (User u : users) {
                                    System.out.printf("%-6d %-12s %-10s %-10s %-10s%n",
                                        u.getUserId(),
                                        u.getStudentNo(),
                                        u.getName(),
                                        u.getRoleLevel(),
                                        u.getStatus()
                                    );
                                }
                                break;

                            case 7:
                                System.out.print("請輸入要停權的學號：");
                                String suspendNo = sc.nextLine();
                                boolean suspended = adminService.suspendUser(suspendNo);
                                System.out.println(suspended ? "已停權！" : "操作失敗！");
                                break;

                            case 8:
                                System.out.print("請輸入要復權的學號：");
                                String activateNo = sc.nextLine();
                                boolean activated = adminService.activateUser(activateNo);
                                System.out.println(activated ? "已復權！" : "操作失敗！");
                                break;

                            case 0:
                                System.out.println("管理者已登出");
                                break;

                            default:
                                System.out.println("無效選項");
                        }

                    } while (adminChoice != 0);

                    break;

                case 0:
                    System.out.println("系統結束");
                    break;

                default:
                    System.out.println("無效選項");
            }

        } while (homeChoice != 0);

        sc.close();
    }
}