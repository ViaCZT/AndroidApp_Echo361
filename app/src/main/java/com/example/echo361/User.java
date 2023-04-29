package com.example.echo361;


import java.io.Serializable;
import java.util.List;

public abstract class User {

    private static String userName;
    private String passWord;

    public User(String userName, String passWord){
        this.userName = userName;
        this.passWord = passWord;
    }

    public static String getname(){
        return userName;
    }
    public void login(){}

    public void logout(){}

    public static List<?> search(String s){
        return null;
    }

    public void post(){}



}
