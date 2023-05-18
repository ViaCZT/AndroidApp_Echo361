package com.example.echo361.Factory;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * A student can take an unlimited number of classes.
 * @author Zetian Chen, u7564812
 */
public class Student extends User{
    /**
     * Creates a default instance of the Student class.
     */
    public Student() {
    }

    /**
     * Creates an instance of the Student class with the specified username, password, and courses.
     *
     * @param userName The username of the student.
     * @param passWord The password of the student.
     * @param courses  The list of courses associated with the student.
     */
    public Student(String userName, String passWord, ArrayList<String> courses)  {
        super(userName, passWord, courses);
    }

    /**
     * Returns a string representation of the Student object.
     *
     * @return A string representation of the Student object.
     */
    @NonNull
    @Override
    public String toString() {
        return "Student{" + super.toString()+ '}';
    }
}
