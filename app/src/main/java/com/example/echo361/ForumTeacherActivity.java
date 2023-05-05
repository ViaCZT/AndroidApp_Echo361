package com.example.echo361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ForumTeacherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_teacher);
        ListView listView = findViewById(R.id.posts_list);
        ForumPost forumPost0 = new ForumPost("Tokenizer & Parser", "Hi Teaching Team, Just for confirmation, is the implementation of parser and tokenizer compulsory in our app (mainly for assisting the search function)? Thanks in advance","Student1");
        ForumPost forumPost1 = new ForumPost("Problem about AVLtree", "Hi Teaching Team, Just for confirmation, is the implementation of parser and tokenizer compulsory in our app (mainly for assisting the search function)? Thanks in advance","Student2");
        ForumPost forumPost2 = new ForumPost("Problem about AVLtree", "Hi Teaching Team, Just for confirmation, is the implementation of parser and tokenizer compulsory in our app (mainly for assisting the search function)? Thanks in advance","Student3");
        ArrayList<String> postTitles = new ArrayList<>();
        postTitles.add(forumPost0.getTitle());
        postTitles.add(forumPost1.getTitle());
        postTitles.add(forumPost2.getTitle());
        ArrayList<ForumPost> posts = new ArrayList<>();
        posts.add(forumPost0);
        posts.add(forumPost1);
        posts.add(forumPost2);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,postTitles);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(ForumTeacherActivity.this, PostActivity.class);
            intent.putExtra("postTitle", posts.get(i).getTitle().toString());
            intent.putExtra("postContent", posts.get(i).getContent().toString());
            ForumTeacherActivity.this.startActivity(intent);
        });

        EditText title = findViewById(R.id.editTextNewPostTitleTeacher);
        EditText content = findViewById(R.id.editTextNewPostContentTeacher);

        Button newPost = findViewById(R.id.btn_teacher_post);
        newPost.setOnClickListener(view -> {
            String title0 = title.getText().toString();
            String content0 = content.getText().toString();
            Intent intent = getIntent();
            String name0 = intent.getStringExtra("name");
            ForumPost forumPost = new ForumPost(title0,content0,name0);
            postTitles.add(forumPost.getTitle());
            posts.add(forumPost);
            arrayAdapter.notifyDataSetChanged();
        });



    }
}