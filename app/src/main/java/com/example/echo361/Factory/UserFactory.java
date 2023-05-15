package com.example.echo361.Factory;

import java.util.ArrayList;

public class UserFactory {
    public User getUser(String userType, String userName, String passWord, ArrayList<String> courses){
        if (userType == null){
            return null;
        }
        if (userType.equalsIgnoreCase("student")){
            return new Student(userName,passWord,courses);
        } else if (userType.equalsIgnoreCase("teacher")) {
            return new Teacher(userName,passWord,courses);
        } else if (userType.equalsIgnoreCase("admin")) {
            return new Admin(userName,passWord,courses);
        }
        return null;
    }
}
