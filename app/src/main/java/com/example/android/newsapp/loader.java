package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class loader extends AsyncTaskLoader<List<story>> {
    private String url;

    public loader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<story> loadInBackground() {
        if (url == null) {
            return null;
        }
        return com.example.android.newsapp.Query.fetchArticleData(url);

    }
}
