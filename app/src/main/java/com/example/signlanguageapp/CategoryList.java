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
    String name, image;
    ImageView back;
    DatabaseReference mDatabaseRef;
    List<Upload> mUploads;
    ExpandableListView listView;
    ProgressBar progressBar;
    TextView textView;
    FloatingActionButton fab;
    FirebaseUser currentUser;
    private View categoryListFragment;
    private Toolbar toolbar;

    public static boolean isAdmin(String emailAddress) {
        String expression = "^[\\w.+\\-]+@admin\\.com$";
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        categoryListFragment = inflater.inflate(R.layout.category_list, container, false);

        toolbar = categoryListFragment.findViewById(R.id.toolbar);
        back = categoryListFragment.findViewById(R.id.back);
        progressBar = categoryListFragment.findViewById(R.id.progress_circle);
        textView = categoryListFragment.findViewById(R.id.text);
        fab = categoryListFragment.findViewById(R.id.fab);

        mUploads = new ArrayList<>();
        mUploads.clear();

//        getData();

        listView = categoryListFragment.findViewById(R.id.dictionary_elv);

        currentUser = MyFirebaseController.getCurrentUserId();

        if (currentUser != null && isAdmin(currentUser.getEmail())) {

            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.GONE);
        }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            type = bundle.getInt("item", 0);
            name = bundle.getString("name");
            image = bundle.getString("image");
            Log.d("postitiondfddff: " + type, "name: " + name);
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads/" + name);

            getData();

        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Category();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, fragment);
//                transaction.addToBackStack(null);
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
//                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


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

    private void getData() {


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUploads.clear();
                Group group;


                for (DataSnapshot postSnapShot : snapshot.getChildren()) {
                    Upload upload = postSnapShot.getValue(Upload.class);
                    mUploads.add(upload);

                }

                if (mUploads.size() > 0) {

                    group = new Group(image, name);

                    group.children.addAll(mUploads);

                    groups.append(0, group);
                    Log.d("jjdjdfjt", mUploads.size() + "");

                    adapter = new MyExpandableListAdapter(getActivity(), groups);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    textView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.INVISIBLE);
                } else {

                    group = new Group(image, name);


                    groups.append(0, group);
                    Log.d("jfgjfgjf", mUploads.size() + "");

                    adapter = new MyExpandableListAdapter(getActivity(), groups);
                    listView.setAdapter(adapter);
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

    private void getData(final String text) {


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUploads.clear();
                Group group;


                for (DataSnapshot postSnapShot : snapshot.getChildren()) {
                    Upload upload = postSnapShot.getValue(Upload.class);
                    if (upload.getName() != null && (upload.getName().toLowerCase()).contains(text.toLowerCase())) {
                        mUploads.add(upload);


                    }
                }


                if (mUploads.size() > 0) {


                    group = new Group(image, name);

                    group.children.addAll(mUploads);

                    groups.append(0, group);
                    Log.d("sdgsdgsdg", mUploads.size() + "");

                    adapter = new MyExpandableListAdapter(getActivity(), groups);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    textView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.INVISIBLE);


                } else {


                    group = new Group(image, name);


                    groups.append(0, group);
                    Log.d("jfgjfgjf", mUploads.size() + "");

                    adapter = new MyExpandableListAdapter(getActivity(), groups);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "there is no item/s found in " + name, Toast.LENGTH_SHORT).show();
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


    private void filter(String text) {

        text = text.toLowerCase();
        Log.v("MyListAdapter", String.valueOf(groups.size()));

        if (text.isEmpty()) {


            getData();
        } else {
            getData(text);


        }
    }


}