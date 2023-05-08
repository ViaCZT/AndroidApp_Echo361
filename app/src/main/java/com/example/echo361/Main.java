package com.example.echo361;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Main {
    public static void main(String[] args) {

//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setApplicationId("")
//                .setApiKey("")
//                .setDatabaseUrl("")
//                .build();
//        FirebaseApp.initializeApp(,options);
        FirebaseOperator firebaseOperator = new FirebaseOperator();
        firebaseOperator.storeData();

    }
}
