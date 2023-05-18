package com.example.echo361.LayoutActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
 *  @Author Yitao Zhang, u7504766
 *  This class is responsible for dropping the student from the selected course and removing the course from
 *  the student's infomation. the drop process updates the Firebase database with the course information
 *  and the enrolled student's enrolment information of the students logged in.
 * <p>
 *   When you have successfully droped in this course, you will be prompted with the message "You dropped this course".
 *   Return to the previous menu to see all the classes you have enrolled in MyCourse
 * <p>
 *  This class searches the studnets stored in the Firebase database to get all the courses the student
 *  is enrolled in, selects the course and then clicks "Drop" button to drop the student from the selected
 *  course and remove the course from the student's infomation.
 */
public class DropActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop);

        // Initializing Firebase app with this context.
        FirebaseApp.initializeApp(getBaseContext());
        // Getting an instance of FirebaseDAOImpl
        FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();

        // Fetching the id of the currently logged-in student from previous activity
        Intent intent1 = getIntent();
        String logedStudent_id = intent1.getStringExtra("student_id");

        // Find the list view and text view for displaying courses' information
        ListView listView_dp = findViewById(R.id.list_currCourse_drop);
        TextView textView = findViewById(R.id.tx_dropedCourse);

        // Fetching data from the Students node in the Firebase database.
        firebaseDAOImpl.getData("Students", null, new FirebaseDataCallback<ArrayList<HashMap<String, Object>>>() {
            @Override
            public void onDataReceived(ArrayList<HashMap<String, Object>> students) {

                // Initialize an array list to store all courses this student enrolled
                ArrayList<String> currentCourse = new ArrayList<>();

                // Process each student's data
                for (HashMap<String, Object> hashMap1 : students) {
                    // get an student instance
                    Student student = new Student((String) hashMap1.get("userName"),(String)hashMap1.get("passWord"),(ArrayList<String>) hashMap1.get("courses"));
                    //Check if the student is the one currently logged in
                    if (student.getPassWord().equals(logedStudent_id)) {
                        // store the course the student enrolled in
                        currentCourse = student.getCourses();
                    }
                }

                // Create an ArrayAdapter and set it to the listView_dp
                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,currentCourse);
                listView_dp.setAdapter(arrayAdapter);

                // store all course the student enrolled in to an ArrayList
                ArrayList<String> finalCurrentCourse = currentCourse;

                // Set the item click listener for the ListView
                listView_dp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // When an item is clicked, display it in the TextView
                        textView.setText(finalCurrentCourse.get(position));
                    }
                });

            }

            @Override
            public void onError(DatabaseError error) {
                // error
            }
        });

        // Set click listener for the drop button
        Button buttonDrop = (Button) findViewById(R.id.btn_dropCourse);
        buttonDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Extract the selected course from the TextView
                String selectCourse = String.valueOf(textView.getText());

                // Check if a course has been selected
                if (!(selectCourse.isEmpty()) || selectCourse.equals("COURSECODE")){

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

                            // drop the logged in student in the course
                            newStudentId.remove(logedStudent_id);

                            // Update the course tree in Firebase with the new student enrollment
                            FirebaseDAOImpl firebaseDAO = FirebaseDAOImpl.getInstance();
                            firebaseDAO.storeData(selectCourse.substring(0,4)+"Tree",null,gson.toJson(courseAVLtree));

                            // Display a toast to confirm successful drop
                            Context context = getApplicationContext();
                            CharSequence text = "You dropped this course.";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                        }

                        @Override
                        public void onError(DatabaseError error) {
                            // error
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
                                    // remove the selected course in student infomation
                                    student.getCourses().remove(selectCourse);

                                    // transfer the courseName to CourseMainpageActivity
                                    Intent intent = new Intent(DropActivity.this, CourseMainpageActivity.class);
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