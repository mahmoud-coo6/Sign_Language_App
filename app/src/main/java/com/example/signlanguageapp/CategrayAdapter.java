package com.example.signlanguageapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CategrayAdapter extends RecyclerView.Adapter<CategrayAdapter.CategoryVh> {


    Context context;
//    List<CategoryItem> categoryList;
    private OnItemClickListener mListener;
    List<Upload> mUploads;

    public CategrayAdapter(Context context, List<Upload> uploads) {
        this.context = context;
        this.mUploads = uploads;
    }

//    public CategrayAdapter(Context context, List<CategoryItem> categoryList) {
//        this.context = context;
//        this.categoryList = categoryList;
//    }

    public void setOnItemClickListener(CategrayAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public CategoryVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_raw, parent, false);

        return new CategoryVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryVh holder, int position) {
        holder.setData(mUploads.get(position), position, holder);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class CategoryVh extends RecyclerView.ViewHolder {
        TextView category_name;
        ImageView category_image;
        View itemCard;

        public CategoryVh(@NonNull View itemView) {
            super(itemView);
            category_name = itemView.findViewById(R.id.category_name);
            category_image = itemView.findViewById(R.id.category_image);
            itemCard = itemView;
        }

        public void setData(final Upload category, final int position, CategoryVh holder) {


            category_name.setText(mUploads.get(position).getName());
//            category_image.setImageResource(mUploads.get(position).getImageUrl());
            Picasso.get().load(mUploads.get(position).getImageUrl()).resize(250, 250).centerCrop().error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder).into(category_image);

            holder.itemCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mListener != null) {

                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }


                }
            });

        }
    }
}
