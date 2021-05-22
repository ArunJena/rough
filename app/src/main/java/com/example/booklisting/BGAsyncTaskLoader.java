package com.example.booklisting;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BGAsyncTaskLoader extends AsyncTaskLoader<String> {

    String Strurl;
    public BGAsyncTaskLoader(@NonNull Context context,String url) {
        super(context);
        this.Strurl=url;
    }

    @Override
    protected void onStartLoading() {
        Log.i("Worker thread","onStartLoading called+ forceLoad()-------");
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        String result=HttpConnection.fetchData(Strurl);
        return result;
    }
}
