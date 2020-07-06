package com.example.signlanguageapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryList extends Fragment {
    MyExpandableListAdapter adapter;
    EditText search;
    List<CategoryItem> categoryList = new ArrayList<>();
    SparseArray<Group> groups = new SparseArray<Group>();
    List<String> alphapticArray = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
    List<String> numberArray = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    Integer type;

    ImageView back;
    private View categoryListFragment;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        categoryListFragment = inflater.inflate(R.layout.category_list, container, false);

        toolbar = categoryListFragment.findViewById(R.id.toolbar);
        back = categoryListFragment.findViewById(R.id.back);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            type = bundle.getInt("item", 0);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Category();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, fragment);
                transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

                transaction.commit();
            }
        });


        createData();
        ExpandableListView listView = categoryListFragment.findViewById(R.id.dictionary_elv);
        adapter = new MyExpandableListAdapter(getActivity(), groups);
        listView.setAdapter(adapter);

        search = categoryListFragment.findViewById(R.id.search_field);
        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                filter(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        return categoryListFragment;
    }

    private void filter(String text) {

        text = text.toLowerCase();
        Log.v("MyListAdapter", String.valueOf(groups.size()));

        if (text.isEmpty()) {
            createData();
            adapter.notifyDataSetChanged();
        } else {

            ArrayList<String> items = new ArrayList<>();
            Group group;
            if (type == 0) {
                group = new Group("Alphaptic ");
                for (int i = 0; i < alphapticArray.size(); i++) {
                    if (alphapticArray.get(i).toLowerCase().contains(text.toLowerCase())) {
                        group.children.add(alphapticArray.get(i));
                        items.add(alphapticArray.get(i));
                    }
                }
            } else {
                group = new Group("Number ");

                for (int i = 0; i < numberArray.size(); i++) {
                    if (numberArray.get(i).contains(text)) {
                        group.children.add(numberArray.get(i));
                        items.add(numberArray.get(i));
                    }
                }
            }

            if (items.isEmpty()) {
                createData();
            } else {
                groups.append(0, group);
            }
            adapter.notifyDataSetChanged();

        }
    }


    public void createData() {
        Group group;
        if (type == 0) {

            group = new Group("Alphaptic ");
            for (int i = 0; i < alphapticArray.size(); i++) {
                group.children.add(alphapticArray.get(i));
            }
        } else {

            group = new Group("Number ");
            for (int i = 0; i < numberArray.size(); i++) {
                group.children.add(numberArray.get(i));
            }
        }
        groups.append(0, group);
    }

}