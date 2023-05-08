package com.example.echo361;

import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends User{
    List<String> conversation;
    String name;
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


    public Teacher(String userName, String passWord, ArrayList<String> courses, List<String> conversation) {
        super(userName, passWord,courses);
        this.conversation = conversation;
        this.name = userName;
    }

    public void block(ForumPost forumPost){}

    public void chat(User user, String message){}

    public void uploadVideo(Course course, MediaStore.Video video){}

}
