import java.util.Scanner;

import dao.BookDAO;
import dao.BorrowDAO;
import dao.ReminderDAO;
import dao.UserDAO;
import model.User;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserDAO userDAO = new UserDAO();
        BookDAO bookDAO = new BookDAO();
        BorrowDAO borrowDAO = new BorrowDAO();
        ReminderDAO reminderDAO = new ReminderDAO();

        int homeChoice;

        do {

            System.out.println();
            System.out.println("===== 圖書館借還書系統 =====");
            System.out.println("1. 註冊");
            System.out.println("2. 登入");
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

                    userDAO.register(
                        studentNo,
                        registerName,
                        registerPassword,
                        roleLevel
                    );

                    break;

                case 2:

                    System.out.print("請輸入姓名：");
                    String loginName = sc.nextLine();

                    System.out.print("請輸入密碼：");
                    String loginPassword = sc.nextLine();

                    User user = userDAO.login(
                        loginName,
                        loginPassword
                    );

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

                                bookDAO.searchBooks(keyword);

                                break;

                            case 2:

                                System.out.print("請輸入 book_id：");

                                int borrowBookId = sc.nextInt();

                                System.out.print(
                                    "請輸入借閱天數 (1 / 3 / 7 / 14)："
                                );

                                int days = sc.nextInt();
                                sc.nextLine();
                                
                                if (user.getRoleLevel().equals("NORMAL") && days > 7) {

                                    System.out.println("NORMAL 使用者最多只能借 7 天！");
                                    break;
                                }

                                if (user.getRoleLevel().equals("VIP") && days > 14) {

                                    System.out.println("VIP 使用者最多只能借 14 天！");
                                    break;
                                }

                                borrowDAO.borrowBook(
                                    user.getUserId(),
                                    borrowBookId,
                                    days
                                );

                                break;

                            case 3:

                                System.out.print("請輸入 record_id：");

                                int recordId = sc.nextInt();
                                sc.nextLine();

                                borrowDAO.returnBook(recordId);

                                break;

                            case 4:

                                borrowDAO.showUserBorrowRecords(
                                    user.getUserId()
                                );

                                break;

                            case 5:

                                System.out.print("請輸入 book_id：");

                                int historyBookId = sc.nextInt();
                                sc.nextLine();

                                borrowDAO.showBookBorrowRecords(
                                    historyBookId
                                );

                                break;

                            case 6:

                                reminderDAO.showDueSoonReminders(
                                    user.getUserId()
                                );

                                break;

                            case 7:

                                borrowDAO.showOverdueBooks();

                                break;

                            case 0:

                                System.out.println("已登出");

                                break;

                            default:

                                System.out.println("無效選項");
                        }

                    } while (choice != 0);

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