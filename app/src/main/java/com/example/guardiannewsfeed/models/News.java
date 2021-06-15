package com.example.guardiannewsfeed.models;

public class News {
    private String mTitle;
    private String mDate;
    private String mUrl;

    public News(String mTitle, String mDate, String mUrl) {
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mUrl = mUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmUrl() {
        return mUrl;
    }
}
