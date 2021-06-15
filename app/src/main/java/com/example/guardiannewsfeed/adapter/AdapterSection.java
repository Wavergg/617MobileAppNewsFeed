package com.example.guardiannewsfeed.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
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
import com.example.guardiannewsfeed.models.Section;

import java.util.List;

public class AdapterSection extends ArrayAdapter<Section> {

    public AdapterSection(@NonNull Context context, @NonNull List<Section> sections) {
        super(context, 0, sections);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.listview_sectionitem, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        Section currentSection = getItem(position);


        TextView textViewSectionTitle = listItemView.findViewById(R.id.textView_sectiontitle);
        ImageView imageViewSectionIcon = listItemView.findViewById(R.id.imageView_sectionimage);


        textViewSectionTitle.setText(currentSection.getmTitle());
        imageViewSectionIcon.setImageResource(currentSection.getmIcon());



        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NewsActivity.class);
                intent.putExtra("sectionName",currentSection.getmTitle());

                getContext().startActivity(intent);
            }
        });

        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }

}
