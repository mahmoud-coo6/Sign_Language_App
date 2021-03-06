package com.example.signlanguageapp;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SoundTextAdapter extends RecyclerView.Adapter<SoundTextAdapter.CategoryVh> {


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


            sound_text_name.setText(soundTextList.get(position));
            sound_text_name.setTag(position);


            holder.itemCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int num = Integer.parseInt(sound_text_name.getTag().toString());
                    prevoise = num;


                    sound_text_name.setBackgroundTintMode(PorterDuff.Mode.ADD);
                    sound_text_name.setTextColor(ColorStateList.valueOf(Color.BLUE));
                    sound_text_name.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));


                }
            });

        }
    }
}
