package com.example.echo361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CourseMainpageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_mainpage);

        Button toChat = findViewById(R.id.btn_gotoChat);
        toChat.setOnClickListener(view -> {
            Intent intent0 = new Intent(CourseMainpageActivity.this, SearchChatTarget.class);
            CourseMainpageActivity.this.startActivity(intent0);
        });

        Button toForum = findViewById(R.id.btn_gotoForum);

        toForum.setOnClickListener(v -> {
            String username = User.getname();
            if (username.equals("Zihan")) {
                Intent intent = new Intent(CourseMainpageActivity.this, ForumTeacherActivity.class);
                intent.putExtra("isTeacher",false);
                startActivity(intent);
            } else if (username.equals("Bernardo")) {
                Intent intent = new Intent(CourseMainpageActivity.this, ForumTeacherActivity.class);
                intent.putExtra("isTeacher",true);
                startActivity(intent);
            } else {
                Intent intent = new Intent(CourseMainpageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}