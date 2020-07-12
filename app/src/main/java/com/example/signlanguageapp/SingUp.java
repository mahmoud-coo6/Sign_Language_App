package com.example.signlanguageapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
//import com.google.firebase.auth.FirebaseToken;
//import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
//import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SingUp extends AppCompatActivity {
    private static final String TAG = "SingUp";
    EditText passwordEt, emailEt;
    TextView text_singTv;
    ImageView image_close;
    Button singupBt;
    //    FirebaseAuth mAuth;
    FirebaseUser currentUser;
//    boolean isAdmin= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
        currentUser = MyFirebaseController.getCurrentUserId();

        if (currentUser != null) {
            Intent intent = new Intent(SingUp.this, Home.class);
            intent.putExtra("userId", currentUser.getUid());
            startActivity(intent);
            finish();
        }

        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        singupBt = findViewById(R.id.singupBt);
//        image_close = findViewById(R.id.image_close);


//        image_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SingUp.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//
//            }
//        });
        singupBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ("".equals(emailEt.getText().toString().trim())) {
                    emailEt.setError("worng email");
                    return;
                }
                if (passwordEt.getText().toString().trim().equals("")) {
                    passwordEt.setError("worng password");
                    return;
                }

                if (isNetworkAvailable()) {
                    doSignUp(emailEt.getText().toString(), passwordEt.getText().toString());
                } else {
                    Toast.makeText(SingUp.this, "please, check internet connection.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingUp.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.entergust).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingUp.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

//        findViewById(R.id.admin_check).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CheckBox checkBox = (CheckBox)v;
//                if(checkBox.isChecked()){
//                    isAdmin= true;
//                }else{
//                    isAdmin= false;
//                }
//
//            }
//        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void doSignUp(String email, String password){
        
            MyFirebaseController.auth.createUserWithEmailAndPassword(email, password).
                    addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                final FirebaseUser user = MyFirebaseController.auth.getCurrentUser();

                                Toast.makeText(SingUp.this, "Success SignUp, ", Toast.LENGTH_SHORT).show();

                                String emailF = user.getEmail();
                                String uid = user.getUid();
                                Map<String, Object> data = new HashMap<>();
                                data.put("uid", uid);
                                data.put("email", emailF);
                                data.put("createdAt", new Date().getTime());
//                                Map<String, Object> claims = new HashMap<>();
//                                data.put("admin", isAdmin);
//                                MyFirebaseController.auth.getCurrentUser().getIdToken(true);

//                                try {
//                                    MyFirebaseController.auth.setCustomUserClaims(uid, claims);
//                                } catch (FirebaseAuthException e) {
//                                    e.printStackTrace();
//                                }

//                                if (Boolean.TRUE.equals(claims.get("admin"))) {
//                                    // Add level.
//                                    claims.put("level", 10);
//                                    // Add custom claims for additional privileges.
//                                    FirebaseAuth.getInstance().setCustomUserClaims(user.getUid(), claims);
//                                }
//                                user.getIdToken(false).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
//                                    @Override
//                                    public void onSuccess(GetTokenResult result) {
//                                        boolean isAdmin = (boolean) result.getClaims().get("admin");
//                                        if (isAdmin) {
//                                            // Show admin UI.
//                                            showAdminUI();
//                                        } else {
//                                            // Show regular user UI.
//                                            showRegularUI();
//                                        }
//                                    }
//                                });
//                                // Verify the ID token first.
//                                UserRecord user2 = null;
//                                try {
//                                    user2 = FirebaseAuth.getInstance().getUser(uid);
//                                    System.out.println(user2.getCustomClaims().get("admin"));
//                                } catch (FirebaseAuthException e) {
//                                    e.printStackTrace();
//                                }


                                FirebaseDatabase.getInstance().getReference().child("User").child(uid).setValue(data)
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SingUp.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                Log.d("error", e.getLocalizedMessage());
                                            }
                                        })
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent intent = new Intent(SingUp.this, Home.class);
                                                intent.putExtra("userId", user.getUid());
                                                startActivity(intent);
                                                finish();
                                            }
                                        });

                            } else {
                                try {
                                    task.getResult();
                                    Toast.makeText(SingUp.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                                } catch (Exception e) {
                                    Toast.makeText(SingUp.this, e.getMessage().split(":")[1], Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
      
       
    }
}





