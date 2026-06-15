package service;

import dao.AdminDAO;
import dao.UserDAO;
import model.Admin;
import model.User;

public class AuthService {

    private UserDAO userDAO = new UserDAO();
    private AdminDAO adminDAO = new AdminDAO();

    public User loginUser(String account, String password) {

        if (account == null || password == null) {
            return null;
        }

        account = account.trim();
        password = password.trim();

        if (account.isEmpty() || password.isEmpty()) {
            return null;
        }

        return userDAO.loginByStudentNoOrName(account, password);
    }

    public Admin loginAdmin(String username, String password) {

        if (username == null || password == null) {
            return null;
        }

        username = username.trim();
        password = password.trim();

        if (username.isEmpty() || password.isEmpty()) {
            return null;
        }

        return adminDAO.login(username, password);
    }

    public boolean registerUser(
            String studentNo,
            String name,
            String password,
            String roleLevel
    ) {
        if (studentNo == null || name == null || password == null) {
            return false;
        }

        if (studentNo.trim().isEmpty()
                || name.trim().isEmpty()
                || password.trim().isEmpty()) {
            return false;
        }

        return userDAO.registerUser(
                studentNo.trim(),
                name.trim(),
                password.trim(),
                roleLevel
        );
    }
}