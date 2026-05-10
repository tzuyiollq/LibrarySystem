import java.util.Scanner;

import dao.UserDAO;
import model.User;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("請輸入姓名：");
        String name = sc.nextLine();

        System.out.print("請輸入密碼：");
        String password = sc.nextLine();

        UserDAO userDAO = new UserDAO();

        User user = userDAO.login(name, password);

        if (user != null) {

            System.out.println("登入成功！");
            System.out.println("歡迎：" + user.getName());

        } else {

            System.out.println("登入失敗");
        }

        sc.close();
    }
}