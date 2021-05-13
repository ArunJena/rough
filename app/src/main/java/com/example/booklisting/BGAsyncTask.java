package com.example.booklisting;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BGAsyncTask extends AsyncTask<String,Void,String> {

    TextView textView;
    public BGAsyncTask(TextView textView) {
        this.textView=textView;
    }

    @Override
    protected String doInBackground(String... strings) {
        String stringurl = strings[0];
        URL url = null;
        String result=null;
        try {
            url=new URL(stringurl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.connect();
            result=Integer.toString(httpURLConnection.getResponseCode());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        textView.setText(s);
    }
}
