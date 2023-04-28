package com.example.echo361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.echo361.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.btn_login);
        EditText editText1 = findViewById(R.id.ed_user);
        EditText editText2 = findViewById(R.id.ed_password);


        button.setOnClickListener(view -> {
            String username = editText1.getText().toString();
            String password = editText2.getText().toString();
            System.out.println(username);
            System.out.println(password);
            if(username.equals("comp2100@anu.au")&&password.equals("comp2100")){
                Student student = new Student("Zihan","comp2100",null,null);
                Intent intent = new Intent(MainActivity.this,StudentMainpageActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}