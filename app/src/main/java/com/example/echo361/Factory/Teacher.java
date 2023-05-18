package com.example.echo361.Factory;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * Every teacher only teach one course.
 * @author Zetian Chen, u7564812
 */
public class Teacher extends User{
    /**
     * Creates a default instance of the Teacher class.
     */
    public Teacher() {
    }

    /**
     * Creates an instance of the Teacher class with the specified username, password, and courses.
     *
     * @param userName The username of the teacher.
     * @param passWord The password of the teacher.
     * @param courses  The list of courses associated with the teacher.
     */
    public Teacher(String userName, String passWord, ArrayList<String> courses) {
        super(userName, passWord, courses);
    }

    /**
     * Returns a string representation of the Teacher object.
     *
     * @return A string representation of the Teacher object.
     */
    @NonNull
    @Override
    public String toString() {
        return "Teacher{" + super.toString()+ '}';
    }
}
