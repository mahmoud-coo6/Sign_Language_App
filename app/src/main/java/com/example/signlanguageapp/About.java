package com.example.signlanguageapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class About extends Fragment {
    private View aboutFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        aboutFragment = inflater.inflate(R.layout.about, container, false);

        return aboutFragment;
    }

}
