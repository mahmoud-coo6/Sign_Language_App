//package com.example.signlanguageapp;
//
//import android.content.Context;
//
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentPagerAdapter;
//
//class PagerAdapter extends FragmentPagerAdapter {
//
//    private Context mContext;
//
//    public PagerAdapter(Context context, FragmentManager fm) {
//        super(fm);
//        mContext = context;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        if (position == 0) {
//            return new TextFragment();
//        } else if (position == 1) {
//
//            return new VideosFragment();
//        } else {
//
//            return new RecognitionActivity();
//        }
//    }
//
//    @Override
//    public int getCount() {
//        return 3;
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        if (position == 0) {
//            return "Text";
//        } else if (position == 1) {
//            return "Sound";
//        } else {
//            return "Image";
//        }
//    }
//
//
//}