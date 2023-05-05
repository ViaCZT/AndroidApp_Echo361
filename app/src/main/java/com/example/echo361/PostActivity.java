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
import java.util.List;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        TextView title = findViewById(R.id.Post_title);
        TextView content = findViewById(R.id.Post_content);
        Intent intent = getIntent();
        title.setText(intent.getStringExtra("postTitle"));
        content.setText(intent.getStringExtra("postContent"));

        ArrayList<String> floors = new ArrayList<>();
        floors.add("I don't know");
        floors.add("lzsb");

        ListView listView = findViewById(R.id.floors);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,floors);
        listView.setAdapter(arrayAdapter);

        EditText editText = findViewById(R.id.new_content);

        Button newPost = findViewById(R.id.new_floor);
        newPost.setOnClickListener(view -> {
            String content0 = editText.getText().toString();
            floors.add(content0);
            arrayAdapter.notifyDataSetChanged();
        });
    }
}