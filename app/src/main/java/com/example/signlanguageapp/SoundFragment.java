package com.example.signlanguageapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class SoundFragment extends Fragment {

    List<String> soundTextList;
    //    SoundTextAdapter soundTextAdapter;
    private View mySoundFragment;
    TextView textView, input;
    ImageView imageIcon,imageView, translate, back, forword;
    //    EditText editText;
    //    SoundTextAdapter soundTextAdapter;
    TextAdapter textAdapter;
//    private View myTextFragment;
    RecyclerView recyclerView;
    String imageUrl;
    TextView holder;
    int currentPosition;
    LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mySoundFragment = inflater.inflate(R.layout.input_sound, container, false);
        soundTextList = new ArrayList<>();
//        soundTextList.clear();
        linearLayout= mySoundFragment.findViewById(R.id.input2);
        linearLayout.setVisibility(View.GONE);
        input = mySoundFragment.findViewById(R.id.input);
        imageIcon = mySoundFragment.findViewById(R.id.text_input);
//        textView.setText("New Arrival");
        textView = mySoundFragment.findViewById(R.id.current_letter_text);
        imageView = mySoundFragment.findViewById(R.id.current_letter_image);
        back = mySoundFragment.findViewById(R.id.back_button);
        forword = mySoundFragment.findViewById(R.id.forword_button);
        back.setVisibility(View.INVISIBLE);
        forword.setVisibility(View.INVISIBLE);
        recyclerView = mySoundFragment.findViewById(R.id.sound_text_rv);
        imageView.setVisibility(View.GONE);

//        for (int i = 0; i < textView.getText().toString().length(); i++) {
//            soundTextList.add(String.valueOf(textView.getText().charAt(i)));
//        }
        RecyclerView recyclerView = mySoundFragment.findViewById(R.id.sound_text_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        soundTextAdapter = new SoundTextAdapter(getActivity(), soundTextList);
//        recyclerView.setAdapter(soundTextAdapter);
        textAdapter = new TextAdapter(getActivity(), soundTextList);
        recyclerView.setAdapter(textAdapter);

        imageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpeechInput(v);
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

        return mySoundFragment;
    }

    private void moveNext(View v) {
        currentPosition += 1;
        if (currentPosition <= soundTextList.size()-1){
            for (int i=0; i<= soundTextList.size()-1; i++) {
                if (currentPosition != i) {
                    holder = recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.item);
                    holder.setBackgroundTintMode(PorterDuff.Mode.ADD);
                    holder.setTextColor(ColorStateList.valueOf(Color.BLACK));
                    holder.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                }
            }

            textAdapter.notifyDataSetChanged();
            imageUrl= "https://res.cloudinary.com/dwpo5xilm/image/upload/v1582724492/sick-fits/"+String.valueOf(input.getText().charAt(currentPosition)).toUpperCase()+".png";
            Picasso.get().load(imageUrl).error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder).into(imageView);

            textView.setText(String.valueOf(input.getText().charAt(currentPosition)).toUpperCase());
            textAdapter.notifyDataSetChanged();

            recyclerView.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    if(recyclerView.findViewHolderForAdapterPosition(0)!=null )
                    {

                        holder = recyclerView.findViewHolderForAdapterPosition(currentPosition).itemView.findViewById(R.id.item);
                        holder.setBackgroundTintMode(PorterDuff.Mode.ADD);
                        holder.setTextColor(ColorStateList.valueOf(Color.BLUE));
                        holder.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));

                    }
                }
            },50);

            textAdapter.notifyItemChanged(currentPosition);
            Log.d("TAG", "changeItem: i="+soundTextList.size()+" position="+currentPosition);

        }

    }
    private void moveBack(View v) {
        currentPosition -= 1;
        if (currentPosition >= 0){
            for (int i=0; i<= soundTextList.size()-1; i++) {
                if (currentPosition != i) {
                    holder = recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.item);
                    holder.setBackgroundTintMode(PorterDuff.Mode.ADD);
                    holder.setTextColor(ColorStateList.valueOf(Color.BLACK));
                    holder.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                }
            }

            textAdapter.notifyDataSetChanged();
            imageUrl= "https://res.cloudinary.com/dwpo5xilm/image/upload/v1582724492/sick-fits/"+String.valueOf(input.getText().charAt(currentPosition)).toUpperCase()+".png";
            Picasso.get().load(imageUrl).error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder).into(imageView);

            textView.setText(String.valueOf(input.getText().charAt(currentPosition)).toUpperCase());
            textAdapter.notifyDataSetChanged();

            recyclerView.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    if(recyclerView.findViewHolderForAdapterPosition(0)!=null )
                    {

                        holder = recyclerView.findViewHolderForAdapterPosition(currentPosition).itemView.findViewById(R.id.item);
                        holder.setBackgroundTintMode(PorterDuff.Mode.ADD);
                        holder.setTextColor(ColorStateList.valueOf(Color.BLUE));
                        holder.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));

                    }
                }
            },50);

            textAdapter.notifyItemChanged(currentPosition);
            Log.d("TAG", "changeItem: i="+soundTextList.size()+" position="+currentPosition);

        }
    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(getActivity(), "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    linearLayout.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    input.setText(result.get(0));
                    soundTextList.clear();
                    for (int i = 0; i < input.getText().toString().length(); i++) {
                        soundTextList.add(String.valueOf(input.getText().charAt(i)));
                    }

                    back.setVisibility(View.VISIBLE);
                    forword.setVisibility(View.VISIBLE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    textAdapter = new TextAdapter(getActivity(),soundTextList);
                    recyclerView.setAdapter(textAdapter);

                    imageUrl= "https://res.cloudinary.com/dwpo5xilm/image/upload/v1582724492/sick-fits/"+String.valueOf(input.getText().charAt(0)).toUpperCase()+".png";
                    Picasso.get().load(imageUrl).error(R.drawable.placeholder)
                            .placeholder(R.drawable.placeholder).into(imageView);



                    recyclerView.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if(recyclerView.findViewHolderForAdapterPosition(0)!=null )
                            {

                                holder = recyclerView.findViewHolderForAdapterPosition(0).itemView.findViewById(R.id.item);
                                holder.setBackgroundTintMode(PorterDuff.Mode.ADD);
                                holder.setTextColor(ColorStateList.valueOf(Color.BLUE));
                                holder.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));

                            }
                        }
                    },50);

                    currentPosition =0;
                    textAdapter.notifyItemChanged(0);
                    textView.setText(String.valueOf(input.getText().charAt(0)).toUpperCase());

                    textAdapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            changeItem(position);
                        }
                    });
