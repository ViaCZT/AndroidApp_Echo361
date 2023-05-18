package com.example.echo361;

import java.util.ArrayList;

/**
 * @Author Zihan Ai, u7528678
 * The Forum class represents a forum that contains a list of forum posts.
 */
public class Forum {

    private ArrayList<ForumPost> posts;

    /**
     * Constructs a new Forum for a specific course with the given list of forum posts.
     *
     * @param courseName The name of the course the forum belongs to
     * @param posts      The list of forum posts in the forum
     */
    public Forum(String courseName, ArrayList<ForumPost> posts){
        this.posts = posts;
    }

    public ArrayList<ForumPost> getPosts(){
        return posts;
    }



}
