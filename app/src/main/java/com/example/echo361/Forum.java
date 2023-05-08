package com.example.echo361;

import java.util.List;

public class Forum {

    private String courseName;
    private List<ForumPost> posts;

    public Forum(String courseName,List<ForumPost> posts){
        this.courseName = courseName;
        this.posts = posts;
    }

    public void setCourseName(String courseName){this.courseName = courseName;}


}
