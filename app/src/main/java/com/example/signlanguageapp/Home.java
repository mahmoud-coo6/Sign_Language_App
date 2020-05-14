package com.example.signlanguageapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    private ActionBar toolbar;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.home:
//                    toolbar.setTitle("Home");
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

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar = getSupportActionBar();
//        toolbar.setTitle("Home");
        loadFragment(new Input());
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
//        fragment.getContext().getChildFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

}
