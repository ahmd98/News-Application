package com.example.android.newsapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<story>>, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int LOADER_ID = 1;
    private static final String apiKey = "http://content.guardianapis.com/search?q=debates&api-key=test&show-tags=contributor";
    private TextView EmptyTextView;
    private storyAdapter adapterStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView ArticlesListView = (ListView) findViewById(R.id.list);

        EmptyTextView = (TextView) findViewById(R.id.empty_view);
        ArticlesListView.setEmptyView(EmptyTextView);

        adapterStory = new storyAdapter(this, new ArrayList<story>());
        ArticlesListView.setAdapter(adapterStory);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
        ArticlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                story currentArticle = adapterStory.getItem(position);
                Uri URI = Uri.parse(currentArticle.getWebUrl());
                Intent siteIntent = new Intent(Intent.ACTION_VIEW, URI);
                if (siteIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(siteIntent);
                }

            }
        });

        ConnectivityManager con = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = con.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading);
            loadingIndicator.setVisibility(View.GONE);
            EmptyTextView.setText(R.string.no_internet);
        }
    }

    @Override
    public Loader<List<story>> onCreateLoader(int i, Bundle bundle) {
        Uri uri = Uri.parse(apiKey);
        Uri.Builder uriBuilder = uri.buildUpon();
        uriBuilder.appendQueryParameter("format", "json");
        return new loader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<story>> loader, List<story> articles) {
        View loadingIndicator = findViewById(R.id.loading);
        loadingIndicator.setVisibility(View.GONE);
        EmptyTextView.setText(R.string.no_articles);
        adapterStory.clear();
        if (articles != null && !articles.isEmpty()) {
            adapterStory.addAll(articles);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<story>> loader) {
        adapterStory.clear();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        EmptyTextView.setVisibility(View.GONE);
        View loadingIndicator = findViewById(R.id.loading);
        loadingIndicator.setVisibility(View.VISIBLE);
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }


}