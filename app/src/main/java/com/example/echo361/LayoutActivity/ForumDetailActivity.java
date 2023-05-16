package com.example.echo361.LayoutActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.echo361.Database.FirebaseDAOImpl;
import com.example.echo361.Database.FirebaseDataCallback;
import com.example.echo361.Forum;
import com.example.echo361.R;
import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ForumDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_detail);

        TextView title = findViewById(R.id.tx_forumTitle);
        TextView content = findViewById(R.id.tx_forumPoster);
        Intent intent = getIntent();
        String course_name = intent.getStringExtra("courseName");
        int postIndex = intent.getIntExtra("postIndex", 0);
        title.setText(intent.getStringExtra("postTitle"));
        content.setText(intent.getStringExtra("postContent"));

        EditText editText = findViewById(R.id.ed_postIdea);

        Button newPost = findViewById(R.id.btn_postIdea);

        // 更新Firebase中的回复
        FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();
        firebaseDAOImpl.getData("Forums", course_name, new FirebaseDataCallback<String>() {
            @Override
            public void onDataReceived(String data) {
                Gson gson = new Gson();
                Forum forum = gson.fromJson(data, Forum.class);
                ArrayList<String> floors = forum.getPosts().get(postIndex).getFloors();
                Log.d("ForumDetailActivity", "Floors after get: " + floors.size());

                ListView listView = findViewById(R.id.list_forumPost);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ForumDetailActivity.this, android.R.layout.simple_list_item_1, floors);
                listView.setAdapter(arrayAdapter);

                newPost.setOnClickListener(view -> {
                    String content0 = editText.getText().toString();
                    editText.setText("");
                    arrayAdapter.notifyDataSetChanged();
                    // 更新课程论坛并将其存储在Firebase中
                    forum.getPosts().get(postIndex).getFloors().add(content0);
                    String updatedForumJson = gson.toJson(forum);
                    firebaseDAOImpl.storeData("Forums" , course_name, updatedForumJson);
                });

            }

            @Override
            public void onError(DatabaseError error) {
                // 处理错误
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
