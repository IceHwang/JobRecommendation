package com.example.demo.entity;

public class Users_user {
    private String email;

    private Integer id;

    private String password;

    public Users_user(String email,String password) {
        this.email = email;
        this.password = password;
    }

    public Users_user() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}