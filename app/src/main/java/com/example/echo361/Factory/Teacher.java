package com.example.echo361.Factory;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends User{
    List<String> conversation;
    public List<String> getConversation() {
        return conversation;
    }

    public void setConversation(List<String> conversation) {
        this.conversation = conversation;
    }


    public Teacher(String userName, String passWord, ArrayList<String> courses, List<String> conversation) {
        super(userName, passWord, courses);
        this.conversation = conversation;
    }


}
