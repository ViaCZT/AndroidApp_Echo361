package com.example.echo361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.echo361.R;
import com.example.echo361.util.ToastUtil;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(getBaseContext());

        Button mBtnLogin = findViewById(R.id.btn_login);
        EditText editText1 = findViewById(R.id.ed_user);
        EditText editText2 = findViewById(R.id.ed_password);

        mBtnLogin.setOnClickListener(view -> {
            String username = editText1.getText().toString();
            String password = editText2.getText().toString();

            if(username.equals("comp2100@anu.au")&&password.equals("comp2100")){
                Student student = new Student("Zihan","comp2100",null,null);
                String loginOk = "Welcome! "+User.getname();
                ToastUtil.showMsg(MainActivity.this,loginOk);
                Intent intent = new Intent(MainActivity.this,StudentMainpageActivity.class);
                MainActivity.this.startActivity(intent);
            }
            else if (username.equals("comp6442@anu.au")&&password.equals("comp6442")){
                Teacher teacher = new Teacher("Bernardo","comp6442",null,null);
                String loginOk = "Welcome! "+User.getname();
                ToastUtil.showMsg(MainActivity.this,loginOk);
                Intent intent = new Intent(MainActivity.this, CourseMainpageActivity.class);
                intent.putExtra("name","Bernardo");
                MainActivity.this.startActivity(intent);
            }
            else if (username.equals("admin")&&password.equals("admin")){
                Admin admin = new Admin("Yuan Li","admin");
                String loginOk = "Welcome! "+User.getname();
                ToastUtil.showMsg(MainActivity.this,loginOk);
                Intent intent = new Intent(MainActivity.this, AdminDeletionActivity.class);
                MainActivity.this.startActivity(intent);
            }
            else {
                String loginFail = "Please check your username and password";
                ToastUtil.showMsg(MainActivity.this,loginFail);
            }
        });
    }
}