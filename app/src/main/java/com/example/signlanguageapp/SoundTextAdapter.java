package com.example.signlanguageapp;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SoundTextAdapter extends RecyclerView.Adapter<SoundTextAdapter.CategoryVh> {
    //    public static final String CATEGORY_TRANSFER = "CATEGORY_TRANSFER";
//    public static final String CATEGORY_POSITION = "CATEGORY_POSITION";
    Context context;
    List<String> soundTextList;
    List<String> secondContainer;
    int prevoise = -1, current;
    AdapterView.OnItemClickListener mListener;

    public SoundTextAdapter(Context context, List<String> soundTextList) {
        this.context = context;
        this.soundTextList = soundTextList;
        secondContainer = soundTextList;
    }

    @NonNull
    @Override
    public CategoryVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sound_text_row, parent, false);

        return new CategoryVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryVh holder, int position) {
        holder.setData(soundTextList.get(position), position, holder);
    }

    @Override
    public int getItemCount() {
        return soundTextList.size();
    }


    class CategoryVh extends RecyclerView.ViewHolder {
        TextView sound_text_name;
        View itemCard;

        public CategoryVh(@NonNull View itemView) {
            super(itemView);
            sound_text_name = itemView.findViewById(R.id.item);
            itemCard = itemView;
        }


        public void setData(final String item, final int position, final CategoryVh holder) {
//            VectorChildFinder vector = new VectorChildFinder(context, R.drawable.ic_links_notebook, category_image);
//            VectorDrawableCompat.VFullPath path1 = vector.findPathByName("path1");
//            path1.setFillColor(category.getColor());
            sound_text_name.setText(soundTextList.get(position));
            sound_text_name.setTag(position);
//            if (position == prevoise && prevoise == -1){
//                sound_text_name.setBackgroundTintMode(PorterDuff.Mode.ADD);
//                sound_text_name.setTextColor(ColorStateList.valueOf(Color.BLACK));
//                sound_text_name.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
//            }
//            else {
//                sound_text_name.setBackgroundTintMode(PorterDuff.Mode.ADD);
//                sound_text_name.setTextColor(ColorStateList.valueOf(Color.BLACK));
//                sound_text_name.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
//            }

            holder.itemCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   int num= Integer.parseInt(sound_text_name.getTag().toString()) ;
                    prevoise= num;

//                    getChildViewHolder(recyclerView.getChildAt(i))

//                    for(View tempItemView : itemViewList) {
//                        /** navigate through all the itemViews and change color
//                         of selected view to colorSelected and rest of the views to colorDefault **/
//                        if(itemViewList.get(myViewHolder.getAdapterPosition()) == tempItemView) {
//                            tempItemView.setBackgroundResource(R.color.colorSelected);
//                        }
//                        else{
//                            tempItemView.setBackgroundResource(R.color.colorDefault);
//                        }

                    sound_text_name.setBackgroundTintMode(PorterDuff.Mode.ADD);
                    sound_text_name.setTextColor(ColorStateList.valueOf(Color.BLUE));
                    sound_text_name.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));

//                    Log.d("TAG", "onClick: " +num);
//                    if (prevoise != -1 && prevoise == num){
//                        Log.d("TAG", "onClick: " +prevoise);
//
//                        ((TextView)sound_text_name.getTag()).setBackgroundTintMode(PorterDuff.Mode.ADD);
//                        ((TextView)sound_text_name.getTag()).setTextColor(ColorStateList.valueOf(Color.BLACK));
//                        ((TextView)sound_text_name.getTag()).setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
//                    }

                }
            });

        }
    }
}
