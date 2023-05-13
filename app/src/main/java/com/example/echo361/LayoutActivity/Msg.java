package com.example.echo361.LayoutActivity;
/**
 * @author Zihan Ai, u7528678
 * @author Yijun Huang, u7564899
 *
 * The class Msg is for implementing the P2P chat feature.
 *
 * reference <a href="https://blog.csdn.net/JMW1407/article/details/120252484">...</a>
 */
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

    /**
     * @param content the content of message
     * @param type the tpye of message
     * @param senderId the id of message sender
     * @param receiverId the id of message receiver
     */
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
