package com.example.signlanguageapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private final SparseArray<Group> groups;
    public LayoutInflater inflater;
    public Activity activity;
    List<Upload> mUploads;


    public MyExpandableListAdapter(Activity act, SparseArray<Group> groups) {
        activity = act;
        this.groups = groups;
        inflater = act.getLayoutInflater();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).children.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
//        final String children = (String) getChild(groupPosition, childPosition);
        final Upload children = (Upload) getChild(groupPosition, childPosition);
        TextView text = null;
        ImageView imageView = null;
        String image;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_details, null);
        }
        text = convertView.findViewById(R.id.textView1);
        imageView = convertView.findViewById(R.id.image);
        text.setText(children.getName());
//        image = "https://res.cloudinary.com/dwpo5xilm/image/upload/v1582724492/sick-fits/" + children + ".png";
        Picasso.get().load(children.getImageUrl()).error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder).into(imageView);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putString("result", children.getName());
                SearchResult searchResult = new SearchResult();
                searchResult.setArguments(bundle);
                FragmentTransaction transaction = ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction();


                transaction.replace(R.id.frame_container, searchResult);
                transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).children.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_group, null);
        }
        Group group = (Group) getGroup(groupPosition);
        ((CheckedTextView) convertView).setText(group.name);

        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}