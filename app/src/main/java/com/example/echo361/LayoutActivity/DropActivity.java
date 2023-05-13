package com.example.echo361.LayoutActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContentInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;

public class DropActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop);

        FirebaseApp.initializeApp(getBaseContext());
        FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();


        Intent intent1 = getIntent();
//        ArrayList<String> courseName = intent1.getStringArrayListExtra("courses_list");
        String logedStudent_id = intent1.getStringExtra("student_id");
//        ArrayList<String> courseNameCurr = ;

        ListView listView_dp = findViewById(R.id.list_currCourse_drop);
        TextView textView = findViewById(R.id.tx_dropedCourse);
//        ArrayAdapter<String> arrayAdapter_dp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseName);
//        Log.d("listview", "" + this);
//        listView_dp.setAdapter(arrayAdapter_dp);

        firebaseDAOImpl.getData("Students", null, new FirebaseDataCallback<ArrayList<HashMap<String, Object>>>() {
            @Override
            public void onDataReceived(ArrayList<HashMap<String, Object>> students) {
//                                    Log.d("asdfasdfaaaaa",students.get(0).getClass().toString());

                ArrayList<String> currentCourse = new ArrayList<>();

                for (HashMap<String, Object> hashMap1 : students
                ) {

                    Student student = new Student((String) hashMap1.get("userName"),(String)hashMap1.get("passWord"),(ArrayList<String>) hashMap1.get("courses"),null);
                    for (String course: student.getCourses()) {
                        if (student.getPassWord().equals(logedStudent_id)) {
                            currentCourse = student.getCourses();
                            break;
                        }

                    }
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,currentCourse);
                listView_dp.setAdapter(arrayAdapter);

                ArrayList<String> finalCurrentCourse = currentCourse;
                listView_dp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        textView.setText(finalCurrentCourse.get(position));
                    }
                });

            }

            @Override
            public void onError(DatabaseError error) {
                // 在这里处理错误
            }
        });



        Log.d("select1", String.valueOf(textView.getText()));

        Button buttonDrop = (Button) findViewById(R.id.btn_dropCourse);
        buttonDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectCourse = String.valueOf(textView.getText());
                Log.d("select", selectCourse);


                if (!(selectCourse.isEmpty())){

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
                            Log.d("course","courses"+ courselist );

                            ArrayList newStudentId = newCourse.getStudents();
                            Log.d("student ID","logedStudent_id: "+ logedStudent_id );
                            Log.d("student ID","StudentsId: "+ newStudentId.size() );
                            newStudentId.remove(logedStudent_id);
                            Log.d("student ID","newStudentsId: "+ newStudentId.size() );
                            Log.d("student ID","course ID: "+ courseID );
                            Log.d("student ID","course: "+ newCourse.getStudents().size() );
//                            newCourse.setStudents(newStudentId);
                            Log.d("student ID","newcourse ID: "+ courseAVLtree.inOrderBSTqualify(courselist,null,null,null,null, String.valueOf(courseID)).get(0) );
                            Log.d("student ID","newcourse ID: "+ newCourse.equals(courseAVLtree.inOrderBSTqualify(courselist,null,null,null,null, String.valueOf(courseID)).get(0)) );
                            Log.d("StoreDate", "storedata to : " + selectCourse.substring(0,4)+"Tree");
                            Log.d("StoreDate", "storedata: " + gson.toJson(courseAVLtree));

                            FirebaseDAOImpl firebaseDAO = FirebaseDAOImpl.getInstance();

                            firebaseDAO.storeData(selectCourse.substring(0,4)+"Tree",null,gson.toJson(courseAVLtree));

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

                            for (HashMap<String, Object> hashMap1 : students) {

                                Student student = new Student((String) hashMap1.get("userName"),(String)hashMap1.get("passWord"),(ArrayList<String>) hashMap1.get("courses"),null);
//                                Log.d("bbbcccc","student: " + student.toString());
                                if (student.getPassWord().equals(logedStudent_id)){
                                    Log.d("bbbcccc","getCourses" + student.getCourses().toString());
                                    student.getCourses().remove(selectCourse);
                                    Log.d("bbbcccc"," getCourses" + student.getCourses().toString());

//                                    Intent intent = new Intent(DropActivity.this, CourseMainpageActivity.class);
//                                    intent.putExtra("courses_list", student.getCourses());
                                }
                                storeStudents.add(student);
                            }
                            Log.d("bbbcccc"," getCourses" + storeStudents);
//                            Log.d("bbbcccc"," getCourses" + students.get(0).toString());
                            FirebaseDAOImpl firebaseDAO = FirebaseDAOImpl.getInstance();
                            firebaseDAO.storeData("Students",null,storeStudents);



                            // 在这里处理学生
                        }

                        @Override
                        public void onError(DatabaseError error) {
                            // 在这里处理错误
                        }
                    });


                }else{
                    Context context = getApplicationContext();
                    CharSequence text = "You must select a course.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

//                Intent intent0 = new Intent(DropActivity.this, StudentMainpageActivity.class);
//                DropActivity.this.startActivity(intent0);

            }
        });



    }
}