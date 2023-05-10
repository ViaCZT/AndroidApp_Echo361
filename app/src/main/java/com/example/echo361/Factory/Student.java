package com.example.echo361.Factory;

import java.util.ArrayList;
import java.util.List;

public class Student extends User{

    public List<String> getConversation() {
        return conversation;
    }

    public void setConversation(List<String> conversation) {
        this.conversation = conversation;
    }


    List<String> conversation;


    public Student(String userName, String passWord, ArrayList<String> courses, List<String> conversation)  {
        super(userName, passWord, courses);
        this.conversation = conversation;
    }


}
