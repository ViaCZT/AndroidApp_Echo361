package com.example.echo361.LayoutActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.echo361.Course;
import com.example.echo361.Database.FirebaseDAOImpl;
import com.example.echo361.R;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

public class CourseMainpageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_mainpage);

        Intent intent1 = getIntent();
        String courseName = intent1.getStringExtra("courseName");
        String student_id = intent1.getStringExtra("student_id");
        String uid = intent1.getStringExtra("uid");

//        FirebaseApp.initializeApp(getBaseContext());
//        FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();
//
//        ArrayList<Course> courses = new ArrayList<Course>();
//
//        ArrayAdapter courseListAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, courses);
//
//        readCourseDate(firebaseDAOImpl, courses, courseListAdapter, courseName);
//
//        Log.d("Search chat courses2", "courses from function" + courses);
//
//        Log.d("Search chat courses2", "courses from function" + courses);

        //

        Button toChat = findViewById(R.id.btn_gotoChat);
        Intent intent2 = getIntent();
        String course_name =  intent2.getStringExtra("courseName");
        if(course_name==""||course_name==null)
            Log.d("123","no name");
        else
            Log.d("123",course_name);
        boolean is_teacher = intent2.getBooleanExtra("is_teacher",false);
        toChat.setOnClickListener(view -> {
//            Intent intent0 = new Intent(CourseMainpageActivity.this, ChatActivity.class);
            Intent intent0 = new Intent(CourseMainpageActivity.this, SearchChatTarget.class);
            intent0.putExtra("courseName", courseName);
            intent0.putExtra("student_id", student_id);
            intent0.putExtra("uid",uid);
            Log.d("currentID",uid);

            CourseMainpageActivity.this.startActivity(intent0);
        });

        Button toForum = findViewById(R.id.btn_gotoForum);

        toForum.setOnClickListener(v -> {
            Intent intent = new Intent(CourseMainpageActivity.this, ForumTotalActivity.class);
            intent.putExtra("courseName",course_name);
            intent.putExtra("is_teacher", is_teacher);
            startActivity(intent);
        });
    }
}