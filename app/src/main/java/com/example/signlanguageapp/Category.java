package com.example.signlanguageapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
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
    RecyclerView recyclerView;
    ArrayList<String> content = new ArrayList<>();
    CategrayAdapter.OnItemClickListener listener;
    private View categoryFragment;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        categoryFragment = inflater.inflate(R.layout.category, container, false);
        toolbar = categoryFragment.findViewById(R.id.toolbar);

        listener = new CategrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("TAG cliksfsdfas" + position, "onItemClick: ");
                Intent intent;

                Fragment fragment = new CategoryList();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("item", position);
                fragment.setArguments(bundle);
                transaction.replace(R.id.frame_container, fragment);
                transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

                transaction.commit();
            }
        };

        int columns = 2;
        recyclerView = categoryFragment.findViewById(R.id.category_rv);
        LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), columns);
        recyclerView.setLayoutManager(layoutManager);
        content.add("Alphaptic");
        content.add("Number");
        categoryList.add(new CategoryItem("Alphaptic", R.drawable.ic_alphabet));
        categoryList.add(new CategoryItem("Number", R.drawable.ic_number));
        categrayAdapter = new CategrayAdapter(getActivity(), categoryList);
        categrayAdapter.setOnItemClickListener(listener);
        recyclerView.setAdapter(categrayAdapter);
        search = categoryFragment.findViewById(R.id.search_field);
        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                filter(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });


        return categoryFragment;
    }


    private void filter(String text) {

        text = text.toLowerCase();
        boolean found0 = false, found1 = false;


        if (text.isEmpty()) {
            createData();

        } else {


            if ("Alphaptic".toLowerCase().contains(text.toLowerCase())) {
                found0 = true;

            }
            if ("Number".toLowerCase().contains(text.toLowerCase())) {
                found1 = true;

            }

            if ((found0 && found1)) {
                createData();
            } else if (found0) {
                createData(0);
            } else if (found1) {
                createData(1);
            } else {
                createData();

            }


        }


        categrayAdapter.notifyDataSetChanged();

    }

    public void createData() {
        recyclerView = categoryFragment.findViewById(R.id.category_rv);
        LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        categoryList.clear();


        categoryList.add(new CategoryItem("Alphaptic", R.drawable.ic_alphabet));
        categoryList.add(new CategoryItem("Number", R.drawable.ic_number));
        categrayAdapter = new CategrayAdapter(getActivity(), categoryList);
        categrayAdapter.setOnItemClickListener(listener);
        recyclerView.setAdapter(categrayAdapter);
        categrayAdapter.notifyDataSetChanged();
    }

    public void createData(int type) {
        recyclerView = categoryFragment.findViewById(R.id.category_rv);
        LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        categoryList.clear();
        if (type == 0) {
            categoryList.add(new CategoryItem("Alphaptic", R.drawable.ic_alphabet));
        } else if (type == 1) {
            categoryList.add(new CategoryItem("Number", R.drawable.ic_number));
        }

        categrayAdapter = new CategrayAdapter(getActivity(), categoryList);
        categrayAdapter.setOnItemClickListener(listener);
        recyclerView.setAdapter(categrayAdapter);
        categrayAdapter.notifyDataSetChanged();
    }
}
