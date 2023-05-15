/**
 * This interface serves as a callback for Firebase data retrieval operations.
 * @param <T> the type of data returned by the callback.
 * @author Zetian Chen, u7564812
 */
package com.example.echo361.Database;

import com.google.firebase.database.DatabaseError;

public interface FirebaseDataCallback<T> {
    /**
     * Called when data is received successfully.
     * @param data the data received from Firebase
     */
    void onDataReceived(T data);

    /**
     * Called when there is an error retrieving data from Firebase.
     * @param error the error received from Firebase
     */
    void onError(DatabaseError error);
}
