package com.example.echo361;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseOperator {

//    public <E> void storeData(String path,E input){
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = firebaseDatabase.getReference("Course");
//        databaseReference.setValue("Course1");
//
//    }

    public void storeData(){
//        FirebaseApp.initializeApp()
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.setValue("course1");
    }




}
