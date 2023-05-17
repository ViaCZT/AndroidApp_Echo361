package com.example.echo361.LayoutActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.echo361.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Zihan Ai, u7528678
 * The ChatActivity class represents the chat page where users can send and receive messages.
 * This activity handles sending messages, updating the chat view, and listening for new messages.
 */
public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayList<Msg> msgList = new ArrayList<>();
    private RecyclerView msgRecyclerView;
    private EditText inputText;
    private Button send;
    private LinearLayoutManager layoutManager;
    private MsgAdapter adapter;

    private DatabaseReference chatReference;

    private String currentUserId; // Replace with the actual ID of the logged-in user
    private String receiverUserId; // Replace with the actual ID of the receiver user

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Retrieve intent data
        Intent intent = getIntent();
        currentUserId = intent.getStringExtra("currentUserId");
        receiverUserId = intent.getStringExtra("receiverUserId");
        Log.d("Conversation",currentUserId + "_" + receiverUserId);

        //set chat title to TextView
        TextView chatTitleTextView = findViewById(R.id.tx_chat_title);
        chatTitleTextView.setText("Hi, "+currentUserId+" ! \n You are chatting with "+receiverUserId);

        // Initialize RecyclerView, EditText, Button, and other UI components
        msgRecyclerView = findViewById(R.id.msg_recycler_view);
        inputText = findViewById(R.id.input_text);
        send = findViewById(R.id.send);
        layoutManager = new LinearLayoutManager(this);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setLayoutManager(layoutManager);
        msgRecyclerView.setAdapter(adapter);

        // Set up the Firebase database reference
        chatReference = FirebaseDatabase.getInstance().getReference("chats");

        // Add a ChildEventListener to listen for new messages
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    Msg msg = messageSnapshot.getValue(Msg.class);
                    if (msg.getSenderId().equals(currentUserId) && msg.getReceiverId().equals(receiverUserId)) {
                        msgList.add(new Msg(msg.getContent(), msg.getType(), msg.getSenderId(), msg.getReceiverId()));
                        adapter.notifyItemInserted(msgList.size() - 1);
                        msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    }
                }
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postMessages:onCancelled", databaseError.toException());
            }
        };



        chatReference.addChildEventListener(childEventListener);

        // Set the OnClickListener for the send button
        send.setOnClickListener(v -> {
            String content = inputText.getText().toString();
            if (!content.equals("")) {
                String chatId1 = currentUserId + "_" + receiverUserId;
                String chatId2 = receiverUserId + "_" + currentUserId;
                DatabaseReference chatRef1 = chatReference.child(chatId1).push();
                DatabaseReference chatRef2 = chatReference.child(chatId2).push();

                // sent
                chatRef1.setValue(new Msg(content, Msg.TYPE_SEND, currentUserId, receiverUserId));
                //received
                chatRef2.setValue(new Msg(content, Msg.TYPE_RECEIVED, receiverUserId, currentUserId));

                if(msgList.size()!=0){
                    msgList.add(new Msg(content, Msg.TYPE_SEND, currentUserId, receiverUserId));
                    adapter.notifyItemInserted(msgList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                }

                inputText.setText("");
            }
        });
    }

}
