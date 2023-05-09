package com.example.echo361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;

public class StudentMainpageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_mainpage);

        Intent intent = getIntent();
        ArrayList<String> courses_list= intent.getStringArrayListExtra("courses_list");

        Button toEnroll = findViewById(R.id.btn_toEnroll);
        toEnroll.setOnClickListener(view -> {
            Intent intent0 = new Intent(StudentMainpageActivity.this, EnrollActivity.class);
            StudentMainpageActivity.this.startActivity(intent0);
        });

        Button toDrop = findViewById(R.id.btn_toDrop);
        toDrop.setOnClickListener(view -> {
            Intent intent0 = new Intent(StudentMainpageActivity.this, DropActivity.class);
            StudentMainpageActivity.this.startActivity(intent0);
        });

        Button ToMyCourse = findViewById(R.id.btn_toMyCourse);
        ToMyCourse.setOnClickListener(view -> {
            Intent intent0 = new Intent(StudentMainpageActivity.this, MyCourseActivity.class);
            intent0.putStringArrayListExtra("courses_list",courses_list);
            StudentMainpageActivity.this.startActivity(intent0);
        });
    }
}