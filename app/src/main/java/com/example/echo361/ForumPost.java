package com.example.echo361;

import java.util.ArrayList;

public class ForumPost {
    private String title;
    private String content;
    private String author;
    private ArrayList<String> floors;
    private Boolean visible = true;

    public ForumPost(String title, String content, String author, ArrayList<String> floors,boolean visible) {
        this.title = title;
        this.content = content;
        this.author = author;
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

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getFloors(){return this.floors;}

    public void setFloors(ArrayList<String> floors) {
        this.floors = floors;
    }


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

    public void block(){
        this.visible = false;
    }

    public void addFloor(String floorContent){this.floors.add(floorContent);}


}
