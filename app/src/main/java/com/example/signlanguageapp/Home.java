package com.example.signlanguageapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Locale;

public class Home extends AppCompatActivity {

    ImageView floatingActionButton;
    boolean isRed = true;

    private ActionBar toolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.home:

                    fragment = new Input();
                    loadFragment(fragment);
                    return true;
                case R.id.category:
                    fragment = new Category();
                    loadFragment(fragment);
                    return true;
                case R.id.dictionary:
                    fragment = new Dictionary();
                    loadFragment(fragment);
                    return true;
                case R.id.about:
                    fragment = new About();
                    loadFragment(fragment);
                    return true;
            }
            return true;
        }
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        CustomBottomNavigationView curvedBottomNavigationView = findViewById(R.id.customBottomBar);
        floatingActionButton = findViewById(R.id.fab);
        curvedBottomNavigationView.inflateMenu(R.menu.navigation);
        curvedBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                selectColor(v, "green");
                getSpeechInput(v);
            }
        });


        loadFragment(new Input());
    }

    public void selectColor(View view, String color) {


        VectorChildFinder vector = new VectorChildFinder(this, R.drawable.microphone, floatingActionButton);

        VectorDrawableCompat.VFullPath path1 = vector.findPathByName("path1");


        if (color.equals("green")) {
            path1.setFillColor(Color.GREEN);


        } else if (color.equals("red")) {
            path1.setFillColor(Color.RED);

        }

    }


    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());


        if (intent.resolveActivity(view.getContext().getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(view.getContext(), "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    selectColor(floatingActionButton, "red");
                    Bundle bundle = new Bundle();
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    bundle.putString("result", result.get(0));
                    TextFragment textFragment = new TextFragment();
                    textFragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_container, textFragment);
                    transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.addToBackStack(null);
                    transaction.commit();


                } else {
                    selectColor(floatingActionButton, "red");
                }
                break;
        }
    }


    private void loadFragment(Fragment fragment) {


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
