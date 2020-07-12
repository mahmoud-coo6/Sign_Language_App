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
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Category extends Fragment {

    CategrayAdapter categrayAdapter;
    EditText search;
//    List<CategoryItem> categoryList = new ArrayList<>();
    RecyclerView recyclerView;
//    ArrayList<String> content = new ArrayList<>();
    CategrayAdapter.OnItemClickListener listener;
    private View categoryFragment;
    private Toolbar toolbar;
    FloatingActionButton fab;
    FirebaseUser currentUser;

    DatabaseReference mDatabaseRef;
    List<Upload> mUploads;
    ProgressBar mProgressCircle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        categoryFragment = inflater.inflate(R.layout.category, container, false);
        toolbar = categoryFragment.findViewById(R.id.toolbar);
        fab = categoryFragment.findViewById(R.id.fab);

        mProgressCircle= categoryFragment.findViewById(R.id.progress_circle);

        mUploads= new ArrayList<>();
        mUploads.clear();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads");

        int columns = 2;
        recyclerView = categoryFragment.findViewById(R.id.category_rv);
        LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), columns);
        recyclerView.setLayoutManager(layoutManager);


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapShot: snapshot.getChildren()){
                    Upload upload= postSnapShot.getValue(Upload.class);
                    if (upload.getName()!= null && upload.getName().trim().length() != 0)
                    mUploads.add(upload);
                }
                Log.d("ksfkjskf",mUploads.size()+"");
                categrayAdapter= new CategrayAdapter(getActivity(), mUploads);
                categrayAdapter.setOnItemClickListener(listener);
                recyclerView.setAdapter(categrayAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
                categrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

        listener = new CategrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("TAG cliksfsdfas" + position, "onItemClick: ");
                Intent intent;

                Fragment fragment = new CategoryList();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("item", position);
                bundle.putString("name", mUploads.get(position).getName());
                bundle.putString("image", mUploads.get(position).getImageUrl());
                fragment.setArguments(bundle);
                transaction.replace(R.id.frame_container, fragment);
                transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

                transaction.commit();
            }
        };

        currentUser = MyFirebaseController.getCurrentUserId();

        if (currentUser != null && isAdmin(currentUser.getEmail())) {

            fab.setVisibility(View.VISIBLE);
        }else{
            fab.setVisibility(View.GONE);
        }



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, new AddCategory());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

//        int columns = 2;
//        recyclerView = categoryFragment.findViewById(R.id.category_rv);
//        LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), columns);
//        recyclerView.setLayoutManager(layoutManager);
//        content.add("Alphaptic");
//        content.add("Number");
//        categoryList.add(new CategoryItem("Alphaptic", R.drawable.ic_alphabet));
//        categoryList.add(new CategoryItem("Number", R.drawable.ic_number));
//        categrayAdapter = new CategrayAdapter(getActivity(), categoryList);
//        categrayAdapter.setOnItemClickListener(listener);
//        recyclerView.setAdapter(categrayAdapter);
        search = categoryFragment.findViewById(R.id.search_field);
        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

//                filter(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });


        return categoryFragment;
    }


    public static boolean isAdmin(String emailAddress) {
        String expression = "^[\\w.+\\-]+@admin\\.com$";
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

//    private void filter(String text) {
//
//        text = text.toLowerCase();
//        boolean found0 = false, found1 = false;
//
//
//        if (text.isEmpty()) {
//            createData();
//
//        } else {
//
//
//            if ("Alphaptic".toLowerCase().contains(text.toLowerCase())) {
//                found0 = true;
//
//            }
//            if ("Number".toLowerCase().contains(text.toLowerCase())) {
//                found1 = true;
//
//            }
//
//            if ((found0 && found1)) {
//                createData();
//            } else if (found0) {
//                createData(0);
//            } else if (found1) {
//                createData(1);
//            } else {
//                createData();
//
//            }
//
//
//        }
//
//
//        categrayAdapter.notifyDataSetChanged();
//
//    }

//    public void createData() {
//        recyclerView = categoryFragment.findViewById(R.id.category_rv);
//        LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
//        recyclerView.setLayoutManager(layoutManager);
//        categoryList.clear();
//
//
//        categoryList.add(new CategoryItem("Alphaptic", R.drawable.ic_alphabet));
//        categoryList.add(new CategoryItem("Number", R.drawable.ic_number));
//        categrayAdapter = new CategrayAdapter(getActivity(), categoryList);
//        categrayAdapter.setOnItemClickListener(listener);
//        recyclerView.setAdapter(categrayAdapter);
//        categrayAdapter.notifyDataSetChanged();
//    }
//
//    public void createData(int type) {
//        recyclerView = categoryFragment.findViewById(R.id.category_rv);
//        LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
//        recyclerView.setLayoutManager(layoutManager);
//        categoryList.clear();
//        if (type == 0) {
//            categoryList.add(new CategoryItem("Alphaptic", R.drawable.ic_alphabet));
//        } else if (type == 1) {
//            categoryList.add(new CategoryItem("Number", R.drawable.ic_number));
//        }
//
//        categrayAdapter = new CategrayAdapter(getActivity(), categoryList);
//        categrayAdapter.setOnItemClickListener(listener);
//        recyclerView.setAdapter(categrayAdapter);
//        categrayAdapter.notifyDataSetChanged();
//    }
}
