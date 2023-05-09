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
        toChat.setOnClickListener(view -> {
            Intent intent0 = new Intent(CourseMainpageActivity.this, ChatActivity.class);
            CourseMainpageActivity.this.startActivity(intent0);
        });

        Button toForum = findViewById(R.id.btn_gotoForum);

        toForum.setOnClickListener(v -> {
            String username = User.getname();
            if (username.equals("Zihan")) {
                Intent intent = new Intent(CourseMainpageActivity.this, ForumTotalActivity.class);
                intent.putExtra("isTeacher",false);
                Intent intent1 = getIntent();
                String courseName = intent1.getStringExtra("courseName");
                intent.putExtra("courseName",courseName);
                startActivity(intent);
            } else if (username.equals("Bernardo")) {
                Intent intent = new Intent(CourseMainpageActivity.this, ForumTotalActivity.class);
                intent.putExtra("isTeacher",true);
                intent.putExtra("courseName","COMP6442");
                startActivity(intent);
            } else {
                Intent intent = new Intent(CourseMainpageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}