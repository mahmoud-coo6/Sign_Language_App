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
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dictionary extends Fragment {
    MyExpandableListAdapter myadapter;
    EditText search;
    List<CategoryItem> dictionaryList = new ArrayList<>();
    SparseArray<Group> groups = new SparseArray<Group>();
    List<String> alphapticArray = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
    List<String> numberArray = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    private View dictionaryFragment;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dictionaryFragment = inflater.inflate(R.layout.dictionary, container, false);

        toolbar = dictionaryFragment.findViewById(R.id.toolbar);

        createData();
        ExpandableListView listView = dictionaryFragment.findViewById(R.id.dictionary_elv);
        myadapter = new MyExpandableListAdapter(getActivity(), groups);
        listView.setAdapter(myadapter);


        search = dictionaryFragment.findViewById(R.id.search_field);
        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                filter(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        return dictionaryFragment;
    }

    private void filter(String text) {

        text = text.toLowerCase();
        Toast.makeText(getActivity(), "value: " + text, Toast.LENGTH_SHORT).show();
        Log.v("MyListAdapter", String.valueOf(groups.size()));

        if (text.isEmpty()) {
            createData();
            myadapter.notifyDataSetChanged();
        } else {

            ArrayList<String> items = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                Group group;
                if (j == 0) {
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

                groups.append(j, group);
            }
            if (items.isEmpty()) {
                createData();
            }
            myadapter.notifyDataSetChanged();

        }
    }

    public void createData() {
        for (int j = 0; j < 2; j++) {
            Group group;
            if (j == 0) {
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

            groups.append(j, group);
        }
    }

}
