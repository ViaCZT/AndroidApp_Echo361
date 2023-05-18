package com.example.echo361.Factory;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * @author Zetian Chen, u7564812
 */
public abstract class User {
    private  String userName; // "firstName lastName"
    private String passWord; // uid
    private ArrayList<String> courses;

    /**
     * Creates a default instance of the User class.
     */
    public User() {
    }

    /**
     * Creates an instance of the User class with the specified username, password, and courses.
     *
     * @param userName The username of the user.
     * @param passWord The password of the user.
     * @param courses  The list of courses associated with the user.
     */
    public User(String userName, String passWord, ArrayList<String> courses){
        this.userName = userName;
        this.passWord = passWord;
        this.courses = courses;
    }

    /**
     * Returns the username of the user.
     * @return The username of the user.
     */
    public  String getUserName() {
        return userName;
    }

    /**
     * Sets the username of the user.
     * @param userName The username of the user.
     */
    public  void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the password of the user.
     * @return The password of the user.
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * Sets the password of the user.
     * @param passWord The password of the user.
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * Returns the list of courses associated with the user.
     * @return The list of courses associated with the user.
     */
    public ArrayList<String> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses associated with the user.
     * @param courses The list of courses associated with the user.
     */
    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }

    /**
     * Returns a string representation of the User object.
     * @return A string representation of the User object.
     */
    @NonNull
    @Override
    public String toString() {
        return "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", courses=" + courses;
    }

}
