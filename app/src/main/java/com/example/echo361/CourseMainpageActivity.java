package com.example.echo361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.IOException;

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

        VideoView videoView = findViewById(R.id.videoView);

//        MediaController mediaController = new MediaController(this);
//        mediaController.setAnchorView(videoView);
//        videoView.setMediaController(mediaController);
//
//        String path = "file:///android_asset/COMP2100APKGenerationGroupAssignment.mp4";
//        Uri uri = Uri.parse(path);
//        videoView.setVideoURI(uri);
//
//        videoView.requestFocus();
//        videoView.start();

        //实例化播放内核
//        android.media.MediaPlayer mediaPlayer = new android.media.MediaPlayer();
//        //获得播放源访问入口
//        AssetManager am = getAssets();
//        try {
//            AssetFileDescriptor afd = am.openFd("COMP2100APKGenerationGroupAssignment.mp4");// 注意这里的区别
//            //给MediaPlayer设置播放源
//            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //设置准备就绪状态监听
//        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                // 开始播放
//                mediaPlayer.start();
//            }
//        });
//        //准备播放
//        mediaPlayer.prepareAsync();


    }
}