package model;

public class User {
    private int userId;
    private String name;
    private String password;
    private String roleLevel;

    public User(
    	    int userId,
    	    String name,
    	    String password,
    	    String roleLevel
    ) {

    	    this.userId = userId;
    	    this.name = name;
    	    this.password = password;
    	    this.roleLevel = roleLevel;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
    
    public String getRoleLevel() {
        return roleLevel;
    }
}