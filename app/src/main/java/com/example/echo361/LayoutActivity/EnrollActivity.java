package com.example.echo361.LayoutActivity;

import static com.example.echo361.Search.Search.courseListFilted;
import static com.example.echo361.Search.Search.getCollege;
import static com.example.echo361.Search.Search.getCollegeCode;
import static com.example.echo361.Search.Search.getCourseCode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.echo361.Course;
import com.example.echo361.Database.FirebaseDAOImpl;
import com.example.echo361.Database.FirebaseDataCallback;
import com.example.echo361.Factory.Student;
import com.example.echo361.R;
import com.example.echo361.Search.CExp;
import com.example.echo361.Search.CParser;
import com.example.echo361.Search.CTokenizer;
import com.example.echo361.Search.CourseAVLtree;
import com.example.echo361.Search.CourseTokenizer;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;

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

        ListView listView = (ListView) findViewById(R.id.list_courseList);
        TextView textView = (TextView) findViewById(R.id.tx_deletedCourse2);



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
                    String searchInput = editText.getText().toString();
                    CTokenizer tok = new CourseTokenizer(searchInput);
                    CExp parsedExp = CParser.parseExp(tok);
                    String inputPersed = parsedExp.show();

                    String courseCode = getCourseCode(inputPersed);
                    String collegeCode = getCollegeCode(inputPersed);

                    ArrayList<String> allCollegeCode = getCollege(collegeCode);
                    ArrayList<String> list = new ArrayList<>();


                    for (String i : allCollegeCode){
                        firebaseDAOImpl.getData(i+"Tree", null, new FirebaseDataCallback<String>() {

                            @Override
                            public void onDataReceived(String data) {
                                //在这里处理树 比如可以对树进行修改 再储存到firebase 例子：
//                                if ((underG_cb.isChecked() && postG_cb.isChecked()) || (onC_cb.isChecked() && online_cb.isChecked())){
//                                    Context context = getApplicationContext();
//                                    CharSequence text = "They can't choose at same time";
//                                    int duration = Toast.LENGTH_SHORT;
//                                    Toast toast = Toast.makeText(context, text, duration);
//                                    toast.show();
//                                }
                                Gson gson = new Gson();
                                CourseAVLtree courseAVLtree = gson.fromJson(data,CourseAVLtree.class);
                                ArrayList<Course> courselist = new ArrayList<>();
                                courselist = courseListFilted(courseAVLtree, underG_cb.isChecked(), postG_cb.isChecked(),
                                        onC_cb.isChecked(), online_cb.isChecked(), courseCode);

//                                courselist = courseAVLtree.inOrderBSTqualify(courselist, Course.CAREER.Undergraduate,null,null,null,courseCode);
                                for (Course c :courselist) {
                                    list.add(c.getTitle());
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

        buttonEnroll.setOnClickListener(new View.OnClickListener() {

//            String enrollCourse = textView.getText().toString();

            @Override
            public void onClick(View v) {
                Intent intent0 = getIntent();
                String  logedStudent_id = intent0.getStringExtra("student_id");


                String courseCode = String.valueOf(textView.getText());

                int courseID = Integer.parseInt(courseCode.substring(4));
                firebaseDAOImpl.getData(courseCode.substring(0,4)+"Tree", null, new FirebaseDataCallback<String>() {

                    @Override
                    public void onDataReceived(String data) {
                        //在这里处理树 比如可以对树进行修改 再储存到firebase 例子：
                        Gson gson = new Gson();
                        CourseAVLtree courseAVLtree = gson.fromJson(data,CourseAVLtree.class);
//                        courseAVLtree = courseAVLtree.delete(courseID);
                        ArrayList<Course> courselist = new ArrayList<>();
                        courselist = courseAVLtree.inOrderBSTqualify(courselist,null,null,null,null, String.valueOf(courseID));
                        Course newCourse = courselist.get(0);
                        ArrayList newStudentId = newCourse.getStudents();
                        newStudentId.add(logedStudent_id);
                        Log.d("student ID","course ID: "+ courseID );
                        Log.d("student ID","course: "+ newCourse );
                        Log.d("student ID","course_id: "+ newCourse.getCourseID() );
                        Log.d("student ID","course lisit: "+ newCourse );
                        newCourse.setStudents(newStudentId);
                        courseAVLtree = courseAVLtree.insert(newCourse.getCourseID(), newCourse);
                        FirebaseDAOImpl firebaseDAO = FirebaseDAOImpl.getInstance();
                        firebaseDAO.storeData(courseCode.substring(0,4)+"Tree",null,gson.toJson(courseAVLtree));
                    }

                    @Override
                    public void onError(DatabaseError error) {
                        // 在这里处理错误
                    }
                });
                firebaseDAOImpl.getData("Students", null, new FirebaseDataCallback<ArrayList<HashMap<String, Object>>>() {
                    @Override
                    public void onDataReceived(ArrayList<HashMap<String, Object>> students) {
//                                    Log.d("asdfasdfaaaaa",students.get(0).getClass().toString());

                        ArrayList<Student> storeStudents = new ArrayList<>();

                        for (HashMap<String, Object> hashMap1 : students
                        ) {

                            Student student = new Student((String) hashMap1.get("userName"),(String)hashMap1.get("passWord"),(ArrayList<String>) hashMap1.get("courses"),null);
                            boolean has = false;
                            Log.d("bbbcccc","courses: " + student.toString());
                            for (String course: student.getCourses()) {
                                if (course.equals(courseCode)){
                                    Log.d("bbbcccc","equal");
                                    has = true;
                                }
                            }
                            if (has){
                                student.getCourses().add(courseCode);
                            }
                            Log.d("bbbcccc",student.getCourses().toString());
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



//                int courseID = Integer.parseInt(enrollCourse.substring(4));
//                firebaseDAOImpl.getData(enrollCourse.substring(0,4)+"Tree", null, new FirebaseDataCallback<String>() {
//
//                    @Override
//                    public void onDataReceived(String data) {
//                        //在这里处理树 比如可以对树进行修改 再储存到firebase 例子：
//                        Gson gson = new Gson();
//                        CourseAVLtree courseAVLtree = gson.fromJson(data,CourseAVLtree.class);
//                        ArrayList<Course> courselist = new ArrayList<>();
//                        courselist = courseAVLtree.inOrderBSTqualify(courselist,null,null,null,null, String.valueOf(courseID));
//                        Course newCourse = courselist.get(0);
//                        courseAVLtree = courseAVLtree.insert(courseID, newCourse);
//                        FirebaseDAOImpl firebaseDAO = FirebaseDAOImpl.getInstance();
//                        firebaseDAO.storeData(enrollCourse.substring(0,4)+"Tree",null,gson.toJson(courseAVLtree));
//                    }
//
//                    @Override
//                    public void onError(DatabaseError error) {
//                        // 在这里处理错误
//                    }
//                });
            }
        });

    }

    public boolean isWord(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

}