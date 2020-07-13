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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CategoryList extends Fragment {
    MyExpandableListAdapter adapter;
    EditText search;
    List<CategoryItem> categoryList = new ArrayList<>();
    SparseArray<Group> groups = new SparseArray<Group>();
    List<String> alphapticArray = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
    List<String> numberArray = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    Integer type;
    String name,image;
    ImageView back;
    private View categoryListFragment;
    private Toolbar toolbar;
    DatabaseReference mDatabaseRef;
    List<Upload> mUploads;
    ExpandableListView listView;
    ProgressBar progressBar;
    TextView textView;
    FloatingActionButton fab;
    FirebaseUser currentUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        categoryListFragment = inflater.inflate(R.layout.category_list, container, false);

        toolbar = categoryListFragment.findViewById(R.id.toolbar);
        back = categoryListFragment.findViewById(R.id.back);
        progressBar = categoryListFragment.findViewById(R.id.progress_circle);
        textView = categoryListFragment.findViewById(R.id.text);
        fab = categoryListFragment.findViewById(R.id.fab);

        mUploads= new ArrayList<>();
        mUploads.clear();
//        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads");

         listView = categoryListFragment.findViewById(R.id.dictionary_elv);

        currentUser = MyFirebaseController.getCurrentUserId();

        if (currentUser != null && isAdmin(currentUser.getEmail())) {

            fab.setVisibility(View.VISIBLE);
        }else{
            fab.setVisibility(View.GONE);
        }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            type = bundle.getInt("item", 0);
            name = bundle.getString("name");
            image = bundle.getString("image");
            Log.d("postitiondfddff: "+type,"name: "+name);
            mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads/"+name);

            getData();

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddCategoryItem();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("item", type);
                bundle.putString("name", name);
                fragment.setArguments(bundle);
                transaction.replace(R.id.frame_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


//        createData();
//        ExpandableListView listView = categoryListFragment.findViewById(R.id.dictionary_elv);
//        adapter = new MyExpandableListAdapter(getActivity(), groups);
//        listView.setAdapter(adapter);

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

    public static boolean isAdmin(String emailAddress) {
        String expression = "^[\\w.+\\-]+@admin\\.com$";
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }


    private void getData() {
//        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads");
//        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads/"+name);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUploads.clear();
                Group group;

//                    for (int i = 0; i < numberArray.size(); i++) {
//                        group.children.add(numberArray.get(i));
//                    }
//                }
//        groups.append(0, group);

                for (DataSnapshot postSnapShot: snapshot.getChildren()){
                    Upload upload= postSnapShot.getValue(Upload.class);
                    mUploads.add(upload);

                }

                if (mUploads.size() > 0){
//                        Log.d("posksksksfskfsd: "+type,"size: "+ mUploads.size());
                    group = new Group(image, name);

                    group.children.addAll(mUploads);

                    groups.append(0, group);
                    Log.d("jjdjdfjt",mUploads.size()+"");

                    adapter = new MyExpandableListAdapter(getActivity(), groups);
                    listView.setAdapter(adapter);
//                    recyclerView.setAdapter(categrayAdapter);
//                        listView.notify();
                    textView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.INVISIBLE);
                }else{

                    group = new Group(image, name);

//                    group.children.addAll(mUploads);

                    groups.append(0, group);
                    Log.d("jfgjfgjf",mUploads.size()+"");

                    adapter = new MyExpandableListAdapter(getActivity(), groups);
                    listView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);

                }

//                    categrayAdapter= new CategrayAdapter(getActivity(), mUploads);
//                    categrayAdapter.setOnItemClickListener(listener);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void getData(final String text) {
//        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads");
//        mUploads= new ArrayList<>();
//        mUploads.clear();
//        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads");
//
//        int columns = 2;
//        recyclerView = categoryFragment.findViewById(R.id.category_rv);
//        LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), columns);
//        recyclerView.setLayoutManager(layoutManager);

//        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads/"+name);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUploads.clear();
                Group group;

//                    for (int i = 0; i < numberArray.size(); i++) {
//                        group.children.add(numberArray.get(i));
//                    }
//                }
//        groups.append(0, group);

                for (DataSnapshot postSnapShot: snapshot.getChildren()){
                    Upload upload= postSnapShot.getValue(Upload.class);
                    if (upload.getName()!= null && upload.getName().contains(text)) {
                        mUploads.add(upload);
//                        Log.d("magjsgjsdgjd: "+upload.getName(),"image: "+upload.getImageUrl());

                    }
                }



                if (mUploads.size() > 0){
//                        Log.d("posksksksfskfsd: "+type,"size: "+ mUploads.size());
//                    mUploads.clear();
                    group = new Group(image, name);
//
                    group.children.addAll(mUploads);

                    groups.append(0, group);
                    Log.d("sdgsdgsdg",mUploads.size()+"");

                    adapter = new MyExpandableListAdapter(getActivity(), groups);
                    listView.setAdapter(adapter);
//                    recyclerView.setAdapter(categrayAdapter);
//                        listView.notify();
                    textView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.INVISIBLE);
//                    recyclerView.setAdapter(categrayAdapter);
//                        listView.notify();
//                    progressBar.setVisibility(View.INVISIBLE);
//                    textView.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.INVISIBLE);

                }else{
//                    textView.setVisibility(View.VISIBLE);

//                    mUploads.clear();
//                    listView.removeAllViews();

                    group = new Group(image, name);

//                    group.children.addAll(mUploads);

                    groups.append(0, group);
                    Log.d("jfgjfgjf",mUploads.size()+"");

                    adapter = new MyExpandableListAdapter(getActivity(), groups);
                    listView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);
                }

