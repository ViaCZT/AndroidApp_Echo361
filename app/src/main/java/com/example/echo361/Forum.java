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

    public String getCourseName() {
        return courseName;
    }

    public void setPosts(ArrayList<ForumPost> posts) {
        this.posts = posts;
    }

    public ArrayList<ForumPost> getPosts(){
        return posts;
    }

    public void addPost(ForumPost forumPost){
        this.posts.add(forumPost);
    }


}
