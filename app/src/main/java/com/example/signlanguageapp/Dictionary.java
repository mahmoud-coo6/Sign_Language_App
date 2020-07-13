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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    ProgressBar progressBar;
    DatabaseReference mDatabaseRef, mDatabaseRef2;
    List<Upload> mUploads,mUploads2;
    ExpandableListView listView;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dictionaryFragment = inflater.inflate(R.layout.dictionary, container, false);

        toolbar = dictionaryFragment.findViewById(R.id.toolbar);
        progressBar = dictionaryFragment.findViewById(R.id.progress_circle);
        textView = dictionaryFragment.findViewById(R.id.text);

        mUploads= new ArrayList<>();
        mUploads2= new ArrayList<>();
        mUploads.clear();
        mUploads2.clear();

//        getList();
//        createData();
        listView = dictionaryFragment.findViewById(R.id.dictionary_elv);

        getItem();
//        myadapter = new MyExpandableListAdapter(getActivity(), groups);
//        listView.setAdapter(myadapter);


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

    public void getItem() {

        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapShot: snapshot.getChildren()){
                    Upload upload= postSnapShot.getValue(Upload.class);
                    if (upload.getName()!= null && upload.getName().trim().length() != 0)
                        mUploads.add(upload);
                }
                for (int i=0; i< mUploads.size(); i++) {
                    mDatabaseRef2= FirebaseDatabase.getInstance().getReference("uploads/"+mUploads.get(i).getName());

                    final int index = i;
                    mDatabaseRef2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            mUploads2.clear();
                            Group group;

                            for (DataSnapshot postSnapShot: snapshot.getChildren()){
                                Upload upload= postSnapShot.getValue(Upload.class);
                                mUploads2.add(upload);
                            }
                            if (mUploads2.size() > 0){
                                group = new Group(mUploads.get(index).getImageUrl(), mUploads.get(index).getName());
                                group.children.addAll(mUploads2);
                                groups.append(index, group);
                                Log.d("jjdjdfjt",mUploads2.size()+"");

                                myadapter = new MyExpandableListAdapter(getActivity(), groups);
                                listView.setAdapter(myadapter);
                                progressBar.setVisibility(View.INVISIBLE);
                                textView.setVisibility(View.GONE);
                            }else{

                                group = new Group(mUploads.get(index).getImageUrl(), mUploads.get(index).getName());
//                                group.children.addAll(mUploads2);
                                groups.append(index, group);
                                Log.d("jjdjdfjt",mUploads2.size()+"");

                                myadapter = new MyExpandableListAdapter(getActivity(), groups);
                                listView.setAdapter(myadapter);
//                                textView.setVisibility(View.GONE);
                                progressBar.setVisibility(View.INVISIBLE);
//                                textView.setVisibility(View.VISIBLE);
//                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
                Log.d("ksfkjskf",mUploads.size()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getItem(final String text) {

        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapShot: snapshot.getChildren()){
                    Upload upload= postSnapShot.getValue(Upload.class);
                    if (upload.getName()!= null && upload.getName().trim().length() != 0)
                        mUploads.add(upload);
                }
                for (int i=0; i< mUploads.size(); i++) {
                    mDatabaseRef2= FirebaseDatabase.getInstance().getReference("uploads/"+mUploads.get(i).getName());

                    final int index = i;
                    mDatabaseRef2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            mUploads2.clear();
                            Group group;

                            for (DataSnapshot postSnapShot: snapshot.getChildren()){
                                Upload upload= postSnapShot.getValue(Upload.class);
                                if (upload.getName()!= null && upload.getName().contains(text))
                                mUploads2.add(upload);
                            }
                            if (mUploads2.size() > 0){
                                group = new Group(mUploads.get(index).getImageUrl(), mUploads.get(index).getName());
                                group.children.addAll(mUploads2);
                                groups.append(index, group);
                                Log.d("jjdjdfjt",mUploads2.size()+"");

                                myadapter = new MyExpandableListAdapter(getActivity(), groups);
                                listView.setAdapter(myadapter);
//                                textView.setVisibility(View.GONE);
                                progressBar.setVisibility(View.INVISIBLE);
                            }else{
//                                group = new Group(mUploads.get(index).getImageUrl(), mUploads.get(index).getName());
////                                group.children.addAll(mUploads2);
//                                groups.append(index, group);
//                                Log.d("jjdjdfjt",mUploads2.size()+"");
//
//                                myadapter = new MyExpandableListAdapter(getActivity(), groups);

//                                textView.setVisibility(View.VISIBLE);
//                                listView.setVisibility(View.GONE);
//                                progressBar.setVisibility(View.INVISIBLE);

                                group = new Group(mUploads.get(index).getImageUrl(), mUploads.get(index).getName());
//                                group.children.addAll(mUploads2);
                                groups.append(index, group);
                                Log.d("jjdjdfjt",mUploads2.size()+"");

                                myadapter = new MyExpandableListAdapter(getActivity(), groups);
                                listView.setAdapter(myadapter);
//                                textView.setVisibility(View.GONE);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
                Log.d("ksfkjskf",mUploads.size()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filter(String text) {

        text = text.toLowerCase();
        Toast.makeText(getActivity(), "value: " + text, Toast.LENGTH_SHORT).show();
        Log.v("MyListAdapter", String.valueOf(groups.size()));

        if (text.isEmpty()) {
            getItem();
//            myadapter.notifyDataSetChanged();
        } else {

            getItem(text);
//            ArrayList<String> items = new ArrayList<>();
//            for (int j = 0; j < 2; j++) {
//                Group group;
//                if (j == 0) {
//                    group = new Group("Alphaptic ");
//                    for (int i = 0; i < alphapticArray.size(); i++) {
//                        if (alphapticArray.get(i).toLowerCase().contains(text.toLowerCase())) {
//                            group.children.add(alphapticArray.get(i));
//                            items.add(alphapticArray.get(i));
//                        }
//                    }
//                } else {
//                    group = new Group("Number ");
//                    for (int i = 0; i < numberArray.size(); i++) {
//                        if (numberArray.get(i).contains(text)) {
//                            group.children.add(numberArray.get(i));
//                            items.add(numberArray.get(i));
//                        }
//                    }
//                }
//
//                groups.append(j, group);
//            }
//            if (items.isEmpty()) {
//                createData();
//            }
//            myadapter.notifyDataSetChanged();

        }
    }
//
//    public void createData() {
//        for (int j = 0; j < 2; j++) {
//            Group group;
//            if (j == 0) {
//                group = new Group("Alphaptic ");
//                for (int i = 0; i < alphapticArray.size(); i++) {
//                    group.children.add(alphapticArray.get(i));
//                }
//            } else {
//                group = new Group("Number ");
//                for (int i = 0; i < numberArray.size(); i++) {
//                    group.children.add(numberArray.get(i));
//                }
//            }
//
//            groups.append(j, group);
//        }
//    }

}
