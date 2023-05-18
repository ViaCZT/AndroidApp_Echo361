package com.example.echo361.LayoutActivity;

import static com.example.echo361.Search.Search.courseListFilted;
import static com.example.echo361.Search.Search.getCollege;
import static com.example.echo361.Search.Search.inputToCourse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.echo361.Course;
import com.example.echo361.Database.FirebaseDAOImpl;
import com.example.echo361.Database.FirebaseDataCallback;
import com.example.echo361.Factory.Student;
import com.example.echo361.R;
import com.example.echo361.Search.CourseAVLtree;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Author Yitao Zhang, u7504766
 * This class is responsible for enrolling a student in a class, while allowing the student's
 * information to be stored in the class he/she is enrolled in. The enrolment process updates the
 * Firebase database with the course information and the enrolment information of the students logged in.
 * <p>
 * When you have successfully enrolled in this course, you will be prompted with the message "You enrolled in this course".
 * Return to the previous menu to see all the classes you have enrolled in MyCourse
 * <p>
 * This class allows students to search for courses stored in the Firebase database by collegeCode and
 * courseCode and can filter the Career and Delivery of the course, e.g. undergraduate/postgraduate, on-campus/online.
 * <p>
 * The search function enables a partial search and will extract the first four letters as collegeCode
 * and the first four digits as courseCode in order to search. When no four letters or digits are entered,
 * all course information containing that input character will be displayed.
 * <p>
 * There must be input characters in the EditText for the search to work, if the input does not
 * contain letters or numbers then will promote a reminder.
 */
