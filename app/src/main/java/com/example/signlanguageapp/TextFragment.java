package com.example.signlanguageapp;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TextFragment extends Fragment {

    ArrayList<String> textList;
    TextView textView;
    ImageView imageView, translate, back, forword;
    EditText editText;

    TextAdapter textAdapter;
    RecyclerView recyclerView;
    String imageUrl;
    TextView holder;
    int currentPosition;
    private View myTextFragment;

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();

        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myTextFragment = inflater.inflate(R.layout.input, container, false);
        textList = new ArrayList<>();
        editText = myTextFragment.findViewById(R.id.text_input);
        translate = myTextFragment.findViewById(R.id.translate);
        textView = myTextFragment.findViewById(R.id.current_letter_text);
        imageView = myTextFragment.findViewById(R.id.current_letter_image);
        back = myTextFragment.findViewById(R.id.back_button);
        forword = myTextFragment.findViewById(R.id.forword_button);
        back.setVisibility(View.INVISIBLE);
        forword.setVisibility(View.INVISIBLE);
        recyclerView = myTextFragment.findViewById(R.id.sound_text_rv);


        try {
            if (getArguments().getString("result") != null && getArguments().getString("result").trim().length() != 0) {
                translate.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);
                editText.setText(getArguments().getString("result"));
                getTextInput(textView);
            }
        } catch (Exception e) {

        }


        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard(getActivity());

                getTextInput(v);
            }
        });

        forword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveNext(v);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveBack(v);
            }
        });

        return myTextFragment;
    }

    private void moveNext(View v) {
        currentPosition += 1;
        if (currentPosition <= textList.size() - 1) {
            for (int i = 0; i <= textList.size() - 1; i++) {
                if (currentPosition != i) {
                    holder = recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.item);
                    holder.setBackgroundTintMode(PorterDuff.Mode.ADD);
                    holder.setTextColor(ColorStateList.valueOf(Color.BLACK));
                    holder.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                }
            }

            textAdapter.notifyDataSetChanged();
            imageUrl = "https://res.cloudinary.com/dwpo5xilm/image/upload/v1582724492/sick-fits/" + String.valueOf(editText.getText().charAt(currentPosition)).toUpperCase() + ".png";

            Picasso.get().load(imageUrl).error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder).into(imageView);

            textView.setText(String.valueOf(editText.getText().charAt(currentPosition)).toUpperCase());
            textAdapter.notifyDataSetChanged();

            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (recyclerView.findViewHolderForAdapterPosition(0) != null) {

                        holder = recyclerView.findViewHolderForAdapterPosition(currentPosition).itemView.findViewById(R.id.item);
                        holder.setBackgroundTintMode(PorterDuff.Mode.ADD);
                        holder.setTextColor(ColorStateList.valueOf(Color.BLUE));
                        holder.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));

                    }
                }
            }, 50);

            textAdapter.notifyItemChanged(currentPosition);
            Log.d("TAG", "changeItem: i=" + textList.size() + " position=" + currentPosition);

        }

    }

    private void moveBack(View v) {
        currentPosition -= 1;
        if (currentPosition >= 0) {
            for (int i = 0; i <= textList.size() - 1; i++) {
                if (currentPosition != i) {
                    holder = recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.item);
                    holder.setBackgroundTintMode(PorterDuff.Mode.ADD);
                    holder.setTextColor(ColorStateList.valueOf(Color.BLACK));
                    holder.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                }
            }

            textAdapter.notifyDataSetChanged();
            imageUrl = "https://res.cloudinary.com/dwpo5xilm/image/upload/v1582724492/sick-fits/" + String.valueOf(editText.getText().charAt(currentPosition)).toUpperCase() + ".png";
            try {
                Picasso.get().load(imageUrl).error(R.drawable.placeholder)
                        .placeholder(R.drawable.placeholder).into(imageView);
            } catch (Exception e) {

            }


            textView.setText(String.valueOf(editText.getText().charAt(currentPosition)).toUpperCase());
            textAdapter.notifyDataSetChanged();

            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (recyclerView.findViewHolderForAdapterPosition(0) != null) {

                        holder = recyclerView.findViewHolderForAdapterPosition(currentPosition).itemView.findViewById(R.id.item);
                        holder.setBackgroundTintMode(PorterDuff.Mode.ADD);
                        holder.setTextColor(ColorStateList.valueOf(Color.BLUE));
                        holder.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));

                    }
                }
            }, 50);

            textAdapter.notifyItemChanged(currentPosition);
            Log.d("TAG", "changeItem: i=" + textList.size() + " position=" + currentPosition);

        }
    }

    private void getTextInput(View v) {
        textList.clear();
        for (int i = 0; i < editText.getText().toString().length(); i++) {
            textList.add(String.valueOf(editText.getText().charAt(i)));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        textAdapter = new TextAdapter(getActivity(), textList);
        recyclerView.setAdapter(textAdapter);

        imageUrl = "https://res.cloudinary.com/dwpo5xilm/image/upload/v1582724492/sick-fits/" + String.valueOf(editText.getText().charAt(currentPosition)).toUpperCase() + ".png";
        Picasso.get().load(imageUrl).error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder).into(imageView);

        back.setVisibility(View.VISIBLE);
        forword.setVisibility(View.VISIBLE);

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (recyclerView.findViewHolderForAdapterPosition(0) != null) {

                    holder = recyclerView.findViewHolderForAdapterPosition(0).itemView.findViewById(R.id.item);
                    holder.setBackgroundTintMode(PorterDuff.Mode.ADD);
                    holder.setTextColor(ColorStateList.valueOf(Color.BLUE));
                    holder.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));

                }
            }
        }, 50);

        currentPosition = 0;
        textAdapter.notifyItemChanged(0);
        textView.setText(String.valueOf(editText.getText().charAt(0)).toUpperCase());

        textAdapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position);
            }
        });
    }

    public void changeItem(final int position) {

        currentPosition = position;

        for (int i = 0; i <= textList.size() - 1; i++) {
            if (position != i) {
                holder = recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.item);
                holder.setBackgroundTintMode(PorterDuff.Mode.ADD);
                holder.setTextColor(ColorStateList.valueOf(Color.BLACK));
                holder.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            }


        }

        textAdapter.notifyDataSetChanged();

        imageUrl = "https://res.cloudinary.com/dwpo5xilm/image/upload/v1582724492/sick-fits/" + String.valueOf(editText.getText().charAt(currentPosition)).toUpperCase() + ".png";
        Picasso.get().load(imageUrl).error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder).into(imageView);

        textView.setText(String.valueOf(editText.getText().charAt(position)).toUpperCase());

        Log.d("TAG changessfdf", "changeItem: i=" + textList.size() + " position=" + position);

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (recyclerView.findViewHolderForAdapterPosition(0) != null) {

                    holder = recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.item);
                    holder.setBackgroundTintMode(PorterDuff.Mode.ADD);
                    holder.setTextColor(ColorStateList.valueOf(Color.BLUE));
                    holder.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));

                }
            }
        }, 50);


        textAdapter.notifyItemChanged(position);

    }

}