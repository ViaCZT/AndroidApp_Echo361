package com.example.echo361;

import java.util.ArrayList;
import java.util.Date;

public class ForumPost {
    private String title;
    private String content;
    private String author;

    private ArrayList<String> floors;
    private Date createdTime;
    private Date updatedTime;
    private Boolean visible = true;

    public ForumPost(String title, String content, String author, ArrayList<String> floors,boolean visible) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdTime = new Date();
        this.updatedTime = new Date();
        this.floors = floors;
    }
    public ForumPost(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<String> getFloors(){return this.floors;}

    public void setContent(String content) {
        this.content = content;
    }

    public void block(){
        boolean b = false;
        this.visible = b;
    }

    public void addFloor(String floorContent){this.floors.add(floorContent);}

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
