package com.example.echo361;

import java.util.ArrayList;


public class Forum {

    private String courseName;
    private ArrayList<ForumPost> posts;

    public Forum(String courseName, ArrayList<ForumPost> posts){
        this.courseName = courseName;
        this.posts = posts;
    }

    public void setCourseName(String courseName){this.courseName = courseName;}

    public ArrayList<ForumPost> getPosts(){
        return posts;
    }

    public void addPost(ForumPost forumPost){
        this.posts.add(forumPost);
    }


}
