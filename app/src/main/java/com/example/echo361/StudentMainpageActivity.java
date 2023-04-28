package com.example.echo361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class StudentMainpageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_mainpage);

        Intent intent = getIntent();
        Student student = (Student) intent.getSerializableExtra("Student");


        Button chat = findViewById(R.id.chat);
        chat.setOnClickListener(view -> {
            Intent intent0 = new Intent(StudentMainpageActivity.this, SearchChatTarget.class);
            StudentMainpageActivity.this.startActivity(intent0);
        });
    }
}