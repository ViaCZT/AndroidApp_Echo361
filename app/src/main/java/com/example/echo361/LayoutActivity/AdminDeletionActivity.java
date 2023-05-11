package com.example.echo361.LayoutActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

                            int courseID = Integer.parseInt(courseCode.substring(4));
                            firebaseDAOImpl.getData(courseCode.substring(0,4)+"Tree", null, new FirebaseDataCallback<String>() {

                                @Override
                                public void onDataReceived(String data) {
                                    //在这里处理树 比如可以对树进行修改 再储存到firebase 例子：
                            Gson gson = new Gson();
                            CourseAVLtree courseAVLtree = gson.fromJson(data,CourseAVLtree.class);
                            courseAVLtree = courseAVLtree.delete(courseID);
                            FirebaseDAOImpl firebaseDAO = FirebaseDAOImpl.getInstance();
                            firebaseDAO.storeData(courseCode.substring(0,4)+"Tree",null,gson.toJson(courseAVLtree));
                                }

                                @Override
                                public void onError(DatabaseError error) {
                                    // 在这里处理错误
                                }
                            });
                            firebaseDAOImpl.getData("Students", null, new FirebaseDataCallback<ArrayList<Student>>() {
                                @Override
                                public void onDataReceived(ArrayList<Student> students) {
                                    for (Student student : students
                                            ) {
                                        for (String course: student.getCourses()) {
                                            Log.d("asdfasdfa","courses: " + course);
                                            if (course.equals(courseCode)){
                                                student.getCourses().remove(course);
                                                Log.d("asdfasdfa","equal");
                                                break;
                                            }
                                        }
                                    }
                                    FirebaseDAOImpl firebaseDAO = FirebaseDAOImpl.getInstance();
                                    firebaseDAO.storeData("Students",null,students);
                                    // 在这里处理学生
                                }

                                @Override
                                public void onError(DatabaseError error) {
                                    // 在这里处理错误
                                }
                            });

                            firebaseDAOImpl.getData("Teachers", null, new FirebaseDataCallback<ArrayList<Teacher>>() {
                                @Override
                                public void onDataReceived(ArrayList<Teacher> teachers) {
                                    for (Teacher teacher : teachers){
                                        for (String course: teacher.getCourses()) {
                                            if (course.equals(courseCode)){
                                                teacher.getCourses().remove(course);
                                                break;
                                            }
                                        }
                                    }
                                    FirebaseDAOImpl firebaseDAO = FirebaseDAOImpl.getInstance();
                                    firebaseDAO.storeData("Teachers",null,teachers);

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
                String courseCode = String.valueOf(editText.getText());
                firebaseDAOImpl.getData(courseCode.substring(0,4)+"Tree", null, new FirebaseDataCallback<String>() {

                    @Override
                    public void onDataReceived(String data) {
                        //在这里处理树 比如可以对树进行修改 再储存到firebase 例子：
                        Gson gson = new Gson();
                        CourseAVLtree courseAVLtree = gson.fromJson(data,CourseAVLtree.class);
                        ArrayList<Course> courselist = new ArrayList<>();
                        courselist = courseAVLtree.inOrderBSTqualify(courselist,null,null,null,null,courseCode.substring(4));
                        ArrayList<String> list = new ArrayList<>();
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
        });



    }
}