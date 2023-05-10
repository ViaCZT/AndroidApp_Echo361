package com.example.echo361.Factory;


import java.util.ArrayList;

public abstract class User {

    private  String userName;
    private String passWord;
    private ArrayList<String> courses;
    public  String getUserName() {
        return userName;
    }

    public  void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }



    public User(String userName, String passWord, ArrayList<String> courses){
        this.userName = userName;
        this.passWord = passWord;
        this.courses = courses;
    }


}
