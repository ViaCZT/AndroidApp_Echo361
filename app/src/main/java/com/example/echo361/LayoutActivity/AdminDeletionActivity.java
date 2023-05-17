package com.example.echo361.LayoutActivity;

import static com.example.echo361.Search.Search.courseListFilted;
import static com.example.echo361.Search.Search.getCollege;
import static com.example.echo361.Search.Search.inputToCourse;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.echo361.Course;
import com.example.echo361.Database.FirebaseDAOImpl;
import com.example.echo361.Database.FirebaseDataCallback;
import com.example.echo361.Factory.Student;
import com.example.echo361.Factory.Teacher;
import com.example.echo361.R;
import com.example.echo361.Search.CourseAVLtree;
import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminDeletionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_deletion);

        EditText editText = (EditText) findViewById(R.id.ed_searchCourse);
        TextView textView = (TextView) findViewById(R.id.tx_deletedCourse);
        ListView listView = (ListView) findViewById(R.id.list_courseList);
        Button buttonDelete = (Button) findViewById(R.id.btn_delete);
        Button buttonSearch = (Button) findViewById(R.id.btn_searchCourse);
        FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();



        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                            String courseCode = String.valueOf(textView.getText());

                            int courseID = Integer.parseInt(courseCode.substring(4,8));
                            firebaseDAOImpl.getData(courseCode.substring(0,4)+"Tree", null, new FirebaseDataCallback<String>() {

                                @Override
                                public void onDataReceived(String data) {
                                    //在这里处理树 比如可以对树进行修改 再储存到firebase 例子：
                            Gson gson = new Gson();
                            CourseAVLtree courseAVLtree = gson.fromJson(data,CourseAVLtree.class);
//                            Log.d("dada", courseAVLtree.toString());
                            courseAVLtree = courseAVLtree.delete(courseID);
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

                                        Student student = new Student((String) hashMap1.get("userName"),(String)hashMap1.get("passWord"),(ArrayList<String>) hashMap1.get("courses"));
                                        boolean has = false;
                                        Log.d("bbbcccc","courses: " + student.toString());
                                        for (String course: student.getCourses()) {
                                            if (course.equals(courseCode.substring(0,8))){
//                                                Log.d("bbbcccc","equal");
                                                has = true;
                                            }
                                        }
                                        if (has){
                                            student.getCourses().remove(courseCode.substring(0,8));
                                        }
//                                        Log.d("bbbcccc",student.getCourses().toString());
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

                            firebaseDAOImpl.getData("Teachers", null, new FirebaseDataCallback<ArrayList<HashMap<String, Object>>>() {
                                @Override
                                public void onDataReceived(ArrayList<HashMap<String, Object>> teachers) {
                                    ArrayList<Teacher> storeTeachers = new ArrayList<>();
                                    for (HashMap<String, Object> hashMap1 : teachers
                                    ) {

                                        Teacher teacher = new Teacher((String) hashMap1.get("userName"),(String)hashMap1.get("passWord"),(ArrayList<String>) hashMap1.get("courses"));
                                        boolean has = false;
                                        Teacher deleteTeacher = new Teacher();
//                                        Log.d("bbbcccc","courses: " + teacher.toString());
                                        if (teacher.getCourses()!=null){
                                            for (String course: teacher.getCourses()) {
                                                if (course.equals(courseCode.substring(0,8))){
//                                                Log.d("bbbcccc","equal");
                                                    has = true;
                                                }
                                            }
                                            if (!has){
                                                storeTeachers.add(teacher);
                                            }
                                        }

//                                        Log.d("bbbcccc",teacher.getCourses().toString());

                                    }
                                    FirebaseDAOImpl firebaseDAO = FirebaseDAOImpl.getInstance();
                                    firebaseDAO.storeData("Teachers",null,storeTeachers);

                                    //在这里处理老师
                                }

                                @Override
                                public void onError(DatabaseError error) {
                                    // 在这里处理错误
                                }
                            });

                        }
                    });

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

//                    ArrayList<String> allCollegeCode = getCollege(collegeCode);
                    ArrayList<String> allCollegeCode = new ArrayList<>();
                    ArrayList<String> list = new ArrayList<>();

                    if (!(collegeCode.equals(""))){
                        allCollegeCode = getCollege(String.valueOf(collegeCode));
                    }else{
                        for (Course.CODE i : Course.CODE.values()) {
                            allCollegeCode.add(String.valueOf(i));
                        }
                    }


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
                                courselist = courseListFilted(courseAVLtree, false, false, false, false, courseCode);
                                Log.d("aass",courselist.toString());
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

//        buttonSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String courseCode = String.valueOf(editText.getText());
//                firebaseDAOImpl.getData(courseCode.substring(0,4)+"Tree", null, new FirebaseDataCallback<String>() {
//
//                    @Override
//                    public void onDataReceived(String data) {
//                        //在这里处理树 比如可以对树进行修改 再储存到firebase 例子：
//                        Gson gson = new Gson();
//                        CourseAVLtree courseAVLtree = gson.fromJson(data,CourseAVLtree.class);
//                        ArrayList<Course> courselist = new ArrayList<>();
//                        courselist = courseAVLtree.inOrderBSTqualify(courselist,null,null,null,null,courseCode.substring(4));
//                        ArrayList<String> list = new ArrayList<>();
//                        for (Course c :courselist) {
//                            list.add(c.getTitle());
//                        }
//                        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,list);
//                        listView.setAdapter(arrayAdapter);
//
//                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                textView.setText(list.get(position));
//                            }
//                        });
//
//
//
//                    }
//                    @Override
//                    public void onError(DatabaseError error) {
//                        // 在这里处理错误
//                    }
//                });
//            }
//        });



    }

}