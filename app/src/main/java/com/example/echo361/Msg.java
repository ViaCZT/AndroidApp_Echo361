package com.example.echo361;

public class Msg {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SEND = 1;
    private String content;
    private int type;
    private String senderId;
    private String receiverId;

    public Msg() {
        // Default constructor required for calls to DataSnapshot.getValue(Msg.class)
    }

    public Msg(String content, int type, String senderId, String receiverId) {
        this.content = content;
        this.type = type;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }
}
