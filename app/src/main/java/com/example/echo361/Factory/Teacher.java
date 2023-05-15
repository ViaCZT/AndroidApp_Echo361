package com.example.echo361.Factory;

import java.util.ArrayList;

public class Teacher extends User{
    public Teacher() {
    }

    public Teacher(String userName, String passWord, ArrayList<String> courses) {
        super(userName, passWord, courses);
    }

    @Override
    public String toString() {
        return "Teacher{" + super.toString()+ '}';
    }
}
