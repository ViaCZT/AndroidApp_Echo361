package com.example.echo361.Database;

import com.example.echo361.Factory.Student;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public interface FirebaseDataCallback<T> {
    void onDataReceived(T data);
    void onError(DatabaseError error);
}
