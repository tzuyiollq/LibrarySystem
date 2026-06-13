package service;

import dao.AdminDAO;
import dao.UserDAO;
import model.Admin;
import model.User;

public class AuthService {

    private UserDAO userDAO = new UserDAO();
    private AdminDAO adminDAO = new AdminDAO();

    public User loginUser(String name, String password) {

        User user = userDAO.login(name, password);

        if (user == null) {
            System.out.println("使用者登入失敗！");
            return null;
        }

        if (user.getStatus().equals("SUSPENDED")) {
            System.out.println("此帳號已被停權，無法登入！");
            return null;
        }

        System.out.println("使用者登入成功！");
        return user;
    }

    public Admin loginAdmin(String username, String password) {

        Admin admin = adminDAO.login(username, password);

        if (admin == null) {
            System.out.println("管理者登入失敗！");
            return null;
        }

        System.out.println("管理者登入成功！");
        return admin;
    }

    public void registerUser(String studentNo, String name, String password, String roleLevel) {
        userDAO.register(studentNo, name, password, roleLevel);
    }
    public boolean existsStudentNo(String studentNo) {

        return userDAO.existsStudentNo(studentNo);
    }
}