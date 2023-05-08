package com.example.echo361;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class User {

    private static String userName;
    private String passWord;
    private ArrayList<String> courses;

    public User(String userName, String passWord,ArrayList<String> courses){
        User.userName = userName;
        this.passWord = passWord;
        this.courses = courses;
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
