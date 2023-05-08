package com.example.echo361;

import java.util.ArrayList;

public class Conversation {

    private User user1;
    private User user2;
    private ArrayList<String> content;

    public Conversation(User user1,User user2,User sender,ArrayList<String> content){
        this.user1 = user1;
        this.user2 = user2;
        this.content = content;
    }

    public void addContent(User user,String s){
        String message = user.getname()+" : "+s;
        content.add(message);
    }

}
