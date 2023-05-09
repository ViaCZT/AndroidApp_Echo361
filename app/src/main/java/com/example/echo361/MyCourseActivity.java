package com.example.echo361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MyCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);

        Intent intent0 = getIntent();
        ArrayList<String> courseName = intent0.getStringArrayListExtra("courses_list");
        if (courseName == null) {
            Log.e("MyCourseActivity", "Received null courseName");
            courseName = new ArrayList<>();
        }



        ListView listView = findViewById(R.id.list_currCourse);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,courseName);
        listView.setAdapter(arrayAdapter);
        ArrayList<String> finalCourseName = courseName;
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(MyCourseActivity.this, CourseMainpageActivity.class);
            intent.putExtra("isTeacher",false);
            intent.putExtra("courseName", finalCourseName.get(i));
            MyCourseActivity.this.startActivity(intent);
        });
    }
}