package com.example.booklisting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private final String GOOGLE_BOOK_API= " https://www.googleapis.com/books/v1/volumes?q=android&maxResults=35";
    private final int LOADER_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportLoaderManager().initLoader(LOADER_ID,null,this);
        Log.i("MA","initLoader called------------");
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        Log.i("MA","onCreateLoader called---------");
        return new BGAsyncTaskLoader(MainActivity.this,GOOGLE_BOOK_API);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Log.i("MA","onLoadFinished called------------");
        ArrayList<Book> books = fetchBook(data);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(new BookAdapter(this,books));

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
    public ArrayList<Book> fetchBook(String jsonStr){
        ArrayList<Book> bookArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            for(int i=0;i<jsonArray.length();i++) {
                JSONObject currentJsonObject = jsonArray.getJSONObject(i);
                JSONObject volumeInfo = currentJsonObject.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                String publisher = volumeInfo.getString("publisher");
                bookArrayList.add(new Book(title, publisher));
                Log.i("MA","Avg rating fetched");
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return bookArrayList;
    }
}