package com.example.echo361.LayoutActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.echo361.Course;
import com.example.echo361.Database.FirebaseDAOImpl;
import com.example.echo361.Database.FirebaseDataCallback;
import com.example.echo361.Factory.Student;
import com.example.echo361.Factory.Teacher;
import com.example.echo361.R;
import com.example.echo361.Search.CourseAVLtree;
import com.example.echo361.Search.NExp;
import com.example.echo361.Search.NParser;
import com.example.echo361.Search.NTokenizer;
import com.example.echo361.Search.NameTokenizer;
import com.example.echo361.Search.Search;
import com.example.echo361.util.ToastUtil;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Author Yitao Zhang, u7504766
 * This class is responsible for searching for the teachers and students of the selected class and
 * allows you to click on the search results to jump to their chat activities with the students they have logged in.
 * <p>
 * You must have input characters in EditText to search, enter "teacher" to display the tercher for this course.
 * <p>
 * The characters entered must contain letters or be "comp2100@anu.au", otherwise an alert will be sent.
 * If the input contains letters and other illegal input, all illegal input will be ignored and only students
 * whose names contain the letters entered will be displayed.
 */
public class SearchChatTarget extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_chat_target);

        Search Search = new Search();

        // Initialize Firebase
        FirebaseApp.initializeApp(getBaseContext());

        // Get an instance of FirebaseDAOImpl
        FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();

        Intent intent0 = getIntent();
        // Fetching the courseName from previous activity
        String courseName = intent0.getStringExtra("courseName");
        // Fetching the id of the currently logged-in student from previous activity
        String student_id = intent0.getStringExtra("student_id");
        String uid = intent0.getStringExtra("uid");

        // Find the list view and text view for displaying student name
        EditText editText_name = findViewById(R.id.ed_searchName);
        ListView studentsList = findViewById(R.id.list_name);


        // Find the button for course search and set its click listener
        Button button = findViewById(R.id.btn_searchChat);
        View.OnClickListener myListener2 = v -> {

            // If the input is not empty
            if(!(editText_name.getText().toString().isEmpty())) {
                // Initialize the inputPersed
                String inputPersed;
                // If the input is "comp2100@anu.au"
                if(editText_name.getText().toString().equals("comp2100@anu.au")){
                    inputPersed  = "comp2100@anu.au";
                }else{
                    // Otherwise, Tokenize the input
                    String searchNameInput = editText_name.getText().toString();
                    NTokenizer tok = new NameTokenizer(searchNameInput);
                    NExp parsedExp = NParser.parseExp(tok);
                    inputPersed = parsedExp.show();
                }

                // store parsed input in a new string
                String finalInputPersed = inputPersed;

                // if the finalInputPersed is not empty
                if (!(finalInputPersed == null)){
                    // Get the data from Firebase and process it
                    firebaseDAOImpl.getData(courseName.substring(0, 4) + "Tree", null, new FirebaseDataCallback<String>() {

                    @Override
                    public void onDataReceived(String data) {
                        // Convert the data from JSON to CourseAVLtree
                        Gson gson = new Gson();
                        CourseAVLtree courseAVLtree = gson.fromJson(data, CourseAVLtree.class);

                        // Create a list of qualified courses from the tree
                        ArrayList<Course> courselist = new ArrayList<>();
                        courselist = courseAVLtree.inOrderBSTqualify(courselist, null, null, null, null, courseName.substring(4));

                        // Get the first course from the list, normally there is only one element in this ArrayList
                        Course course = courselist.get(0);

                        // Get the list of students enrolled in the course
                        ArrayList<String> studentsId;
                        studentsId = course.getStudents();

                        ArrayList<String> storeStudents = new ArrayList<>();
                        ArrayList<String> storeStudentsID = new ArrayList<>();

                        // If the finalInputPersed is "teacher"
                        if (finalInputPersed.equals("teacher")){

                            // Create a ArrayList to store teachers' name
                            ArrayList<String> storeTeacher = new ArrayList<>();
                            // Create a ArrayList to store teachers' id
                            ArrayList<String> storeTeacherID = new ArrayList<>();

                            // Fetch data for all teachers from Firebase
                            firebaseDAOImpl.getData("Teachers", null, new FirebaseDataCallback<ArrayList<HashMap<String, Object>>>() {

                                @Override
                                public void onDataReceived(ArrayList<HashMap<String, Object>> teachers) {

                                    // Process each teacher's data
                                    for (HashMap<String, Object> hashMap1 : teachers) {
                                        // Get an teacher instance
                                        Teacher teacher = new Teacher((String) hashMap1.get("userName"), (String) hashMap1.get("passWord"), (ArrayList<String>) hashMap1.get("courses"));
                                        // Check if the teacher teaching the course we selected
                                        if (teacher.getCourses().get(0).equals(courseName) && !(teacher.getPassWord().equals(uid))) {
                                            // Add teacher's name to storeTeacher
                                            storeTeacher.add(teacher.getUserName());
                                            // Add teacher's id to storeTeacher
                                            storeTeacherID.add(teacher.getPassWord());
                                        }
                                    }


                                    // Create an ArrayAdapter and set it to the ListView
                                    ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, storeTeacher);
                                    studentsList.setAdapter(arrayAdapter);
                                    // Set the item click listener for the ListView
                                    studentsList.setOnItemClickListener((adapterView, view, i, l) -> {
                                        // When an item is clicked, go to the corresponding chat activity
                                        Intent intent = new Intent(SearchChatTarget.this,ChatActivity.class);
                                        intent.putExtra("currentUserId",uid);
                                        intent.putExtra("receiverUserId",storeTeacherID.get(i));
                                        SearchChatTarget.this.startActivity(intent);
                                    });

                                }

                                @Override
                                public void onError(DatabaseError error) {
                                    // error
                                }
                            });
                        }else{
                            // Otherwise, for all students enrolled in the course
                            for (String i : studentsId) {

                                // Fetching data from the Students node in the Firebase database.
                                firebaseDAOImpl.getData("Students", null, new FirebaseDataCallback<ArrayList<HashMap<String, Object>>>() {
                                    @Override
                                    public void onDataReceived(ArrayList<HashMap<String, Object>> students) {

                                        // Process each student's data
                                        for (HashMap<String, Object> hashMap1 : students) {
                                            // get an student instance
                                            Student student = new Student((String) hashMap1.get("userName"), (String) hashMap1.get("passWord"), (ArrayList<String>) hashMap1.get("courses"));
                                            //Check if the student is enrolled in this course and is not the student logged in
                                            if (student.getPassWord().equals(i) && !(student.getPassWord().equals(student_id))) {
                                                // Check if the student's name contain the input
                                                if ((Search.getName(student.getUserName())[0].toLowerCase().contains(Search.getName(finalInputPersed)[0].toLowerCase()) &&
                                                        Search.getName(student.getUserName())[1].toLowerCase().contains(Search.getName(finalInputPersed)[1].toLowerCase()) ) ||
                                                            student.getUserName().equals(finalInputPersed)) {
                                                    // Stroe studnets name and id
                                                    storeStudents.add(student.getUserName());
                                                    storeStudentsID.add(student.getPassWord());
                                                    }
                                            }
                                        }

                                        // Create an ArrayAdapter and set it to the studentsList
                                        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, storeStudents);
                                        studentsList.setAdapter(arrayAdapter);


                                        // Set the item click listener for the ListView
                                        studentsList.setOnItemClickListener((adapterView, view, i, l) -> {
                                            // When an item is clicked, go to the corresponding chat activity
                                            Intent intent = new Intent(SearchChatTarget.this,ChatActivity.class);
                                            intent.putExtra("currentUserId",uid);
                                            intent.putExtra("receiverUserId",storeStudentsID.get(i));
                                            SearchChatTarget.this.startActivity(intent);
                                        });
                                    }
                                    @Override
                                    public void onError(DatabaseError error) {
                                        // error
                                    }
                                });
                            }
                        }


                    }

                    @Override
                    public void onError(DatabaseError error) {
                        // error
                    }
                });
                }else{
                    // Otherwise, toast "Your input is invalid."
                    ToastUtil.showMsg(SearchChatTarget.this, "Your input is invalid.");
                }
            }else{
                // If the input is empty, show a Toast message
                ToastUtil.showMsg(SearchChatTarget.this, "Input can not be empty.");
            }
        };
        button.setOnClickListener(myListener2);

    }
}