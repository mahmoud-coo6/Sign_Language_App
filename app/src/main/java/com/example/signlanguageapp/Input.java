package com.example.signlanguageapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class Input extends Fragment {

    ViewPagerAdapter adapter;
    private View inputFragment;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.text,
            R.drawable.video,
            R.drawable.image
    };
    private String[] tabTitles = {
            "Text",
            "Video",
            "Image"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inputFragment = inflater.inflate(R.layout.activity_tap_input, container, false);

        toolbar = inputFragment.findViewById(R.id.toolbar);
        viewPager = inputFragment.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(0);
        setupViewPager(viewPager);

        tabLayout = inputFragment.findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        setupTabIconsTap1();
        setupTabIconsTap2();
        setupTabIconsTap3();
        return inputFragment;

    }

    public void setupTabIconsTap1() {
        TextView tabContent = setupTabIcons();
        tabContent.setText(tabTitles[0]);
        tabContent.setCompoundDrawablePadding(6);
        tabContent.setTextColor(Color.BLACK);
        tabContent.setCompoundDrawablesWithIntrinsicBounds(tabIcons[0], 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabContent);
    }

    public void setupTabIconsTap2() {
        TextView tabContent = setupTabIcons();
        tabContent.setText(tabTitles[1]);
        tabContent.setCompoundDrawablePadding(5);
        tabContent.setTextColor(Color.BLACK);
        tabContent.setCompoundDrawablesWithIntrinsicBounds(tabIcons[1], 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabContent);
    }

    public void setupTabIconsTap3() {
        TextView tabContent = setupTabIcons();
        tabContent.setText(tabTitles[2]);
        tabContent.setCompoundDrawablePadding(4);
        tabContent.setTextColor(Color.BLACK);
        tabContent.setCompoundDrawablesWithIntrinsicBounds(tabIcons[2], 0, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabContent);
    }


    private TextView setupTabIcons() {
        LinearLayout tabLinearLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabContent = tabLinearLayout.findViewById(R.id.tabContent);
        return tabContent;

    }

    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new TextFragment(), "Text");
        adapter.addFrag(new VideosFragment(), "Video");
        adapter.addFrag(new RecognitionActivity(), "Image");


        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }


        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
