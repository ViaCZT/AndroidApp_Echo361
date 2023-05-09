package com.example.echo361.LayoutActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.echo361.R;
import com.example.echo361.util.ToastUtil;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

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

//        FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();
//        firebaseDAOImpl.getData("Students", "", new TypeToken<ArrayList<Student>>() {}.getType(), new FirebaseDataCallback<ArrayList<Student>>() {
//            @Override
//            public void onDataReceived(ArrayList<Student> students) {
//                Log.d(TAG,"asdadasasdaddasd"+ students.get(0).toString());
//                // 在这里处理学生列表
//            }
//
//            @Override
//            public void onError(DatabaseError error) {
//                // 在这里处理错误
//            }
//        });
//        Admin admin = new Admin("Ad Admin","u0000000",null);
//        ArrayList<String> courseA = new ArrayList<>();
//        courseA.add("COMP0001");
//        Student student = new Student("comp2100@anu.au","comp2100",courseA,null);
//        Teacher teacher = new Teacher("comp6442@anu.au","comp6442",courseA,null);
//        firebaseOperator.storeData("Admin",admin);
//        firebaseDAOImpl.storeData("Students","2000",student);
//        firebaseDAOImpl.storeData("Teachers","500",teacher);

//        try {
//            firebaseOperator.storeStudentAndTeacherData(getApplicationContext());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        firebaseOperator.storeCoursesData(getApplicationContext());

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
                                            String teacherName = userSnapshot.child("userName").getValue(String.class);
                                            Intent intent = new Intent(MainActivity.this, CourseMainpageActivity.class);
                                            intent.putExtra("teacher_name", teacherName);
                                            intent.putExtra("course_name",course);
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