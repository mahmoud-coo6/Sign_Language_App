package com.example.signlanguageapp;

import java.util.ArrayList;
import java.util.List;

public class Group {


    public final List<Upload> children = new ArrayList<>();
    public String imageUrl;
    public String name;

    public Group(String imageUrl, String name) {
        this.imageUrl = imageUrl;
        this.name = name;
    }

}