//                    for (int i = 0; i < textView.getText().toString().length(); i++) {
//                        soundTextList.add(String.valueOf(textView.getText().charAt(i)));
//                    }
//                    Toast.makeText(getActivity(), soundTextList.toString(), Toast.LENGTH_SHORT).show();
//                    textAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    public void changeItem(final int position) {

        currentPosition =position;

        for (int i=0; i<= soundTextList.size()-1; i++) {
            if (position != i) {
                holder = recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.item);
                holder.setBackgroundTintMode(PorterDuff.Mode.ADD);
                holder.setTextColor(ColorStateList.valueOf(Color.BLACK));
                holder.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            }
        }

        textAdapter.notifyDataSetChanged();

        imageUrl= "https://res.cloudinary.com/dwpo5xilm/image/upload/v1582724492/sick-fits/"+String.valueOf(input.getText().charAt(position)).toUpperCase()+".png";
        Picasso.get().load(imageUrl).error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder).into(imageView);

        textView.setText(String.valueOf(input.getText().charAt(position)).toUpperCase());

        Log.d("TAG changessfdf", "changeItem: i="+soundTextList.size()+" position="+position);
//        textAdapter.notifyDataSetChanged();
        recyclerView.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if(recyclerView.findViewHolderForAdapterPosition(0)!=null )
                {

                    holder = recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.item);
                    holder.setBackgroundTintMode(PorterDuff.Mode.ADD);
                    holder.setTextColor(ColorStateList.valueOf(Color.BLUE));
                    holder.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));

                }
            }
        },50);

        textAdapter.notifyItemChanged(position);

    }
}