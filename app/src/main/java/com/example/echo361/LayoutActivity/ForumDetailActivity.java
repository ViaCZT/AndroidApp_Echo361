package com.example.echo361.LayoutActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.echo361.Course;
import com.example.echo361.Database.FirebaseDAOImpl;
import com.example.echo361.Database.FirebaseDataCallback;
import com.example.echo361.Forum;
import com.example.echo361.ForumPost;
import com.example.echo361.R;
import com.example.echo361.Search.CourseAVLtree;
import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ForumDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_detail);

        TextView title = findViewById(R.id.tx_forumTitle);
        TextView content = findViewById(R.id.tx_forumPoster);
        Intent intent = getIntent();
        String course_name = intent.getStringExtra("courseName");
        int postIndex = intent.getIntExtra("postIndex", 0);
        title.setText(intent.getStringExtra("postTitle"));
        content.setText(intent.getStringExtra("postContent"));

        EditText editText = findViewById(R.id.ed_postIdea);

        Button newPost = findViewById(R.id.btn_postIdea);

        // 更新Firebase中的回复
        FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();
        firebaseDAOImpl.getData(course_name.substring(0, 4) + "Tree", null, new FirebaseDataCallback<String>() {
            @Override
            public void onDataReceived(String data) {
                Gson gson = new Gson();
                CourseAVLtree courseAVLtree = gson.fromJson(data, CourseAVLtree.class);
                Course course = courseAVLtree.find(Integer.valueOf(course_name.substring(4, 8))).course;
                Forum forum = course.getForum();
                ForumPost forumPost = forum.getPosts().get(postIndex);
                ArrayList<String> floors = forumPost.getFloors();
                Log.d("ForumDetailActivity","Floors after get: " + floors.size());

                ListView listView = findViewById(R.id.list_forumPost);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ForumDetailActivity.this, android.R.layout.simple_list_item_1, floors);
                listView.setAdapter(arrayAdapter);

                newPost.setOnClickListener(view -> {
                    String content0 = editText.getText().toString();
                    editText.setText("");
//                    Log.d("ForumDetailActivity","Before adding reply, floors size: " + floors.size());
//                    floors.add(content0);
//                    Log.d("ForumDetailActivity","After adding reply, floors size: " + floors.size());
                    arrayAdapter.notifyDataSetChanged();
//                    Log.d("ForumDetailActivity","Floors in arrayAdapter after notifyDataSetChanged: " + arrayAdapter.getCount());
                    // 更新课程论坛并将其存储在Firebase中
                    forumPost.getFloors().add(content0);
                    course.setForum(forum);
                    CourseAVLtree newCourseNode = courseAVLtree.find(Integer.valueOf(course_name.substring(4, 8)));
                    newCourseNode.course = course;
                    String updatedTreeJson = gson.toJson(courseAVLtree);
                    firebaseDAOImpl.storeData(course_name.substring(0, 4) + "Tree", null, updatedTreeJson);
                });

            }

            @Override
            public void onError(DatabaseError error) {
                // 处理错误
            }
        });


    }
}
