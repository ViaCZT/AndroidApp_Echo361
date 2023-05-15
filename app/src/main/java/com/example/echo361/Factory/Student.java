package com.example.echo361.Factory;

import java.util.ArrayList;

public class Student extends User{
    public Student() {
    }


    public Student(String userName, String passWord, ArrayList<String> courses)  {
        super(userName, passWord, courses);
    }

    @Override
    public String toString() {
        return "Student{" + super.toString()+ '}';
    }
}
