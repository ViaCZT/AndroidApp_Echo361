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

        Button toChat = findViewById(R.id.btn_gotoChat);
        Intent intent2 = getIntent();
        String course_name =  intent2.getStringExtra("course_name");
        boolean is_teacher = intent2.getBooleanExtra("is_teacher",false);
        toChat.setOnClickListener(view -> {
            Intent intent0 = new Intent(CourseMainpageActivity.this, ChatActivity.class);
            CourseMainpageActivity.this.startActivity(intent0);
        });

        Button toForum = findViewById(R.id.btn_gotoForum);

        toForum.setOnClickListener(v -> {
            Intent intent = new Intent(CourseMainpageActivity.this, ForumTotalActivity.class);
            intent.putExtra("courseName",course_name);
            if (!is_teacher)
                intent.putExtra("is_teacher",false);
            else
                intent.putExtra("is_teacher",true);
            startActivity(intent);
        });

    }
}