package com.example.echo361.LayoutActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

/**
 * @Author  Zihan Ai, u7528678
 * The ForumTotalActivity class represents an overview page of a course forum.
 * On this page, users can view, post, and (if a teacher) manage posts.
 */
public class ForumTotalActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_total);

        // Retrieve intent data
        Intent intent1 = getIntent();
        String name0 = intent1.getStringExtra("name");
        String course_name = intent1.getStringExtra("courseName");
        Boolean isTeacher = intent1.getBooleanExtra("is_teacher", false);
        Log.d("111", course_name.substring(0,4));

        // set course name to slogan TextView
        TextView courseNameTextView = findViewById(R.id.tx_forumSlogan);
        String forumSloganMessage = getString(R.string.the_forum_is_supposed_to_be_collaborative_and_supportive);
        courseNameTextView.setText(course_name+"\n"+ forumSloganMessage);

        FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();

        final Forum[] forum = new Forum[1];
        final ArrayList<ForumPost> visiblePosts = new ArrayList<>();

        // Get forum data from Firebase
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

                // Process forum data and populate lists with post titles
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

                // Set up list view, adapter, and event listeners
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
                        visiblePosts.add(post); // Add visible posts
                    }
                }
                arrayAdapter = new ArrayAdapter<>(ForumTotalActivity.this, android.R.layout.simple_list_item_1, postTitlesForAdapter);
                listView.setAdapter(arrayAdapter);

                AtomicBoolean isBlockingMode = new AtomicBoolean(false);
                listView.setOnItemClickListener((adapterView, view, i, l) -> {
                    ForumPost selectedPost = visiblePosts.get(i); // Use the list of visible posts
                    if (isBlockingMode.get()) {
                        // Change visibility and update the list
                        ForumPost selectedPostBlock = forum[0].getPosts().get(i);
                        selectedPost.setVisible(!selectedPostBlock.getVisible());
                        arrayAdapter.notifyDataSetChanged();
                        if(selectedPost.getVisible()==true) {
                            ToastUtil.showMsg(ForumTotalActivity.this, "Visibility for post " + selectedPostBlock.getTitle()+" true");
                        }
                        else
                            ToastUtil.showMsg(ForumTotalActivity.this, "Visibility for post " + selectedPostBlock.getTitle()+" false");

                        // update posts in Firebase
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
                        intent.putExtra("postIndex", forum[0].getPosts().indexOf(selectedPost));
                        ForumTotalActivity.this.startActivity(intent);
                    }

                });

                // Handle new post button click
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

                        // Save forum
                        String forumId = course_name; // Distribute a unique name, like course name
                        String forumJson = gson.toJson(forum[0]);
                        firebaseDAOImpl.storeData("Forums", forumId, forumJson);
                    }
                    else
                        ToastUtil.showMsg(ForumTotalActivity.this, "Title or content can't be empty!");
                });

                // Handle block button click (for teachers)
                block.setOnClickListener(view -> {
                    // Switch block mode
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
                // deal with mistake
            }
        });
    }

}

