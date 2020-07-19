package com.example.signlanguageapp;


import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;

public class MyFirebaseController {

    static FirebaseAuth mFirebaseAuth;

    static DatabaseReference mFirebaseDatabase;
    static FirebaseAuth auth;
    private static FirebaseOptions options;


    FileInputStream refreshToken;


    public static FirebaseUser getCurrentUserId() {
        if (mFirebaseAuth == null)

            mFirebaseAuth = FirebaseAuth.getInstance();


        auth = mFirebaseAuth;

        return mFirebaseAuth.getCurrentUser();


    }


    public static DatabaseReference getDatabaseReference() {

        if (mFirebaseDatabase == null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
            mFirebaseDatabase = database.getReference();
        }

        return mFirebaseDatabase;
    }

}
