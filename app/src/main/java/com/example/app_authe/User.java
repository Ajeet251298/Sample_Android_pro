package com.example.app_authe;


public class User {

    String userid;
    String email;
    String password;


    public User()
    {

    }

    public User(String userid, String email, String password) {
        this.userid = userid;
        this.email = email;
        this.password = password;

    }

    public String getUserid() {
        return userid;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


}
