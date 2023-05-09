package com.example.echo361;

import java.util.ArrayList;

public class Conversation {
    private ArrayList<Msg> messages;
    private String senderId;
    private String receiverId;

    public Conversation(String senderId, String receiverId, ArrayList<Msg> messages) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messages = messages;
    }

    public void addMessage(Msg message) {
        messages.add(message);
    }

    public ArrayList<Msg> getMessages() {
        return messages;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }
}