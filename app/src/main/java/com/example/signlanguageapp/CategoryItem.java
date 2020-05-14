package com.example.signlanguageapp;

public class CategoryItem {

    String Title;
    int Image;

    public CategoryItem() {
    }

    public CategoryItem(String title, int image) {
        Title = title;
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
