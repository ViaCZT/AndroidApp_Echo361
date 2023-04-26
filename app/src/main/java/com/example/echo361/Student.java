package com.example.echo361;

import java.util.List;

public class Student extends User{

    AVLTree<Course> courseAVLTree;
    List<String> conversation;
    public Student(String userName, String passWord, AVLTree<Course> courseAVLTree,List<String> conversation)  {
        super(userName, passWord);
        this.conversation = conversation;
        this.courseAVLTree = courseAVLTree;
    }

    public void enrol(Course course){}

    public void drop(Course course){}

    public void chat(User user, String message){}

}
