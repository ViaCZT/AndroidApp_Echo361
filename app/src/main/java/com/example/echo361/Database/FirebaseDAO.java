package com.example.echo361.Database;

import android.content.Context;

import java.io.IOException;

public interface FirebaseDAO {
    <T> void getData(String refPath, String childPath, FirebaseDataCallback<T> callback);
    <E> void storeData(String refpath,String childpath,E input);
    void storeStudentAndTeacherData(Context context) throws IOException;
    void storeCoursesData(Context context);
}
