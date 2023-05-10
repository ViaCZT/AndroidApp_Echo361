package com.example.echo361.LayoutActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;

public class EnrollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);


        // search

        ArrayList<String> courses = new ArrayList<String>();

        ListView coursesList = (ListView) findViewById(R.id.list_courseList);
        EditText editText = findViewById(R.id.ed_searchCourse);

        ArrayAdapter coursesListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, courses);
        coursesList.setAdapter(coursesListAdapter);


        Button button = (Button) findViewById(R.id.btn_searchCourse);
        View.OnClickListener myListener2 = v -> {
            // get course code and college code
            String searchInput = editText.getText().toString();
            CTokenizer tok = new CourseTokenizer(searchInput);
            CExp parsedExp = CParser.parseExp(tok);
            String inputPersed = parsedExp.show();
            String courseCode;
            String collegeCode;
            int i  = 0;
            while (i < 4 && isWord(inputPersed.charAt(i))){
                i++;
            }
            collegeCode = inputPersed.substring(0,i);

            int j = 0;
            int startPos = 0;
            while (j < 4 && Character.isDigit(inputPersed.charAt(j))){
                startPos = j;
                j++;
            }
            courseCode = inputPersed.substring(startPos,j);

//            courses.add((collegeCode + " " + courseCode).toString());
//            courses.add(searchInput);
            courses.add(editText.getText().toString());
            editText.setText("");
            coursesListAdapter.notifyDataSetChanged();

            //search


//            final FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference ref = database.getReference("server/saving-data/fireblog/posts");


//            // search
//            courseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) { /*
//            TODO: get the value and transform or transfer the data into a User class.
//            Hint: use snapshot.getValue(String.class) to get the value and transform it into a string.
//            */
//                    for (DataSnapshot rootSnapshot : snapshot.getChildren()) {
//                        courses.add(rootSnapshot+"");
//                    }
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    // Failed to retrieve the data
//                    System.err.println("Failed to retrieve data, error: " + error.toException());
//                } });







        };
        button.setOnClickListener(myListener2);


    }

    public boolean isWord(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

}