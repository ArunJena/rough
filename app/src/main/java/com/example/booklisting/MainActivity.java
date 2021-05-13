package com.example.booklisting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private final String GOOGLE_BOOK_API= " https://www.googleapis.com/books/v1/volumes?q=android&maxResults=1";
    private final int LOADER_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        BGAsyncTask bgAsyncTask = new BGAsyncTask((TextView)findViewById(R.id.text));
//        bgAsyncTask.execute(GOOGLE_BOOK_API);

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
        Book book = books.get(0);
        TextView textView = (TextView)findViewById(R.id.text);
        textView.setText(book.getTitle());
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
    public ArrayList<Book> fetchBook(String jsonStr){
        ArrayList<Book> bookArrayList = new ArrayList<>();

        Log.i("MA","fetchBook called");
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            JSONObject firstJsonObject = jsonArray.getJSONObject(0);
            JSONObject volumeInfo = firstJsonObject.getJSONObject("volumeInfo");
            String title=volumeInfo.getString("title");
            String printType = volumeInfo.getString("printType");
            bookArrayList.add(new Book(title,printType));
        }catch (JSONException e){
            e.printStackTrace();
        }
        return bookArrayList;
    }
}