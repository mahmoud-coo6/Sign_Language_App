package com.example.signlanguageapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends
        SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<SliderItem> mSliderItems = new ArrayList<>();

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<SliderItem> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderItem sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.sign_language_intro, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(final SliderAdapterVH viewHolder, final int position) {

        SliderItem sliderItem = mSliderItems.get(position);

        viewHolder.textViewDescription.setText(sliderItem.getDescription());
        viewHolder.textViewTitle.setText(sliderItem.getTitle());
        viewHolder.imageGifContainer.setImageResource(sliderItem.getImageUrl());

        if (position == 3) {
            viewHolder.buttonSkip.setText("Skip");
            viewHolder.buttonSkip.setVisibility(View.VISIBLE);
        } else {
            viewHolder.buttonSkip.setVisibility(View.GONE);
        }

        viewHolder.buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                view.getContext().startActivity(intent);
            }
        });


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getCount() {

        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;

        ImageView imageGifContainer;
        TextView textViewDescription;
        TextView textViewTitle;
        Button buttonSkip;

        public SliderAdapterVH(View itemView) {
            super(itemView);

            imageGifContainer = itemView.findViewById(R.id.logo);
            textViewDescription = itemView.findViewById(R.id.description);
            textViewTitle = itemView.findViewById(R.id.title);
            buttonSkip = itemView.findViewById(R.id.skip);
            this.itemView = itemView;
        }
    }

}