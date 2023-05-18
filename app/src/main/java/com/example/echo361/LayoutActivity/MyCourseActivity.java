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

/**
 * @author Yijun Huang, u7564899
 * @author Yitao Zhang, u7504766
 * @author Zihan Ai, u7528678
 * <p>
 * This class implements the process of retrieving all enrolled courses of the student
 * who logged in from the Firebase database and displaying them in the listView.
 * <p>
 * Click on the course displayed in the listView to be redirected to the CourseMainpageActivity
 * for that course.
 */
public class MyCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);

        // Retrieve data passed from the previous activity via Intent
        Intent intent0 = getIntent();
        String logedStudent_id = intent0.getStringExtra("student_id");
        String uid = intent0.getStringExtra("uid");

        // Initialize Firebase
        FirebaseApp.initializeApp(getBaseContext());
        FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();

        // Get reference to the ListView component in the layout
        ListView listView = findViewById(R.id.list_currCourse);

        // Fetch student data from Firebase database
        firebaseDAOImpl.getData("Students", null, new FirebaseDataCallback<ArrayList<HashMap<String, Object>>>() {
            @Override
            public void onDataReceived(ArrayList<HashMap<String, Object>> students) {
                // Create an ArrayList to hold the current courses
                ArrayList<String> currentCourse = new ArrayList<>();

                // Process the student data
                for (HashMap<String, Object> hashMap1 : students) {
                    // get an student instance
                    Student student = new Student((String) hashMap1.get("userName"),(String)hashMap1.get("passWord"),(ArrayList<String>) hashMap1.get("courses"));
                    for (String course: student.getCourses()) {
                        // Find the current courses for the logged-in student
                        if (student.getPassWord().equals(logedStudent_id)) {
                            currentCourse = student.getCourses();
                            break;
                        }

                    }
                }

                // Create an ArrayAdapter and set it to the ListView
                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,currentCourse);
                listView.setAdapter(arrayAdapter);

                // Set the item click listener for the ListView
                final ArrayList<String> finalCurrentCourse = currentCourse;
                listView.setOnItemClickListener((adapterView, view, i, l) -> {
                    // When an item is clicked, go to the CourseMainPage
                    Intent intent = new Intent(MyCourseActivity.this, CourseMainpageActivity.class);
                    intent.putExtra("is_teacher",false);
                    intent.putExtra("courseName", finalCurrentCourse.get(i));
                    intent.putExtra("student_id", logedStudent_id);
                    intent.putExtra("uid",uid);
                    MyCourseActivity.this.startActivity(intent);
                });

            }

            @Override
            public void onError(DatabaseError error) {
                // error
            }
        });
    }
}