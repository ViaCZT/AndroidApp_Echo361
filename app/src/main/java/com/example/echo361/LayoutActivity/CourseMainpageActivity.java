package com.example.echo361.LayoutActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.echo361.R;

public class CourseMainpageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_mainpage);

        Intent intent1 = getIntent();
        String courseName = intent1.getStringExtra("courseName");
        String student_id = intent1.getStringExtra("student_id");
        String uid = intent1.getStringExtra("uid");

        // set course name to TextView
        TextView courseNameTextView = findViewById(R.id.tx_currCourseCode);
        courseNameTextView.setText(courseName);


        Button toChat = findViewById(R.id.btn_gotoChat);
        Intent intent2 = getIntent();
        String course_name =  intent2.getStringExtra("courseName");
        if(course_name==null || course_name.equals(""))
            Log.d("123","no name");
        else
            Log.d("123",course_name);
        boolean is_teacher = intent2.getBooleanExtra("is_teacher",false);
        toChat.setOnClickListener(view -> {
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