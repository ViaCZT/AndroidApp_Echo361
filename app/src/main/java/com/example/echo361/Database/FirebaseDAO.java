package com.example.echo361.Database;

import android.content.Context;

import com.example.echo361.ForumPost;

import java.io.IOException;

public interface FirebaseDAO {
    <T> void getData(String refPath, String childPath, FirebaseDataCallback<T> callback);
    <E> void storeData(String refpath,String childpath,E input);
    void initialStudentAndTeacherData(Context context) throws IOException;
    void initialCoursesData(Context context);
    void initialForum(Context context) throws IOException;
    void updateForumPost(String course, String postNum, ForumPost post);
}
