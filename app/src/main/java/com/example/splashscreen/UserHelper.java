package com.example.splashscreen;

public class UserHelper {
    private String name,password;

    public UserHelper(String id,String password) {
        this.password = password;
        this.name=id;
    }

    public UserHelper() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}