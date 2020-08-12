package com.example.signlanguageapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText email_logEt, password_logEt;
    ImageView image_close_login, loginButon;
//    Button loginButon;

    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        currentUser = MyFirebaseController.getCurrentUserId();

        if (currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, Home.class);
            intent.putExtra("userId", currentUser.getUid());
            startActivity(intent);
            finish();
        }

        email_logEt = findViewById(R.id.email_logEt);
        password_logEt = findViewById(R.id.password_logEt);
        loginButon = findViewById(R.id.loginButon);


        loginButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ("".equals(email_logEt.getText().toString().trim())) {
                    email_logEt.setError("worng email");
                    return;
                }
                if (password_logEt.getText().toString().trim().equals("")) {
                    password_logEt.setError("worng password");
                    return;
                }

                if (isNetworkAvailable()) {
                    doSignIn(email_logEt.getText().toString(), password_logEt.getText().toString());
                } else {
                    Toast.makeText(LoginActivity.this, "please, check internet connection.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SingUp.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.entergust).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Home.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

       this.finishAffinity();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void doSignIn(String email, String password) {
        MyFirebaseController.auth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final FirebaseUser user = MyFirebaseController.auth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Success Login.", Toast.LENGTH_SHORT).show();

                            Map<String, Object> data = new HashMap<>();
                            data.put("lastSignIn", new Date().getTime());

                            FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).updateChildren(data)
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                            Log.d("error", e.getLocalizedMessage());
                                        }
                                    })
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent intent = new Intent(LoginActivity.this, Home.class);
                                            intent.putExtra("userId", user.getUid());
                                            startActivity(intent);
                                            finish();
                                        }
                                    });


                        } else {
                            try {
                                task.getResult();
                                Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                Toast.makeText(LoginActivity.this, e.getMessage().split(":")[1], Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
    }
}





