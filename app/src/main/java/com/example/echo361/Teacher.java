package com.example.echo361;

import android.provider.MediaStore;

import java.util.List;

public class Teacher extends User{
    AVLTree<Course> courseAVLTree;
    List<String> conversation;
    public Teacher(String userName, String passWord, AVLTree<Course> courseAVLTree, List<String> conversation) {
        super(userName, passWord);
        this.conversation = conversation;
        this.courseAVLTree = courseAVLTree;

    }

    public void block(ForumPost forumPost){}

    public void chat(User user, String message){}

    public void uploadVideo(Course course, MediaStore.Video video){}

}
