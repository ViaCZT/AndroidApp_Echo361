package com.example.echo361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ForumDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_detail);

        TextView title = findViewById(R.id.tx_forumTitle);
        TextView content = findViewById(R.id.tx_forumPoster);
        Intent intent = getIntent();
        title.setText(intent.getStringExtra("postTitle"));
        content.setText(intent.getStringExtra("postContent"));

        ArrayList<String> floors = intent.getStringArrayListExtra("floors");


        ListView listView = findViewById(R.id.list_forumPost);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,floors);
        listView.setAdapter(arrayAdapter);

        EditText editText = findViewById(R.id.ed_postIdea);

        Button newPost = findViewById(R.id.btn_postIdea);
        newPost.setOnClickListener(view -> {
            String content0 = editText.getText().toString();
            floors.add(content0);
            arrayAdapter.notifyDataSetChanged();
        });
    }
}