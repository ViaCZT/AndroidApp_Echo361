/**
 * A student can take an unlimited number of classes.
 * @author Zetian Chen
 */
package com.example.echo361.Factory;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Student extends User{
    public Student() {
    }


    public Student(String userName, String passWord, ArrayList<String> courses)  {
        super(userName, passWord, courses);
    }

    @NonNull
    @Override
    public String toString() {
        return "Student{" + super.toString()+ '}';
    }
}
