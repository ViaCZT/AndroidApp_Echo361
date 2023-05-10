package com.example.echo361.LayoutActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import com.example.echo361.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private List<Msg> msgList = new ArrayList<>();
    private RecyclerView msgRecyclerView;
    private EditText inputText;
    private Button send;
    private LinearLayoutManager layoutManager;
    private MsgAdapter adapter;

    private DatabaseReference chatReference;

    private String currentUserId = "current_user_id"; // 将这里替换为登录用户的实际 ID
    private String receiverUserId = "receiver_user_id"; // 将这里替换为接收者用户的实际 ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        msgRecyclerView = findViewById(R.id.msg_recycler_view);
        inputText = findViewById(R.id.input_text);
        send = findViewById(R.id.send);
        layoutManager = new LinearLayoutManager(this);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setLayoutManager(layoutManager);
        msgRecyclerView.setAdapter(adapter);

        chatReference = FirebaseDatabase.getInstance().getReference("chats");

        loadChatHistory(); // 调用此方法加载聊天记录

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Msg msg = dataSnapshot.getValue(Msg.class);
                if ((msg.getSenderId().equals(currentUserId) && msg.getReceiverId().equals(receiverUserId)) ||
                        (msg.getSenderId().equals(receiverUserId) && msg.getReceiverId().equals(currentUserId))) {
                    msgList.add(new Msg(msg.getContent(), msg.getType(), msg.getSenderId(), msg.getReceiverId()));
                    adapter.notifyItemInserted(msgList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postMessages:onCancelled", databaseError.toException());
            }
        };

        chatReference.addChildEventListener(childEventListener);

        send.setOnClickListener(v -> {
            String content = inputText.getText().toString();
            if (!content.equals("")) {
                Msg msg = new Msg(content, Msg.TYPE_SEND, currentUserId, receiverUserId);
                chatReference.push().setValue(msg);
                inputText.setText("");
            }
        });
    }

    private void loadChatHistory() {
        chatReference.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Msg msg = dataSnapshot.getValue(Msg.class);
                if ((msg.getSenderId().equals(currentUserId) && msg.getReceiverId().equals(receiverUserId)) ||
                        (msg.getSenderId().equals(receiverUserId) && msg.getReceiverId().equals(currentUserId))) {
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadChatHistory:onCancelled", databaseError.toException());
            }
        });
    }



}
