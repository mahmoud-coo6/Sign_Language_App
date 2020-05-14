package com.example.signlanguageapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategrayAdapter extends RecyclerView.Adapter<CategrayAdapter.CategoryVh> {
    //    public static final String CATEGORY_TRANSFER = "CATEGORY_TRANSFER";
//    public static final String CATEGORY_POSITION = "CATEGORY_POSITION";
    Context context;
    List<CategoryItem> categoryList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(CategrayAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public CategrayAdapter(Context context, List<CategoryItem> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_raw, parent, false);

        return new CategoryVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryVh holder, int position) {
        holder.setData(categoryList.get(position), position, holder);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
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

        public void setData(final CategoryItem category, final int position, CategoryVh holder) {
//            VectorChildFinder vector = new VectorChildFinder(context, R.drawable.ic_links_notebook, category_image);
//            VectorDrawableCompat.VFullPath path1 = vector.findPathByName("path1");
//            path1.setFillColor(category.getColor());
//            category_name.setText(category.getTitle());
            category_name.setText(categoryList.get(position).getTitle());
            category_image.setImageResource(categoryList.get(position).getImage());

            holder.itemCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mListener != null) {
//                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
//                    Intent intent = new Intent(v.getContext(), CategoryNote.class);
//                    intent.putParcelableArrayListExtra(CATEGORY_TRANSFER, (ArrayList) categoryList);
//                    intent.putExtra(CATEGORY_POSITION, position);
//                    context.startActivity(intent);
                }
            });

        }
    }
}
