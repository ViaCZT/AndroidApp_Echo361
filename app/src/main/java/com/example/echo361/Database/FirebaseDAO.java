package com.example.echo361.Database;

import android.content.Context;

import java.io.IOException;

public interface FirebaseDAO {
    <T> void getData(String refPath, String childPath, FirebaseDataCallback<T> callback);
    <E> void storeData(String refpath,String childpath,E input);
    void initialStudentData(Context context) throws IOException;
    void initialTeacherData(Context context) throws IOException;
    void initialCoursesData(Context context);
    void initialForumData(Context context) throws IOException;

}
