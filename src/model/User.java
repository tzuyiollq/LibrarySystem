package model;

public class User {
    private int userId;
    private String studentNo;
    private String name;
    private String password;
    private String roleLevel;
    private String status;

    public User(int userId, String studentNo, String name, String password, String roleLevel, String status) {
        this.userId = userId;
        this.studentNo = studentNo;
        this.name = name;
        this.password = password;
        this.roleLevel = roleLevel;
        this.status = status;
    }

    public int getUserId() { return userId; }
    public String getStudentNo() { return studentNo; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getRoleLevel() { return roleLevel; }
    public String getStatus() { return status; }
    public void setRoleLevel(String roleLevel) { this.roleLevel = roleLevel; }
    public void setStatus(String status) { this.status = status; }
}
