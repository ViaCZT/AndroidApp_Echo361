package com.example.echo361.LayoutActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.echo361.R;

public class CourseMainpageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_mainpage);

        Button toChat = findViewById(R.id.btn_gotoChat);
        Intent intent2 = getIntent();
        String course_name =  intent2.getStringExtra("courseName");
        if(course_name==""||course_name==null)
            Log.d("123","no name");
        else
            Log.d("123",course_name);
        boolean is_teacher = intent2.getBooleanExtra("is_teacher",false);
        toChat.setOnClickListener(view -> {
            Intent intent0 = new Intent(CourseMainpageActivity.this, ChatActivity.class);
            CourseMainpageActivity.this.startActivity(intent0);
        });

        Button toForum = findViewById(R.id.btn_gotoForum);

        toForum.setOnClickListener(v -> {
            Intent intent = new Intent(CourseMainpageActivity.this, ForumTotalActivity.class);
            intent.putExtra("courseName",course_name);
            intent.putExtra("is_teacher", is_teacher);
            startActivity(intent);
        });

        VideoView videoView = findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/raw/video1"));
        videoView.start();

    }
}