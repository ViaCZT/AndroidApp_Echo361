package com.example.echo361;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

     /*   这段是想用searchView，还是由你决定怎么写，不需要可删掉此段
        SearchView searchPerson = findViewById(R.id.searchPerson);
        searchPerson.setQueryHint("Search the person to chat");
        searchPerson.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 在用户提交搜索时回调
                // 根据搜索关键字进行搜索操作
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 在搜索关键字改变时回调
                // 可以根据搜索关键字进行实时搜索操作
                return false;
            }
        });

        String target = searchPerson.getQuery().toString();*/

       String target = name.getText().toString();
        ArrayList<String> arrayList = new ArrayList<>();
        Button search = findViewById(R.id.btn_searchChat);
        search.setOnClickListener(view -> {
            ArrayList<Student> students = (ArrayList<Student>) Student.search(target);
            for(Student s:students){
                arrayList.add(s.getname());
            }
        });

        ListView listView = findViewById(R.id.list_name);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(SearchChatTarget.this, ChatActivity.class);
            String text = arrayList.get(i);
            intent.putExtra("targetName",text);
        });

    }
}