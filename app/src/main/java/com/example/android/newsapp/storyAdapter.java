package com.example.android.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class storyAdapter extends ArrayAdapter<story> {
    public storyAdapter(Context context, List<story> articles) {

        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView== null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_story, parent, false);
        }
        story currentArticle = getItem(position);

        TextView sectionView = (TextView) convertView.findViewById(R.id.section);
        sectionView.setText(currentArticle.getSection());


        TextView dateView = (TextView) convertView.findViewById(R.id.date);
        dateView.setText(currentArticle.getDate());

        TextView titleView = (TextView) convertView.findViewById(R.id.title);
        titleView.setText(currentArticle.getTitle());

        TextView authorView = (TextView) convertView.findViewById(R.id.author);
        authorView.setText(currentArticle.getAuthor());

        return convertView;
    }

}

