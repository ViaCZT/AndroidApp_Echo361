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
import android.widget.Toast;

import com.example.echo361.Database.FirebaseDAOImpl;
import com.example.echo361.Database.FirebaseDataCallback;
import com.example.echo361.Forum;
import com.example.echo361.ForumPost;
import com.example.echo361.R;
import com.example.echo361.util.ToastUtil;
import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class ForumTotalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_total);

        Intent intent1 = getIntent();
        String name0 = intent1.getStringExtra("name");
        String course_name = intent1.getStringExtra("courseName");
        Boolean isTeacher = intent1.getBooleanExtra("is_teacher", false);
        Log.d("111", course_name.substring(0,4));

        FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();

        final Forum[] forum = new Forum[1];
        final ArrayList<ForumPost> visiblePosts = new ArrayList<>();

        firebaseDAOImpl.getData("Forums",course_name, new FirebaseDataCallback<String>() {

            @Override
            public void onDataReceived(String data) {
                Gson gson = new Gson();
                if(!data.equals("0")){
                    forum[0] = gson.fromJson(data, Forum.class);
                }
                else {
                    forum[0] = new Forum(course_name,new ArrayList<>());
                }
                ArrayList<ForumPost> posts = forum[0].getPosts();
                ArrayList<String> postTitles = new ArrayList<>();
                for (ForumPost post : posts) {
                    postTitles.add(post.getTitle());
                }
                ArrayList<String> visiblePostsTitles = new ArrayList<>();
                for(ForumPost forumPost:posts){
                    if(forumPost.getVisible()) {
                        visiblePostsTitles.add(forumPost.getTitle());
                    }
                }

                EditText title = findViewById(R.id.ed_postTitle);
                EditText content = findViewById(R.id.ed_postContent);

                Button newPost = findViewById(R.id.btn_postForum);
                Button block = findViewById(R.id.btn_blockForum);

                ListView listView = findViewById(R.id.list_forumTotal);
                ArrayAdapter<String> arrayAdapter;
                if (!isTeacher) {
                    block.setVisibility(View.INVISIBLE);
                }
                else {
                    block.setVisibility(View.VISIBLE);
                }

                ArrayList<String> postTitlesForAdapter = new ArrayList<>();

                for (ForumPost post : forum[0].getPosts()) {
                    if (isTeacher || post.getVisible()) {
                        postTitlesForAdapter.add(post.getTitle());
                        visiblePosts.add(post); // 添加可见帖子
                    }
                }
                arrayAdapter = new ArrayAdapter<>(ForumTotalActivity.this, android.R.layout.simple_list_item_1, postTitlesForAdapter);
                listView.setAdapter(arrayAdapter);

                AtomicBoolean isBlockingMode = new AtomicBoolean(false);
                listView.setOnItemClickListener((adapterView, view, i, l) -> {
                    ForumPost selectedPost = visiblePosts.get(i); // 使用可见帖子列表
                    if (isBlockingMode.get()) {
                        // 更改论坛帖子的可见性并更新列表
                        ForumPost selectedPostBlock = forum[0].getPosts().get(i);
                        selectedPost.setVisible(!selectedPostBlock.getVisible());
                        arrayAdapter.notifyDataSetChanged();
                        ToastUtil.showMsg(ForumTotalActivity.this, "Visibility toggled for post " + selectedPostBlock.getTitle());

                        // 更新 Firebase 数据库中的论坛帖子
                        String forumId = course_name;
                        Gson gson1 = new Gson();
                        String forumJson = gson1.toJson(forum[0]);
                        firebaseDAOImpl.storeData("Forums", course_name, forumJson);
                    }
                    else {
                        Intent intent = new Intent(ForumTotalActivity.this, ForumDetailActivity.class);
                        intent.putExtra("postTitle", selectedPost.getTitle());
                        intent.putExtra("postContent", selectedPost.getContent());
                        intent.putExtra("floors", selectedPost.getFloors());
                        intent.putExtra("courseName", course_name);
                        intent.putExtra("postIndex", forum[0].getPosts().indexOf(selectedPost)); // 使用原始帖子列表中的索引
                        ForumTotalActivity.this.startActivity(intent);
                    }

                });

                newPost.setOnClickListener(view -> {
                    String title0 = title.getText().toString();
                    String content0 = content.getText().toString();
                    title.setText("");
                    content.setText("");
                    if (!title0.isEmpty()&&!content0.isEmpty()) {
                        ForumPost forumPost = new ForumPost(title0, content0, name0, new ArrayList<>(), true);
                        postTitles.add(forumPost.getTitle());
                        visiblePostsTitles.add(forumPost.getTitle());
                        postTitlesForAdapter.add(forumPost.getTitle());
                        arrayAdapter.notifyDataSetChanged();
                        forum[0].getPosts().add(forumPost);
                        // 将论坛存储在独立的分支中
                        String forumId = course_name; // 为每个论坛分配一个唯一ID，例如课程名称
                        String forumJson = gson.toJson(forum[0]);
                        firebaseDAOImpl.storeData("Forums", forumId, forumJson);
                    }
                    else
                        ToastUtil.showMsg(ForumTotalActivity.this, "Title or content can't be empty!");
                });

                block.setOnClickListener(view -> {
                    // 切换屏蔽模式
                    if (!isBlockingMode.get()) {
                        isBlockingMode.set(true);
                        ToastUtil.showMsg(ForumTotalActivity.this, "Blocking mode enabled");
                    } else {
                        isBlockingMode.set(false);
                        ToastUtil.showMsg(ForumTotalActivity.this, "Blocking mode disabled");
                    }

                });


            }

            @Override
            public void onError(DatabaseError error) {
                // 在这里处理错误
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        Toast.makeText(getApplicationContext(), "onStart!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(), "onRestart!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "onResume!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), "onPause!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "onStop!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "onDestroy!", Toast.LENGTH_LONG).show();
    }

}

