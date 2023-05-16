package com.example.echo361.LayoutActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private List<Msg> msgList = new ArrayList<>();
    private RecyclerView msgRecyclerView;
    private EditText inputText;
    private Button send;
    private LinearLayoutManager layoutManager;
    private MsgAdapter adapter;

    private DatabaseReference chatReference;

    private String currentUserId; // 将这里替换为登录用户的实际 ID
    private String receiverUserId; // 将这里替换为接收者用户的实际 ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        currentUserId = intent.getStringExtra("currentUserId");
        receiverUserId = intent.getStringExtra("receiverUserId");
        Log.d("Conversation",currentUserId + "_" + receiverUserId);


        msgRecyclerView = findViewById(R.id.msg_recycler_view);
        inputText = findViewById(R.id.input_text);
        send = findViewById(R.id.send);
        layoutManager = new LinearLayoutManager(this);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setLayoutManager(layoutManager);
        msgRecyclerView.setAdapter(adapter);

        chatReference = FirebaseDatabase.getInstance().getReference("chats");

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

        send.setOnClickListener(v -> {
            String content = inputText.getText().toString();
            if (!content.equals("")) {
                String chatId1 = currentUserId + "_" + receiverUserId;
                String chatId2 = receiverUserId + "_" + currentUserId;
                DatabaseReference chatRef1 = chatReference.child(chatId1).push();
                DatabaseReference chatRef2 = chatReference.child(chatId2).push();

                // 一条标记为发送
                chatRef1.setValue(new Msg(content, Msg.TYPE_SEND, currentUserId, receiverUserId));
                //标记为接收到
                chatRef2.setValue(new Msg(content, Msg.TYPE_RECEIVED, receiverUserId, currentUserId));

                msgList.add(new Msg(content, Msg.TYPE_SEND, currentUserId, receiverUserId));
                adapter.notifyItemInserted(msgList.size() - 1);
                msgRecyclerView.scrollToPosition(msgList.size() - 1);


                inputText.setText("");
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        Toast.makeText(getApplicationContext(), "onStart!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(), "onRestart!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "onResume!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), "onPause!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "onStop!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "onDestroy!", Toast.LENGTH_LONG).show();
    }

}
