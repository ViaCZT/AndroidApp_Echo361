package com.example.echo361.LayoutActivity;

import static com.example.echo361.Search.Search.getName;
import static com.example.echo361.Search.Search.partialNameSearch;
import static com.example.echo361.Search.Search.readCourseDate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.echo361.Course;
import com.example.echo361.Database.FirebaseDAOImpl;
import com.example.echo361.Database.FirebaseDataCallback;
import com.example.echo361.Factory.Student;
import com.example.echo361.Search.CExp;
import com.example.echo361.Search.CParser;
import com.example.echo361.Search.CTokenizer;
import com.example.echo361.Search.CourseAVLtree;
import com.example.echo361.Search.CourseTokenizer;
import com.example.echo361.R;
import com.example.echo361.Search.NExp;
import com.example.echo361.Search.NParser;
import com.example.echo361.Search.NTokenizer;
import com.example.echo361.Search.NameTokenizer;
import com.example.echo361.Search.Search;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchChatTarget extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_chat_target);

        FirebaseApp.initializeApp(getBaseContext());
        FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();

        Intent intent0 = getIntent();
        String courseName = intent0.getStringExtra("courseName");
        String student_id = intent0.getStringExtra("student_id");

        // search

        EditText editText_name = findViewById(R.id.ed_searchName);
        ListView studentsList = (ListView) findViewById(R.id.list_name);



        Button button = (Button) findViewById(R.id.btn_searchChat);
        View.OnClickListener myListener2 = v -> {

            if(!(editText_name.getText().toString().isEmpty())) {
                String searchNameInput = editText_name.getText().toString();
                NTokenizer tok = new NameTokenizer(searchNameInput);
                NExp parsedExp = NParser.parseExp(tok);
                String inputPersed = parsedExp.show();

                firebaseDAOImpl.getData(courseName.substring(0, 4) + "Tree", null, new FirebaseDataCallback<String>() {

                    @Override
                    public void onDataReceived(String data) {

                        Gson gson = new Gson();
                        CourseAVLtree courseAVLtree = gson.fromJson(data, CourseAVLtree.class);
                        ArrayList<Course> courselist = new ArrayList<>();
                        courselist = courseAVLtree.inOrderBSTqualify(courselist, null, null, null, null, courseName.substring(4));

                        Course course = courselist.get(0);

                        System.out.println("N" + course);
                        Log.d("Search chat courses2", "courses from function" + course);

                        ArrayList<String> studentsId = new ArrayList<String>();
                        studentsId = course.getStudents();

                        Log.d("Search chat courses2", "studentsId from chat" + studentsId);

                        ArrayList<String> storeStudents = new ArrayList<>();

                        for (String i : studentsId) {
                            firebaseDAOImpl.getData("Students", null, new FirebaseDataCallback<ArrayList<HashMap<String, Object>>>() {
                                @Override
                                public void onDataReceived(ArrayList<HashMap<String, Object>> students) {


                                    for (HashMap<String, Object> hashMap1 : students) {

                                        Student student = new Student((String) hashMap1.get("userName"), (String) hashMap1.get("passWord"), (ArrayList<String>) hashMap1.get("courses"), null);
                                        if (student.getPassWord().equals(i) && !(student.getPassWord().equals(student_id))) {
                                            if (getName(student.getUserName())[0].toLowerCase().contains(getName(inputPersed)[0].toLowerCase()) &&
                                                    getName(student.getUserName())[1].toLowerCase().contains(getName(inputPersed)[1].toLowerCase())) {
                                                storeStudents.add(student.getUserName());
                                            }
                                        }
                                    }


                                    ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, storeStudents);
                                    studentsList.setAdapter(arrayAdapter);

                                    //转跳code需要在这里写
                                    studentsList.setOnItemClickListener((adapterView, view, i, l) -> {
//                                        Intent intent = new Intent(SearchChatTarget.this, .class);
//                                        SearchChatTarget.this.startActivity(intent);
                                    });


                                }

                                @Override
                                public void onError(DatabaseError error) {
                                    // 在这里处理错误
                                }
                            });
                        }


                    }

                    @Override
                    public void onError(DatabaseError error) {
                        // 在这里处理错误
                    }
                });
            }else{
                Context context = getApplicationContext();
                CharSequence text = "Input can not be empty.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }



//            ArrayAdapter courseListAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, courses);
//
//            readCourseDate(firebaseDAOImpl, courses, courseListAdapter, courseName);
//
//            Log.d("Search chat courses", "courses from function" + courses);







            // search there are firstName, lastName
        };
        button.setOnClickListener(myListener2);


    }

    // additional functions
    public static boolean isWord(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }


}