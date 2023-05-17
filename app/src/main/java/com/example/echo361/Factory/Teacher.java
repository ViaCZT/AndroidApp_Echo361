/**
 * Every teacher only teach one course.
 * @author Zetian Chen
 */
package com.example.echo361.Factory;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Teacher extends User{
    public Teacher() {
    }

    public Teacher(String userName, String passWord, ArrayList<String> courses) {
        super(userName, passWord, courses);
    }

    @NonNull
    @Override
    public String toString() {
        return "Teacher{" + super.toString()+ '}';
    }
}
