package com.example.echo361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class CourseMainpageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_mainpage);

        Button toForum = findViewById(R.id.btn_gotoForum);
        toForum.setOnClickListener(view -> {
            Intent intent0 = new Intent(CourseMainpageActivity.this, ForumStudentActivity.class);
            CourseMainpageActivity.this.startActivity(intent0);
        });

        Button toChat = findViewById(R.id.btn_gotoChat);
        toChat.setOnClickListener(view -> {
            Intent intent0 = new Intent(CourseMainpageActivity.this, SearchChatTarget.class);
            CourseMainpageActivity.this.startActivity(intent0);
        });
    }
}