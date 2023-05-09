package com.example.echo361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.echo361.util.ToastUtil;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference studentsReference;
    private DatabaseReference teachersReference;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(getBaseContext());

        Button mBtnLogin = findViewById(R.id.btn_login);
        EditText editText1 = findViewById(R.id.ed_user);
        EditText editText2 = findViewById(R.id.ed_password);

        studentsReference = FirebaseDatabase.getInstance().getReference("Students");
        teachersReference = FirebaseDatabase.getInstance().getReference("Teachers");

        mBtnLogin.setOnClickListener(view -> {
            String inputUsername = editText1.getText().toString();
            String inputPassword = editText2.getText().toString();

            if (!inputUsername.isEmpty() && !inputPassword.isEmpty()) {
                // 查询学生子节点
                studentsReference.orderByChild("name").equalTo(inputUsername).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean userFound = false;
                        Log.d(TAG, "Data snapshot: " + dataSnapshot.toString());

                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            Log.d(TAG, "Checking node");
                            String storedPassword = userSnapshot.child("uid").getValue(String.class);
                            if (storedPassword != null && storedPassword.equals(inputPassword)) {
                                userFound = true;
                                // 登录成功，学生用户
                                String studentName = userSnapshot.child("name").getValue(String.class);
                                Intent intent = new Intent(MainActivity.this, StudentMainpageActivity.class);
                                intent.putExtra("student_name", studentName);
                                MainActivity.this.startActivity(intent);
                                break;
                            }
                        }

                        if (!userFound) {
                            // 查询教师子节点
                            teachersReference.orderByChild("name").equalTo(inputUsername).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    boolean userFound = false;


                                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                        for (DataSnapshot teacherSnapshot : userSnapshot.getChildren()) {
                                            String storedName = teacherSnapshot.child("name").getValue(String.class);
                                            String storedPassword = teacherSnapshot.child("passWord").getValue(String.class);

                                            if (storedName != null && storedName.equals(inputUsername) && storedPassword != null && storedPassword.equals(inputPassword)) {
                                                userFound = true;
                                                // 登录成功，教师用户
                                                String teacherName = teacherSnapshot.child("name").getValue(String.class);
                                                Intent intent = new Intent(MainActivity.this, CourseMainpageActivity.class);
                                                intent.putExtra("teacher_name", teacherName);
                                                MainActivity.this.startActivity(intent);
                                                break;
                                            }
                                        }

                                        if (userFound) {
                                            break;
                                        }
                                    }

                                    if (!userFound) {
                                        ToastUtil.showMsg(MainActivity.this, "Login failed: No user found with username " + inputUsername + " and password " + inputPassword);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.w(TAG, "loadTeacher:onCancelled", databaseError.toException());
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "loadStudent:onCancelled", databaseError.toException());
                    }
                });
            } else {
                ToastUtil.showMsg(MainActivity.this, "Username and password cannot be empty");
            }
        });
    }
}
