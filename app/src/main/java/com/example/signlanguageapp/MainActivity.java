package com.example.signlanguageapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs = null;
    List<SliderItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slider);

        prefs = getSharedPreferences("com.example.signlanguageapp", MODE_PRIVATE);

        items = new ArrayList<SliderItem>();
        items.add(new SliderItem("Sign Language Learning &amp; Translating", "Welcome to sign language Buy our products easily and get access to app only exclusives.", R.drawable.binocularsicon));
        items.add(new SliderItem("Dictionary", "Use the dictonary to find specific character or number", R.drawable.dictionary));
        items.add(new SliderItem("Quick Search", "Quickly find the sign you like the most.", R.drawable.search));
        items.add(new SliderItem("Recorder Sounds", "Use microphone to translate what you need", R.drawable.ic_soundlogo));

        SliderView sliderView = findViewById(R.id.imageSlider);

        SliderAdapter adapter = new SliderAdapter(this);
        adapter.renewItems(items);
        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4);


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            prefs.edit().putBoolean("firstrun", false).apply();
        } else {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
    }
}
