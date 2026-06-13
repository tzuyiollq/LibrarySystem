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
        if (user == null) return null;
        if ("SUSPENDED".equals(user.getStatus())) return null;
        return user;
    }

    public Admin loginAdmin(String username, String password) { return adminDAO.login(username, password); }
    public void registerUser(String studentNo, String name, String password, String roleLevel) { userDAO.register(studentNo, name, password, roleLevel); }
    public boolean existsStudentNo(String studentNo) { return userDAO.existsStudentNo(studentNo); }
}
