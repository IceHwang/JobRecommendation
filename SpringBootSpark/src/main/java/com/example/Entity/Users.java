package com.example.Entity;

public class Users {
    private String user_id;
    private String user_nikname;
    private String user_password;
    private String user_mail;

    public Users(String user_id, String user_nikname, String user_password, String user_mail) {
        this.user_id = user_id;
        this.user_nikname = user_nikname;
        this.user_password = user_password;
        this.user_mail = user_mail;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_nikname() {
        return user_nikname;
    }

    public void setUser_nikname(String user_nikname) {
        this.user_nikname = user_nikname;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }
}
