package com.example.crescendo.classmodel;

public class users extends Object{
    private String username, password;

    public users(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public users(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
