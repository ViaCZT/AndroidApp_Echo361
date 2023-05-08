package com.example.echo361;

public class Admin extends User{
    public Admin(String userName, String passWord) {
        super(userName, passWord,null);
    }

    public void createCourse(Course course){}

    public void deleteCourse(Course course){}

    public void block(ForumPost forumPost){}
}
