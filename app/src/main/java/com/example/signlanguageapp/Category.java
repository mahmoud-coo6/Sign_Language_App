package com.example.signlanguageapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Category extends Fragment {

    CategrayAdapter categrayAdapter;
    EditText search;
    List<CategoryItem> categoryList = new ArrayList<>();
    private View categoryFragment;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        categoryFragment = inflater.inflate(R.layout.category, container, false);
        toolbar = categoryFragment.findViewById(R.id.toolbar);

        int columns = 2;
        RecyclerView recyclerView = categoryFragment.findViewById(R.id.category_rv);
        LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), columns);
        recyclerView.setLayoutManager(layoutManager);
        categoryList.add(new CategoryItem("Alphaptic", R.drawable.numbercategory));
        categoryList.add(new CategoryItem("Number", R.drawable.numbercategory));
        categrayAdapter = new CategrayAdapter(getActivity(), categoryList);
        recyclerView.setAdapter(categrayAdapter);
        search = categoryFragment.findViewById(R.id.search_field);

        categrayAdapter.setOnItemClickListener(new CategrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                changeItem(position);
                Log.d("TAG cliksfsdfas"+position, "onItemClick: ");
                Intent intent;

                    Fragment fragment = new CategoryList();
                    FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putInt("item", position);
                    fragment.setArguments(bundle);
                    transaction.replace(R.id.frame_container, fragment);
                    transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        transaction.addToBackStack(null);
                    transaction.commit();
            }
        });
        

//        search.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//                // you can call or do what you want with your EditText here
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                initSearch(search.getText().toString());
//            }
//        });

        return categoryFragment;
    }
}
