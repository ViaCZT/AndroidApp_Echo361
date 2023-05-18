package com.example.echo361;

import java.util.ArrayList;

/**
 * @Author Zihan Ai, u7528678
 * The ForumPost class represents a forum post with a title, content, author, and list of floors (replies).
 * This class also contains a flag to indicate whether the post is visible or not.
 */
public class ForumPost {
    private String title;
    private String content;
    private String author;
    private ArrayList<String> floors;
    private Boolean visible = true;

    /**
     * Constructs a new ForumPost with the given title, content, author, floors and visibility.
     *
     * @param title    The title of the forum post
     * @param content  The content of the forum post
     * @param author   The author of the forum post
     * @param floors   The list of floors (replies) of the forum post
     * @param visible  The visibility of the forum post (true if visible, false otherwise)
     */
    public ForumPost(String title, String content, String author, ArrayList<String> floors,boolean visible) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.floors = floors;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getFloors(){return this.floors;}

    public boolean getVisible(){
        return this.visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



}
