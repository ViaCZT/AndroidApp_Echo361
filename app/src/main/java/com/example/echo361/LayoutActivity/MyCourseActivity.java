package com.example.echo361.LayoutActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.echo361.Database.FirebaseDAOImpl;
import com.example.echo361.Database.FirebaseDataCallback;
import com.example.echo361.Factory.Student;
import com.example.echo361.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;

public class MyCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);

        Intent intent0 = getIntent();
        ArrayList<String> courseName = intent0.getStringArrayListExtra("courses_list");
        String logedStudent_id = intent0.getStringExtra("student_id");
        String uid = intent0.getStringExtra("uid");
        Log.d("my course", String.valueOf(courseName));

        FirebaseApp.initializeApp(getBaseContext());
        FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();

        ListView listView = findViewById(R.id.list_currCourse);

        firebaseDAOImpl.getData("Students", null, new FirebaseDataCallback<ArrayList<HashMap<String, Object>>>() {
            @Override
            public void onDataReceived(ArrayList<HashMap<String, Object>> students) {
//                                    Log.d("asdfasdfaaaaa",students.get(0).getClass().toString());

                ArrayList<String> currentCourse = new ArrayList<>();

                for (HashMap<String, Object> hashMap1 : students
                ) {

                    Student student = new Student((String) hashMap1.get("userName"),(String)hashMap1.get("passWord"),(ArrayList<String>) hashMap1.get("courses"));
                    for (String course: student.getCourses()) {
                        if (student.getPassWord().equals(logedStudent_id)) {
                            currentCourse = student.getCourses();
                            break;
                        }

                    }
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,currentCourse);
                listView.setAdapter(arrayAdapter);

                final ArrayList<String> finalCurrentCourse = currentCourse;
                listView.setOnItemClickListener((adapterView, view, i, l) -> {
                    Intent intent = new Intent(MyCourseActivity.this, CourseMainpageActivity.class);
                    intent.putExtra("is_teacher",false);
                    intent.putExtra("courseName", finalCurrentCourse.get(i));
                    intent.putExtra("student_id", logedStudent_id);
                    intent.putExtra("uid",uid);
                    Log.d("currentID",uid);
                    Log.d("1", finalCurrentCourse.get(i));
                    MyCourseActivity.this.startActivity(intent);
                });

            }

            @Override
            public void onError(DatabaseError error) {
                // 在这里处理错误
            }
        });




//        ListView listView = findViewById(R.id.list_currCourse);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseName);
//        listView.setAdapter(arrayAdapter);
//        listView.setOnItemClickListener((adapterView, view, i, l) -> {
//            Intent intent = new Intent(MyCourseActivity.this, CourseMainpageActivity.class);
//            intent.putExtra("is_teacher",false);
//            intent.putExtra("courseName",courseName.get(i));
//                Log.d("1",courseName.get(i));
//            MyCourseActivity.this.startActivity(intent);
//        });
    }
}