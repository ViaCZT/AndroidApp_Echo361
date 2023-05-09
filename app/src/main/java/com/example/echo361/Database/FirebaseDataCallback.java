package com.example.echo361.Database;

import com.google.firebase.database.DatabaseError;

public interface FirebaseDataCallback<T> {
    void onDataReceived(T data);
    void onError(DatabaseError error);
}
