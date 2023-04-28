package com.example.echo361;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchChatTarget extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_chat_target);

        EditText name = findViewById(R.id.editTextTextPersonName);
        String target = name.getText().toString();
        ArrayList<String> arrayList = new ArrayList<>();
        Button search = findViewById(R.id.button);
        search.setOnClickListener(view -> {
            ArrayList<Student> students = (ArrayList<Student>) Student.search(target);
            for(Student s:students){
                arrayList.add(s.getname());
            }
        });

        ListView listView = findViewById(R.id.studentList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(SearchChatTarget.this, ChatActivity.class);
            String text = arrayList.get(i);
            intent.putExtra("targetName",text);
        });


    }
}