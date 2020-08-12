package com.example.signlanguageapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Vector;

public class VideosFragment extends Fragment {

    List<VideoItem> videoList;
    SoundTextAdapter soundTextAdapter;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    Button button;
    Vector<YouTubeVideos> youtubeVideos = new Vector<YouTubeVideos>();
    private View myvideoFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myvideoFragment = inflater.inflate(R.layout.input_sound, container, false);
        myvideoFragment.setTag("Fragment_TAG");

        recyclerView = myvideoFragment.findViewById(R.id.recyclerview);
        relativeLayout = myvideoFragment.findViewById(R.id.content);
        button = myvideoFragment.findViewById(R.id.button);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/CD1c1F6Q1iY\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/kIq5w2EguTU\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/0TZPoYKkC-s\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/yToUNDce_2Y&t=3s\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/PYJhLcLda_U\" frameborder=\"0\" allowfullscreen></iframe>"));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container2, new VideosFragment());
                transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        if (isOnline()) {
            recyclerView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
            VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);

            recyclerView.setAdapter(videoAdapter);
        } else {
            try {


                recyclerView.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);


            } catch (Exception e) {

            }
        }

        return myvideoFragment;
    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnected() && netInfo.isAvailable();
    }

}