package com.example.echo361;

import java.util.ArrayList;

public class Admin extends User{
    public Admin(String userName, String passWord, ArrayList<String> courses) {
        super(userName, passWord, courses);
    }

    public void createCourse(Course course){}

    public void deleteCourse(Course course){}

    public void block(ForumPost forumPost){}
}
