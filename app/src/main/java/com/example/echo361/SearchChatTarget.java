package com.example.echo361;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchChatTarget extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_chat_target);

        EditText name = findViewById(R.id.ed_searchName);

        // search

        ArrayList<String> students = new ArrayList<String>();

        ListView studentsList = (ListView) findViewById(R.id.list_name);
        EditText editText_name = findViewById(R.id.ed_searchName);

        ArrayAdapter studentsListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, students);
        studentsList.setAdapter(studentsListAdapter);


        Button button = (Button) findViewById(R.id.btn_searchChat);
        View.OnClickListener myListener2 = v -> {
            // get course code and college code
            String searchNameInput = editText_name.getText().toString();
            CTokenizer tok = new CourseTokenizer(searchNameInput);
            CExp parsedExp = CParser.parseExp(tok);
            String inputPersed = parsedExp.show();

            String firstName = "";
            String lastName = "";
            ArrayList space = indexOfSpace(inputPersed);
            System.out.println(space);
            if (space.size() == 0){
                firstName = inputPersed;
            }else if (space.size() == 1){
                firstName = inputPersed.substring(0, (Integer) space.get(0));
                lastName = inputPersed.substring((Integer) space.get(0));
            }else{
                firstName = inputPersed.substring(0,7);
                lastName = inputPersed.substring((Integer) space.get(space.size()-1) +1);
            }

            // search there are firstName, lastName
        };
        button.setOnClickListener(myListener2);


    }

    // additional functions
    public static boolean isWord(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static ArrayList indexOfSpace(String inputPersed){
        ArrayList<Integer> indexOfSpce= new ArrayList<Integer>();
        for (int i = 0; i < inputPersed.length(); i++){
            if (!isWord(inputPersed.charAt(i))){
                indexOfSpce.add(i);
            }
        }
        return indexOfSpce;
    }

}