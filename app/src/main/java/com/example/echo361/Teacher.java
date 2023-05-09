package com.example.echo361;

import android.provider.MediaStore;

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

    public void block(ForumPost forumPost){}

    public void chat(User user, String message){}

    public void uploadVideo(Course course, MediaStore.Video video){}

}
