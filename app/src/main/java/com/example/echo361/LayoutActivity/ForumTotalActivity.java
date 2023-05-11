package com.example.echo361.LayoutActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.echo361.Course;
import com.example.echo361.Database.FirebaseDAOImpl;
import com.example.echo361.Database.FirebaseDataCallback;
import com.example.echo361.Forum;
import com.example.echo361.ForumPost;
import com.example.echo361.R;
import com.example.echo361.Search.CourseAVLtree;
import com.example.echo361.util.ToastUtil;
import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ForumTotalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_total);

        Intent intent1 = getIntent();
        String name0 = intent1.getStringExtra("name");
        String course_name = intent1.getStringExtra("courseName");
        Boolean isTeacher = intent1.getBooleanExtra("is_Teacher", false);
        Log.d("111", course_name.substring(0,4));

        FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();

        final Forum[] forum = new Forum[1];
        final CourseAVLtree[] courseAVLtree = new CourseAVLtree[1];

        firebaseDAOImpl.getData(course_name.substring(0, 4) + "Tree", null, new FirebaseDataCallback<String>() {

            @Override
            public void onDataReceived(String data) {
                Gson gson = new Gson();
                courseAVLtree[0] = gson.fromJson(data, CourseAVLtree.class);
                Course course = courseAVLtree[0].find(Integer.valueOf(course_name.substring(4, 8))).course;
                forum[0] = course.getForum();

                ArrayList<ForumPost> posts = forum[0].getPosts();
                ArrayList<String> postTitles = new ArrayList<>();
                for (ForumPost post : posts) {
                    postTitles.add(post.getTitle());
                }

                ListView listView = findViewById(R.id.list_forumTotal);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ForumTotalActivity.this, android.R.layout.simple_list_item_1, postTitles);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener((adapterView, view, i, l) -> {
                    Intent intent = new Intent(ForumTotalActivity.this, ForumDetailActivity.class);
                    intent.putExtra("postTitle", posts.get(i).getTitle());
                    intent.putExtra("postContent", posts.get(i).getContent());
                    intent.putExtra("floors", posts.get(i).getFloors());
                    intent.putExtra("courseName",course_name);
                    intent.putExtra("postIndex",i);
                    ForumTotalActivity.this.startActivity(intent);
                });

                EditText title = findViewById(R.id.ed_postTitle);
                EditText content = findViewById(R.id.ed_postContent);

                Button newPost = findViewById(R.id.btn_postForum);
                Button block = findViewById(R.id.btn_blockForum);
                if (!isTeacher) {
                    block.setVisibility(View.INVISIBLE);
                }
                newPost.setOnClickListener(view -> {
                    String title0 = title.getText().toString();
                    String content0 = content.getText().toString();
                    if (!title0.isEmpty()&&!content0.isEmpty()) {
                        ForumPost forumPost = new ForumPost(title0, content0, name0, new ArrayList<>(), true);
                        postTitles.add(forumPost.getTitle());
//                        posts.add(forumPost);
                        arrayAdapter.notifyDataSetChanged();
                        forum[0].getPosts().add(forumPost);

                        // 将包含新帖子的论坛存储在Firebase中
                        course.setForum(forum[0]);
                        CourseAVLtree newCourseNode = courseAVLtree[0].find(Integer.valueOf(course_name.substring(4, 8)));
                        newCourseNode.course = course;
                        // 将修改后的树存储到Firebase
                        Gson gson1 = new Gson();
                        String updatedTreeJson = gson1.toJson(courseAVLtree[0]);
                        firebaseDAOImpl.storeData(course_name.substring(0, 4) + "Tree", null, updatedTreeJson);
                    }
                    else
                        ToastUtil.showMsg(ForumTotalActivity.this, "Title or content can't be empty!");
                });
            }

            @Override
            public void onError(DatabaseError error) {
                // 在这里处理错误
            }
        });
    }
}

