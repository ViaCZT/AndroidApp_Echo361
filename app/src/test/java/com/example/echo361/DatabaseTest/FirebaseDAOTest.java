/**
 * Mockito cannot mock Firebase classes, including class com.google.firebase.database.FirebaseDatabase etc.
 * Because Mockito can only mock non-private & non-final classes, while the Firebase classes are modified by final.
 *
 * Alternatively, since we are using the Firebase Realtime Database, we can use the Firebase Emulator Suite
 * for integration testing instead of using mock objects.
 * Firebase Emulator Suite can provide a local Firebase service where we can Run and test our Firebase
 * application locally, thus avoiding the problem of mock objects.
 *
 * @author Zetian Chen, u7564812
 */
package com.example.echo361.DatabaseTest;

import com.example.echo361.Database.FirebaseDAO;
import com.example.echo361.Database.FirebaseDAOImpl;
import com.example.echo361.Database.FirebaseDataCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

public class FirebaseDAOTest {

    @Test
    public void testGetData() {
        // Mock FirebaseDatabase and DatabaseReference
        FirebaseDatabase firebaseDatabase = Mockito.mock(FirebaseDatabase.class); // Mockito cannot mock this class: class com.google.firebase.database.FirebaseDatabase.
        DatabaseReference databaseReference = Mockito.mock(DatabaseReference.class);

        // Mock DataSnapshot and its value
        DataSnapshot dataSnapshot = Mockito.mock(DataSnapshot.class);
        Map<String, String> data = new HashMap<>();
        data.put("key", "value");
        Mockito.when(dataSnapshot.getValue(Mockito.any(GenericTypeIndicator.class))).thenReturn(data);

        // Mock FirebaseDataCallback
        FirebaseDataCallback<Map<String, String>> callback = Mockito.mock(FirebaseDataCallback.class);

        // Mock the behavior of Firebase database and reference
        Mockito.when(FirebaseDatabase.getInstance()).thenReturn(firebaseDatabase);
        Mockito.when(firebaseDatabase.getReference("refPath")).thenReturn(databaseReference);
        Mockito.when(databaseReference.child("childPath")).thenReturn(databaseReference);
        Mockito.doAnswer(invocation -> {
            ValueEventListener valueEventListener = invocation.getArgument(0);
            valueEventListener.onDataChange(dataSnapshot);
            return null;
        }).when(databaseReference).addListenerForSingleValueEvent(Mockito.any(ValueEventListener.class));

        // Create an instance of FirebaseDAOImpl
        FirebaseDAO firebaseDAO = FirebaseDAOImpl.getInstance();

        // Call the getData() method
        firebaseDAO.getData("refPath", "childPath", callback);

        // Verify that the DatabaseReference methods were called correctly
        Mockito.verify(databaseReference).addListenerForSingleValueEvent(Mockito.any(ValueEventListener.class));
        Mockito.verify(databaseReference).child("childPath");

        // Verify that the FirebaseDataCallback methods were called correctly
        Mockito.verify(callback).onDataReceived(data);
        Mockito.verify(callback, Mockito.never()).onError(Mockito.any(DatabaseError.class));
    }
}

