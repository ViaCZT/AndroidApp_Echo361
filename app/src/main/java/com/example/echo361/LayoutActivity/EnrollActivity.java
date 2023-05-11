package com.example.echo361.LayoutActivity;

import static com.example.echo361.LayoutActivity.Search.getCollege;
import static com.example.echo361.LayoutActivity.Search.getCollegeCode;
import static com.example.echo361.LayoutActivity.Search.getCourseCode;
import static com.example.echo361.LayoutActivity.Search.isInCollege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.echo361.Course;
import com.example.echo361.Database.FirebaseDAOImpl;
import com.example.echo361.Database.FirebaseDataCallback;
import com.example.echo361.R;
import com.example.echo361.Search.CExp;
import com.example.echo361.Search.CParser;
import com.example.echo361.Search.CTokenizer;
import com.example.echo361.Search.CourseAVLtree;
import com.example.echo361.Search.CourseTokenizer;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.echo361.Database.FirebaseDAOImpl;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class EnrollActivity extends AppCompatActivity {

    private DatabaseReference courseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        FirebaseApp.initializeApp(getBaseContext());

        CheckBox underG_cb = (CheckBox) findViewById(R.id.check_underGra);
        CheckBox postG_cb = (CheckBox) findViewById(R.id.check_postGra);
        CheckBox onC_cb = (CheckBox) findViewById(R.id.check_onCampus);
        CheckBox online_cb = (CheckBox) findViewById(R.id.check_online);



        ArrayList<String> courses = new ArrayList<String>();

        ListView coursesList = (ListView) findViewById(R.id.list_courseList);
        EditText editText = findViewById(R.id.ed_searchCourse);

        ArrayAdapter coursesListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, courses);
        coursesList.setAdapter(coursesListAdapter);


        Button button = (Button) findViewById(R.id.btn_searchCourse);
        View.OnClickListener myListener2 = v -> {
            if (!(editText.getText().toString().isEmpty())) {
                courses.clear();

                // get course code and college code
                String searchInput = editText.getText().toString();
                CTokenizer tok = new CourseTokenizer(searchInput);
                CExp parsedExp = CParser.parseExp(tok);
                String inputPersed = parsedExp.show();

                String courseCode = getCourseCode(inputPersed);
                String collegeCode = getCollegeCode(inputPersed);

                ArrayList<String> allCourses = new ArrayList<>();

                courses.add(inputPersed);
                courses.add("out search" + courseCode + " " + collegeCode);

                //search

//                FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();

//                FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
////
//                DatabaseReference courseReference1 = firebaseDatabase1.getReference("COMPTree");
////
////
//                courseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
////                               courses.add("get tree" + snapshot.getValue());
//                        String courseData = (String) snapshot.getValue();
////                                Gson gson = new Gson();
////                                Gson gson = new GsonBuilder().setDateFormat("MM dd, yyyy hh:mm:ssa").create();
//                        Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy HH:mm:ss").create();
////                                Log.d(courseData, "onDataChange: ");
//
//                        CourseAVLtree courseAVLtree = gson.fromJson(courseData,CourseAVLtree.class);
//                        courses.add("getanother" + courseAVLtree.course.getTitle());
////                                courses.add("get tree" + snapshot.child("i + \"Tree/""));
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                                // Failed to retrieve the data
//                        System.err.println("Failed to retrieve data, error: " + error.toException());
//                    }
//                });


//
//            courses.add((collegeCode + " " + courseCode).toString());
                editText.setText("");
                coursesListAdapter.notifyDataSetChanged();





            }else{
                Context context = getApplicationContext();
                CharSequence text = "Input can not be empty.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }







        };
        button.setOnClickListener(myListener2);


    }

    public boolean isWord(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

}