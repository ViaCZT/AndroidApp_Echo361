package com.example.echo361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MyCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);

        ArrayList<String> courseName= new ArrayList<>();
        courseName.add("1234");
        courseName.add(("dwfw"));
        courseName.add("dwf");
        courseName.add("wefwe");

        ListView listView = findViewById(R.id.list_currCourse);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,courseName);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(MyCourseActivity.this, CourseMainpageActivity.class);
            intent.putExtra("isTeacher",false);
            intent.putExtra("courseName", courseName.get(i));
            MyCourseActivity.this.startActivity(intent);
        });
    }
}