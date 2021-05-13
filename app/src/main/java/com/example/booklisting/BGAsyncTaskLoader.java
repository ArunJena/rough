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
        Log.i("Worker thread","loadInBackground executing++++++++");
        URL url = null;
        String result=null;
        InputStream inputStream;
        try {
            url=new URL(Strurl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            StringBuilder sb = new StringBuilder();
            while(line!=null){
                sb.append(line);
                line=bufferedReader.readLine();
            }
            result=sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
