package com.example.echo361.LayoutActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.echo361.Database.FirebaseDAOImpl;
import com.example.echo361.ForumPost;
import com.example.echo361.R;
import com.example.echo361.util.ToastUtil;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference studentsReference;
    private DatabaseReference teachersReference;
    private DatabaseReference adminReference;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(getBaseContext());

        FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();
        firebaseDAOImpl.initialCoursesData(getApplicationContext());

        /*
       firebaseDAOImpl.getData("Admin", null, new FirebaseDataCallback<Admin>() {
            @Override
            public void onDataReceived(Admin admin) {
                // 在这里处理Admin
            }

            @Override
            public void onError(DatabaseError error) {
                // 在这里处理错误
            }
        });
        */
        //学生
        /*
       firebaseDAOImpl.getData("Students", null, new FirebaseDataCallback<ArrayList<Student>>() {
            @Override
            public void onDataReceived(ArrayList<Student> students) {
                // 在这里处理学生
            }

            @Override
            public void onError(DatabaseError error) {
                // 在这里处理错误
            }
        });
        */
        //教师
        /*
        firebaseDAOImpl.getData("Teachers", null, new FirebaseDataCallback<ArrayList<Teacher>>() {
            @Override
            public void onDataReceived(ArrayList<Teacher> teachers) {
                //在这里处理老师
            }

            @Override
            public void onError(DatabaseError error) {
                // 在这里处理错误
            }
        });
        */
        /*

        //各个树
        firebaseDAOImpl.getData("XXXXTree", null, new FirebaseDataCallback<String>() {

            @Override
            public void onDataReceived(String data) {
                //在这里处理树 比如可以对树进行修改 再储存到firebase 例子：
//                Gson gson = new Gson();
//                CourseAVLtree courseAVLtree = gson.fromJson(data,CourseAVLtree.class);
//                courseAVLtree.delete(1);
//                FirebaseDAOImpl firebaseDAO = FirebaseDAOImpl.getInstance();
//                firebaseDAO.storeData("XXXXTree",null,gson.toJson(courseAVLtree));

            }

            @Override
            public void onError(DatabaseError error) {
                // 在这里处理错误
            }
        });
        */


//        Admin admin = new Admin("Ad Admin","u0000000",null);
//        ArrayList<String> courseA = new ArrayList<>();
//        courseA.add("COMP0001");
//        Student student = new Student("comp2100@anu.au","comp2100",courseA,null);
//        Teacher teacher = new Teacher("comp6442@anu.au","comp6442",courseA,null);
//        firebaseDAOImpl.storeData("Admin",admin);
//        firebaseDAOImpl.storeData("Students","2000",student);
//        firebaseDAOImpl.storeData("Teachers","500",teacher);
//
//        try {
//            firebaseDAOImpl.initialStudentAndTeacherData(getApplicationContext());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        firebaseDAOImpl.initialCoursesData(getApplicationContext());
//        try {
//            firebaseDAOImpl.initialForum(getApplicationContext());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        ForumPost post = new ForumPost("","","",null,true);
//        firebaseDAOImpl.updateForumPost("BIOL0024","0",post);



        Button mBtnLogin = findViewById(R.id.btn_login);
        EditText editText1 = findViewById(R.id.ed_user);
        EditText editText2 = findViewById(R.id.ed_password);

        studentsReference = FirebaseDatabase.getInstance().getReference("Students");


        teachersReference = FirebaseDatabase.getInstance().getReference("Teachers");
        adminReference = FirebaseDatabase.getInstance().getReference("Admin");

        mBtnLogin.setOnClickListener(view -> {
            String inputUsername = editText1.getText().toString();
            String inputPassword = editText2.getText().toString();

            if (!inputUsername.isEmpty() && !inputPassword.isEmpty()) {
                // search student node
                studentsReference.orderByChild("userName").equalTo(inputUsername).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean userFound = false;
//                        Log.d(TAG, "Data snapshot: " + dataSnapshot.toString());
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                            Log.d(TAG, "Checking node");
                            String storedPassword = userSnapshot.child("passWord").getValue(String.class);
                            if (storedPassword != null && storedPassword.equals(inputPassword)) {
                                userFound = true;
                                // login successfully, go to student page
                                String studentName = userSnapshot.child("userName").getValue(String.class);
                                // 获取课程列表
                                GenericTypeIndicator<List<String>> typeIndicator = new GenericTypeIndicator<List<String>>() {};
                                List<String> coursesList = userSnapshot.child("courses").getValue(typeIndicator);
//                                Log.d(TAG, coursesList.get(0));
                                Intent intent = new Intent(MainActivity.this, StudentMainpageActivity.class);
                                intent.putExtra("student_name", studentName);
                                intent.putStringArrayListExtra("courses_list", new ArrayList<>(Objects.requireNonNull(coursesList)));
                                MainActivity.this.startActivity(intent);
                                break;
                            }
                        }

                        if (!userFound) {
                            // 查询教师子节点
                            teachersReference.orderByChild("userName").equalTo(inputUsername).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    boolean userFound = false;
//                                    Log.d(TAG, "Data snapshot: " + dataSnapshot.toString());
                                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                                        Log.d(TAG, "Checking node");
                                        String storedPassword = userSnapshot.child("passWord").getValue(String.class);
                                        if (storedPassword != null && storedPassword.equals(inputPassword)) {
                                            userFound = true;
                                            // 登录成功，教师用户
                                            GenericTypeIndicator<List<String>> typeIndicator = new GenericTypeIndicator<List<String>>() {};
                                            List<String> coursesList = userSnapshot.child("courses").getValue(typeIndicator);
                                            String course = (coursesList != null) ? coursesList.get(0) : null;
                                            Log.d(TAG,"get course"+course);
                                            String teacherName = userSnapshot.child("userName").getValue(String.class);
                                            Intent intent = new Intent(MainActivity.this, CourseMainpageActivity.class);
                                            intent.putExtra("teacher_name", teacherName);
                                            intent.putExtra("courseName",course);
                                            intent.putExtra("is_teacher",true);
                                            MainActivity.this.startActivity(intent);
                                            break;
                                        }
                                    }

                                    if (!userFound) { // 查询管理员子节点
                                        adminReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                boolean userFound = false;
                                                String storedUsername = dataSnapshot.child("userName").getValue(String.class);
                                                String storedPassword = dataSnapshot.child("passWord").getValue(String.class);
                                                if (storedPassword != null && storedUsername != null
                                                        && storedUsername.equals(inputUsername) && storedPassword.equals(inputPassword)){
                                                    userFound = true;
                                                    // 登录成功，管理员用户
                                                    Intent intent = new Intent(getApplicationContext(), AdminDeletionActivity.class);
                                                    startActivity(intent);
                                                }

                                                if (!userFound) {
                                                    ToastUtil.showMsg(MainActivity.this, "Login failed: No user found with username " + inputUsername + " and password " + inputPassword);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
//                                    Log.w(TAG, "loadTeacher:onCancelled", databaseError.toException());
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        Log.w(TAG, "loadStudent:onCancelled", databaseError.toException());
                    }
                });
            } else {
                ToastUtil.showMsg(MainActivity.this, "Username and password cannot be empty");
            }
        });
    }
}
