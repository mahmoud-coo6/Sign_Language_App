package com.example.signlanguageapp;

//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyFirebaseController {

    static FirebaseAuth mFirebaseAuth;
    //   static FirebaseDatabase mFirebaseDatabase ;
    static DatabaseReference mFirebaseDatabase;
    static FirebaseAuth auth;
    private static FirebaseOptions options;
//   static DatabaseReference mFirebaseDatabase ;

//    FirebaseOptions options;
    FileInputStream refreshToken;

//    {
//        try {
//            refreshToken = new FileInputStream("C:\\Users\\Acer\\AndroidStudioProjects\\SignLanguageApp\\app\\google-services.json");
//
//            options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
//                    .setDatabaseUrl("https://sign-language-5444d.firebaseio.com/")
//                    .build();
//
//            FirebaseApp.initializeApp(options);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }





    public static FirebaseUser getCurrentUserId() {
        if (mFirebaseAuth == null)
//            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseAuth = FirebaseAuth.getInstance();


        auth = mFirebaseAuth;

            return mFirebaseAuth.getCurrentUser();


    }

//    public static FirebaseUser getAuthtication(){
//        if (mFirebaseAuth == null)
//            mFirebaseAuth = FirebaseAuth.getInstance();
//
//      return   mFirebaseAuth.get();
//    }


// public static  String getCurrentUserId(){
//        if (mFirebaseAuth == null)
//            mFirebaseAuth = FirebaseAuth.getInstance();
//
//      return   mFirebaseAuth.getCurrentUser().getUid();
//    }

    public static DatabaseReference getDatabaseReference() {

        if (mFirebaseDatabase == null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
            mFirebaseDatabase = database.getReference();
        }

        return mFirebaseDatabase;
    }

}
