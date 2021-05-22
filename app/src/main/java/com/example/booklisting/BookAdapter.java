package com.example.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends ArrayAdapter {

    public BookAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.each_book,parent,false);
        }
        Book book = (Book) getItem(position);
        TextView book_title = (TextView)listItemView.findViewById(R.id.book_name);
        book_title.setText(book.getTitle());

        TextView book_author = (TextView)listItemView.findViewById(R.id.author_name);
        book_author.setText(book.getAuthor());

        return listItemView;
    }
}
