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
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class EnrollActivity extends AppCompatActivity {

    private DatabaseReference courseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        FirebaseApp.initializeApp(getBaseContext());

        Intent intent1 = getIntent();
        String logedStudent_id = intent1.getStringExtra("student_id");
        System.out.println("logedStudent_id" + logedStudent_id);

        CheckBox underG_cb = (CheckBox) findViewById(R.id.check_underGra);
        CheckBox postG_cb = (CheckBox) findViewById(R.id.check_postGra);
        CheckBox onC_cb = (CheckBox) findViewById(R.id.check_onCampus);
        CheckBox online_cb = (CheckBox) findViewById(R.id.check_online);



        ArrayList<String> courses = new ArrayList<>();

        ListView listView = (ListView) findViewById(R.id.list_courseList);
        TextView textView = (TextView) findViewById(R.id.tx_enrolledCourse);



        EditText editText = findViewById(R.id.ed_searchCourse);
        FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();


//        ArrayAdapter coursesListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, courses);
//        coursesList.setAdapter(coursesListAdapter);


        Button buttonSearch = (Button) findViewById(R.id.btn_searchCourse);
//        button.setOnClickListener(myListener2);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = String.valueOf(editText.getText());
//                String courseCode = String.valueOf(editText.getText());


                if (!(input.isEmpty())) {

                    // get course code and college code

                    String[] courseinfo = inputToCourse(input);
                    String collegeCode = courseinfo[0];
                    String courseCode = courseinfo[1];

                    ArrayList<String> allCollegeCode = new ArrayList<>();

                    if (!(collegeCode.equals(""))){
                        allCollegeCode = getCollege(String.valueOf(collegeCode));
                    }else{
                        for (Course.CODE i : Course.CODE.values()) {
                            allCollegeCode.add(String.valueOf(i));
                        }
                    }

                    ArrayList<String> list = new ArrayList<>();

                    for (String i : allCollegeCode){
                        firebaseDAOImpl.getData(i+"Tree", null, new FirebaseDataCallback<String>() {

                            @Override
                            public void onDataReceived(String data) {
                                Gson gson = new Gson();
                                CourseAVLtree courseAVLtree = gson.fromJson(data,CourseAVLtree.class);
                                ArrayList<Course> courselist;
                                courselist = courseListFilted(courseAVLtree, underG_cb.isChecked(), postG_cb.isChecked(),
                                        onC_cb.isChecked(), online_cb.isChecked(), courseCode);

//                                courselist = courseAVLtree.inOrderBSTqualify(courselist, Course.CAREER.Undergraduate,null,null,null,courseCode);
                                for (Course c :courselist) {
                                    list.add(c.getTitle() +"-"+ c.getDelivery()+ "-"+c.getCareer());
                                }

                                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,list);
                                listView.setAdapter(arrayAdapter);

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        textView.setText(list.get(position));
                                    }
                                });




                            }
                            @Override
                            public void onError(DatabaseError error) {
                                // 在这里处理错误
                            }
                        });

                    }

                }else{
                    Context context = getApplicationContext();
                    CharSequence text = "Input can not be empty.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }


            }
        });


        Button buttonEnroll = (Button) findViewById(R.id.btn_enrollCourse);

//        String selectCourse = (String) textView.getText();

        buttonEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectCourse = String.valueOf(textView.getText()).substring(0,8);

                if (!(selectCourse.isEmpty())){
//
                    int courseID = Integer.parseInt(selectCourse.substring(4));
                    firebaseDAOImpl.getData(selectCourse.substring(0,4)+"Tree", null, new FirebaseDataCallback<String>() {

                        @Override
                        public void onDataReceived(String data) {
                            //在这里处理树 比如可以对树进行修改 再储存到firebase 例子：
                            Gson gson = new Gson();
                            CourseAVLtree courseAVLtree = gson.fromJson(data,CourseAVLtree.class);
                            ArrayList<Course> courselist = new ArrayList<>();
                            courselist = courseAVLtree.inOrderBSTqualify(courselist,null,null,null,null, String.valueOf(courseID));
                            Course newCourse = courselist.get(0);

                            ArrayList newStudentId = newCourse.getStudents();
                            newStudentId.add(logedStudent_id);

                            FirebaseDAOImpl firebaseDAO = FirebaseDAOImpl.getInstance();

                            firebaseDAO.storeData(selectCourse.substring(0,4)+"Tree",null,gson.toJson(courseAVLtree));

                            Context context = getApplicationContext();
                            CharSequence text = "You enrolled in this course.";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                        }

                        @Override
                        public void onError(DatabaseError error) {
                            // 在这里处理错误
                        }
                    });
                    firebaseDAOImpl.getData("Students", null, new FirebaseDataCallback<ArrayList<HashMap<String, Object>>>() {
                        @Override
                        public void onDataReceived(ArrayList<HashMap<String, Object>> students) {

                            ArrayList<Student> storeStudents = new ArrayList<>();

                            for (HashMap<String, Object> hashMap1 : students) {

                                Student student = new Student((String) hashMap1.get("userName"),(String)hashMap1.get("passWord"),(ArrayList<String>) hashMap1.get("courses"));
                                if (student.getPassWord().equals(logedStudent_id)){
                                    student.getCourses().add(selectCourse);

                                    Intent intent = new Intent(EnrollActivity.this, CourseMainpageActivity.class);
                                    intent.putStringArrayListExtra("courses_list_drop", student.getCourses());
                                }
                                storeStudents.add(student);
                            }
                            FirebaseDAOImpl firebaseDAO = FirebaseDAOImpl.getInstance();
                            firebaseDAO.storeData("Students",null,storeStudents);



                            // 在这里处理学生
                        }

                        @Override
                        public void onError(DatabaseError error) {
                            // 在这里处理错误
                        }
                    });
//
//
                }else{
                    Context context = getApplicationContext();
                    CharSequence text = "You must select a course.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });


    }

    public boolean isWord(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

}