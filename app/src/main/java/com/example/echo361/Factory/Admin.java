/**
 * Admin only delete courses, they do not teach nor enroll any course.
 * @author Zetian Chen, u7564812
 */
package com.example.echo361.Factory;

import java.util.ArrayList;

public class Admin extends User{
    public Admin(String userName, String passWord, ArrayList<String> courses) {
        super(userName, passWord, courses);
    }

    public Admin() {
    }
}
