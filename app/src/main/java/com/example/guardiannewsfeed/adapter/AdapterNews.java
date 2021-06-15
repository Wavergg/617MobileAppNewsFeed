package com.example.guardiannewsfeed.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.guardiannewsfeed.NewsActivity;
import com.example.guardiannewsfeed.R;
import com.example.guardiannewsfeed.models.News;
import com.example.guardiannewsfeed.models.Section;

import java.util.List;

public class AdapterNews extends ArrayAdapter<News> {

    public AdapterNews(@NonNull Context context, @NonNull List<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.listview_newsitem, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        News currentNews = getItem(position);


        TextView textViewNewsTitle = listItemView.findViewById(R.id.textView_newstitle);
        TextView textViewNewsDate = listItemView.findViewById(R.id.textView_newsdate);


        textViewNewsTitle.setText(currentNews.getmTitle());
        textViewNewsDate.setText(currentNews.getmDate());



        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentNews.getmUrl()));
                getContext().startActivity(intent);
            }
        });

        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }

}
