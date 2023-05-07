package com.example.echo361;

import java.util.List;

public class Student extends User{

    CourseAVLtree courseAVLTree;
    List<String> conversation;

    String name;
    public Student(String userName, String passWord, CourseAVLtree courseAVLTree,List<String> conversation)  {
        super(userName, passWord);
        name = userName;
        this.conversation = conversation;
        this.courseAVLTree = courseAVLTree;
    }




    public void enrol(Course course){}

    public void drop(Course course){}

    public void chat(User user, String message){}

}