public class EnrollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        // Initialize Firebase in this context
        FirebaseApp.initializeApp(getBaseContext());

        // get student id from previous intent
        Intent intent1 = getIntent();
        String logedStudent_id = intent1.getStringExtra("student_id");

        // Find checkboxes for different categories of courses
        CheckBox underG_cb = (CheckBox) findViewById(R.id.check_underGra);
        CheckBox postG_cb = (CheckBox) findViewById(R.id.check_postGra);
        CheckBox onC_cb = (CheckBox) findViewById(R.id.check_onCampus);
        CheckBox online_cb = (CheckBox) findViewById(R.id.check_online);


        // Find the list view and text view for displaying courses' information
        ListView listView = (ListView) findViewById(R.id.list_courseList);
        TextView textView = (TextView) findViewById(R.id.tx_enrolledCourse);

        // Find the edit text for course search input
        EditText editText = findViewById(R.id.ed_searchCourse);

        // Get an instance of FirebaseDAOImpl
        FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();

        // Find the button for course search and set its click listener
        Button buttonSearch = (Button) findViewById(R.id.btn_searchCourse);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input from the EditText
                String input = String.valueOf(editText.getText());

                // If the input is not empty
                if (!(input.isEmpty())) {

                    // Get the course code and college code from the input
                    String[] courseinfo = inputToCourse(input);
                    String collegeCode = courseinfo[0];
                    String courseCode = courseinfo[1];

                    // Initialize an array list to store all college codes
                    ArrayList<String> allCollegeCode = new ArrayList<>();

                    // If the college code is not empty, get the corresponding college
                    if (!(collegeCode.equals(""))){
                        allCollegeCode = getCollege(String.valueOf(collegeCode));
                    }else{
                        // Otherwise, add all possible colleges
                        for (Course.CODE i : Course.CODE.values()) {
                            allCollegeCode.add(String.valueOf(i));
                        }
                    }

                    // If the input is not only non-letter/non-number
                    if (!(collegeCode.equals("") && courseCode.equals(""))){
                        // Initialize an array list to store the list of courses
                        ArrayList<String> list = new ArrayList<>();

                        // For each college code in the list
                        for (String i : allCollegeCode){
                            // Get the data from Firebase and process it
                            firebaseDAOImpl.getData(i+"Tree", null, new FirebaseDataCallback<String>() {

                                @Override
                                public void onDataReceived(String data) {
                                    // Convert the data from JSON to CourseAVLtree
                                    Gson gson = new Gson();
                                    CourseAVLtree courseAVLtree = gson.fromJson(data,CourseAVLtree.class);

                                    // Get the list of courses filtered by four check box and course code
                                    ArrayList<Course> courselist;
                                    courselist = courseListFilted(courseAVLtree, underG_cb.isChecked(), postG_cb.isChecked(),
                                            onC_cb.isChecked(), online_cb.isChecked(), courseCode);

                                    // For each course in the courselist
                                    for (Course c :courselist) {
                                        // Add the course to the list will display on listView
                                        list.add(c.getTitle() +"-"+ c.getDelivery()+ "-"+c.getCareer());
                                    }

                                    // Create an ArrayAdapter and set it to the ListView
                                    ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,list);
                                    listView.setAdapter(arrayAdapter);

                                    // Set the item click listener for the ListView
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // When an item is clicked, display it in the TextView
                                            textView.setText(list.get(position));
                                        }
                                    });




                                }
                                @Override
                                public void onError(DatabaseError error) {
                                }
                            });

                        }
                    }else{
                        // If the input is invalid, show a Toast message
                        Context context = getApplicationContext();
                        CharSequence text = "Your input is invalid.";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }

                }else{
                    // If the input is empty, show a Toast message
                    Context context = getApplicationContext();
                    CharSequence text = "Input can not be empty.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }


            }
        });

        // Find the button for course enrollment and set its click listener
        Button buttonEnroll = (Button) findViewById(R.id.btn_enrollCourse);
        buttonEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Extract the selected course from the TextView
                String selectCourse = String.valueOf(textView.getText()).substring(0,8);

                // Check if a course has been selected
                if (!(selectCourse.isEmpty())){

                    // Extract the course code from the selectedCourse
                    int courseID = Integer.parseInt(selectCourse.substring(4));

                    // Fetch data from Firebase for the course's tree
                    firebaseDAOImpl.getData(selectCourse.substring(0,4)+"Tree", null, new FirebaseDataCallback<String>() {

                        @Override
                        public void onDataReceived(String data) {
                            // Convert the fetched data from JSON to a CourseAVLtree
                            Gson gson = new Gson();
                            CourseAVLtree courseAVLtree = gson.fromJson(data,CourseAVLtree.class);

                            // Create a list of qualified courses from the tree
                            ArrayList<Course> courselist = new ArrayList<>();
                            courselist = courseAVLtree.inOrderBSTqualify(courselist,null,null,null,null, String.valueOf(courseID));

                            // Get the first course from the list, normally there is only one element in this ArrayList
                            Course newCourse = courselist.get(0);

                            // Get the list of students enrolled in the course
                            ArrayList newStudentId = newCourse.getStudents();

                            // Enroll the logged in student in the course
                            newStudentId.add(logedStudent_id);

                            // Update the course tree in Firebase with the new student enrollment
                            FirebaseDAOImpl firebaseDAO = FirebaseDAOImpl.getInstance();
                            firebaseDAO.storeData(selectCourse.substring(0,4)+"Tree",null,gson.toJson(courseAVLtree));

                            // Display a toast to confirm successful enrollment
                            Context context = getApplicationContext();
                            CharSequence text = "You enrolled in this course.";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                        }

                        @Override
                        public void onError(DatabaseError error) {
                        }
                    });

                    // Fetch data for all students from Firebase
                    firebaseDAOImpl.getData("Students", null, new FirebaseDataCallback<ArrayList<HashMap<String, Object>>>() {
                        @Override
                        public void onDataReceived(ArrayList<HashMap<String, Object>> students) {

                            // Initialize an ArrayList to store the students which will be stored in firebase
                            ArrayList<Student> storeStudents = new ArrayList<>();

                            // Process each student's data
                            for (HashMap<String, Object> hashMap1 : students) {
                                // get an student instance
                                Student student = new Student((String) hashMap1.get("userName"),(String)hashMap1.get("passWord"),(ArrayList<String>) hashMap1.get("courses"));
                                //Check if the student is the one currently logged in
                                if (student.getPassWord().equals(logedStudent_id)){
                                    // Enroll the student in the selected course
                                    student.getCourses().add(selectCourse);

                                    // transfer the courseName to CourseMainpageActivity
                                    Intent intent = new Intent(EnrollActivity.this, CourseMainpageActivity.class);
                                    intent.putStringArrayListExtra("courses_list_drop", student.getCourses());
                                }
                                // Add the student to the list we initialized above
                                storeStudents.add(student);
                            }
                            // Update the student data in Firebase
                            FirebaseDAOImpl firebaseDAO = FirebaseDAOImpl.getInstance();
                            firebaseDAO.storeData("Students",null,storeStudents);

                        }

                        @Override
                        public void onError(DatabaseError error) {
                            // error
                        }
                    });
//
//
                }else{
                    // If no course has been selected, display a toast to prompt the user to select a course
                    Context context = getApplicationContext();
                    CharSequence text = "You must select a course.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });


    }

}