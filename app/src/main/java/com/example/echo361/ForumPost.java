package com.example.echo361;

import java.util.Date;

public class ForumPost {
    private String title;
    private String content;
    private String author;
    private Date createdTime;
    private Date updatedTime;
    private Boolean visible;

    public ForumPost(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdTime = new Date();
        this.updatedTime = new Date();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void block(){
        boolean b = false;
        this.visible = b;
    }

    public boolean getVisible(){
        return this.visible;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

}
