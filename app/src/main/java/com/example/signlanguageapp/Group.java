package com.example.signlanguageapp;

import java.util.ArrayList;
import java.util.List;

public class Group {

    public final List<String> children = new ArrayList<String>();
    public String string;

    public Group(String string) {
        this.string = string;
    }

}