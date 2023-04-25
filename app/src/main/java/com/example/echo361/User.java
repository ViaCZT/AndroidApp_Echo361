package com.example.echo361;


import java.util.List;

public abstract class User {
    private String userName;
    private String passWord;

    public User(String userName, String passWord){
        this.userName = userName;
        this.passWord = passWord;
    }

    public void login(){}

    public void logout(){}

    public List<?> search(){
        return null;
    }

    public void post(){}



}
