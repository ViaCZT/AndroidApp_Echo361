package com.example.echo361.Factory;

import java.util.ArrayList;

/**
 * The UserFactory class is responsible for creating instances of User subclasses based on the provided user type.
 * It encapsulates the object creation logic and returns the appropriate User object.
 *
 * @author Zetian Chen, u7564812
 */
public class UserFactory {
    /**
     * Creates and returns a User object based on the specified user type, username, password, and courses.
     *
     * @param userType The type of user to create ("student", "teacher", or "admin").
     * @param userName The username of the user.
     * @param passWord The password of the user.
     * @param courses  The list of courses associated with the user.
     * @return A User object of the specified type, or null if the userType is invalid.
     */
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
