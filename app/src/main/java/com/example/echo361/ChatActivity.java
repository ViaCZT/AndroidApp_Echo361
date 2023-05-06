package com.example.echo361;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);
            msgRecyclerView = findViewById(R.id.msg_recycler_view);
            inputText = findViewById(R.id.input_text);
            send = findViewById(R.id.send);
            layoutManager = new LinearLayoutManager(this);
            adapter = new MsgAdapter(msgList = getData());
            msgRecyclerView.setLayoutManager(layoutManager);
            msgRecyclerView.setAdapter(adapter);

            send.setOnClickListener(v -> {
                String content = inputText.getText().toString();
                if(!content.equals("")) {
                    msgList.add(new Msg(content,Msg.TYPE_SEND));
                    adapter.notifyItemInserted(msgList.size()-1);
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    inputText.setText("");
                }

                if(msgList.size() == 2){
                    msgList.add(new Msg("What's your name?",Msg.TYPE_RECEIVED));
                    adapter.notifyItemInserted(msgList.size()-1);
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                }
                if(msgList.size() == 4){
                    msgList.add(new Msg("Nice to meet you,Bye!",Msg.TYPE_RECEIVED));
                    adapter.notifyItemInserted(msgList.size()-1);
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                }
            });
        }

        private List<Msg> getData(){
            List<Msg> list = new ArrayList<>();
            list.add(new Msg("Hello",Msg.TYPE_RECEIVED));
            return list;
        }

}