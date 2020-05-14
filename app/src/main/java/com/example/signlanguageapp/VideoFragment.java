package com.example.signlanguageapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class VideoFragment extends Fragment {

    private View myVideoFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myVideoFragment = inflater.inflate(R.layout.input, container, false);

        return myVideoFragment;
    }
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        myVideoFragment = null;
//    }
}