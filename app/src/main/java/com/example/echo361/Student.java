package com.example.echo361;

import java.util.ArrayList;
import java.util.List;

public class Student extends User{

    public List<String> getConversation() {
        return conversation;
    }

    public void setConversation(List<String> conversation) {
        this.conversation = conversation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    List<String> conversation;

    private String name;
    private String uid;

    public Student(String userName, String passWord, ArrayList<String> courses, List<String> conversation)  {
        super(userName, passWord,courses);
        name = userName;
        uid = passWord;
        this.conversation = conversation;

    }




    public void enrol(Course course){}

    public void drop(Course course){}

    public void chat(User user, String message){}

}
