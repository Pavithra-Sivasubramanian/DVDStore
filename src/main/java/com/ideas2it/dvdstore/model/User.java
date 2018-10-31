package com.ideas2it.dvdstore.model;

public class User {
    private Integer id;
    private String userName;
    private String password;
    private String role;
    private Boolean status;
    
    public User() {
    }
    
    public User(String userName, String password, String role, Boolean status) {
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.status = status;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
  
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    
    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
    
    public void setStatus(Boolean status) {
        this.status = status;
    }
    
    public Boolean getStatus() {
        return status;
    }
}
