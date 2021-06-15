package com.example.guardiannewsfeed.models;

public class Section {
    private String mTitle;
    private int mIcon;

    public Section(String mTitle, int mIcon) {
        this.mTitle = mTitle;
        this.mIcon = mIcon;
    }

    public String getmTitle() {
        return mTitle;
    }

    public int getmIcon() {
        return mIcon;
    }
}
