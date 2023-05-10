package com.example.echo361.LayoutActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.echo361.R;

import java.util.ArrayList;

public class MyCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);

        Intent intent0 = getIntent();
        ArrayList<String> courseName = intent0.getStringArrayListExtra("courses_list");


        ListView listView = findViewById(R.id.list_currCourse);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseName);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(MyCourseActivity.this, CourseMainpageActivity.class);
            intent.putExtra("is_teacher",false);
            intent.putExtra("courseName",courseName.get(i));
                Log.d("1",courseName.get(i));
            MyCourseActivity.this.startActivity(intent);
        });
    }
}