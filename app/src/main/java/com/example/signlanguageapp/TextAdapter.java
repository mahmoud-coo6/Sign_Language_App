package com.example.signlanguageapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.CategoryVh> {


    Context context;
    List<String> soundTextList;


    private OnItemClickListener mListener;

    public TextAdapter(Context context, List<String> soundTextList) {
        this.context = context;
        this.soundTextList = soundTextList;

    }

    public void setOnItemClickListener(TextAdapter.OnItemClickListener listener) {
        mListener = listener;
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

    public interface OnItemClickListener {
        void onItemClick(int position);
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
