package com.example.signlanguageapp;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class PagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public PagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new TextFragment();
        } else if (position == 1) {
            return new ImageFragment();
        } else {
            return new SoundFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Text";
        } else if (position == 1) {
            return "Image";
        }  else {
            return "Sound";
        }
    }


}