//                    categrayAdapter= new CategrayAdapter(getActivity(), mUploads);
//                    categrayAdapter.setOnItemClickListener(listener);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });



//
//
//
//        mDatabaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mUploads.clear();
//                for (DataSnapshot postSnapShot: snapshot.getChildren()){
//                    Upload upload= postSnapShot.getValue(Upload.class);
////                    if (upload != null && upload.getName().contains(text))
//                    if (upload.getName()!= null && upload.getName().contains(text))
//                        mUploads.add(upload);
//                }
//                Log.d("ksfkjskf",mUploads.size()+"");
//                adapter= new MyExpandableListAdapter(getActivity(), mUploads);
//                categrayAdapter.setOnItemClickListener(listener);
//                recyclerView.setAdapter(categrayAdapter);
//                mProgressCircle.setVisibility(View.INVISIBLE);
//                categrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                mProgressCircle.setVisibility(View.INVISIBLE);
//            }
//        });


    }


    private void filter(String text) {

        text = text.toLowerCase();
        Log.v("MyListAdapter", String.valueOf(groups.size()));

        if (text.isEmpty()) {
//            createData();
//            adapter.notifyDataSetChanged();

            getData();
        } else {
            getData(text);
//            ArrayList<String> items = new ArrayList<>();
//            Group group;
//            if (type == 0) {
//                group = new Group("Alphaptic ");
//                for (int i = 0; i < alphapticArray.size(); i++) {
//                    if (alphapticArray.get(i).toLowerCase().contains(text.toLowerCase())) {
//                        group.children.add(alphapticArray.get(i));
//                        items.add(alphapticArray.get(i));
//                    }
//                }
//            } else {
//                group = new Group("Number ");
//
//                for (int i = 0; i < numberArray.size(); i++) {
//                    if (numberArray.get(i).contains(text)) {
//                        group.children.add(numberArray.get(i));
//                        items.add(numberArray.get(i));
//                    }
//                }
//            }
//
//            if (items.isEmpty()) {
//                createData();
//            } else {
//                groups.append(0, group);
//            }
//            adapter.notifyDataSetChanged();


        }
    }

//
//    public void createData() {
//        Group group;
//        if (type == 0) {
//
//            group = new Group("Alphaptic ");
//            for (int i = 0; i < alphapticArray.size(); i++) {
//                group.children.add(alphapticArray.get(i));
//            }
//        } else {
//
//            group = new Group("Number ");
//            for (int i = 0; i < numberArray.size(); i++) {
//                group.children.add(numberArray.get(i));
//            }
//        }
//        groups.append(0, group);
//    }

}