package com.example.echo361.Factory;

import java.util.ArrayList;

/**
 * Admin only delete courses, they do not teach nor enroll any course.
 * @author Zetian Chen, u7564812
 */
public class Admin extends User{
    /**
     * Creates an instance of the Admin class with the specified username, password, and courses.
     *
     * @param userName The username of the admin.
     * @param passWord The password of the admin.
     * @param courses  The list of courses associated with the admin.
     */
    public Admin(String userName, String passWord, ArrayList<String> courses) {
        super(userName, passWord, courses);
    }

    /**
     * Creates a default instance of the Admin class.
     */
    public Admin() {
    }
}
