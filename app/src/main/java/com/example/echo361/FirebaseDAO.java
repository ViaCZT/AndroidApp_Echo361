package com.example.echo361;

import android.content.Context;

import java.io.IOException;
import java.util.List;

public interface FirebaseDAO {
//    void addItem(FirebaseDAO item);
//    void updateItem(FirebaseDAO item);
//    void deleteItem(FirebaseDAO item);
//    List<FirebaseDAO> getAllItems();
    <E> void storeData(String refpath,String childpath,E input);
    void storeStudentAndTeacherData(Context context) throws IOException;
    void storeCoursesData(Context context);
}
