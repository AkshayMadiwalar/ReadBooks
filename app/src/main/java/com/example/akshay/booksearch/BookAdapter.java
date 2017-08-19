package com.example.akshay.booksearch;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Akshay on 15-07-2017.
 */

public class BookAdapter extends ArrayAdapter<Book>{

    public BookAdapter(Activity context, ArrayList<Book> books){
        super(context,0,books);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }
        Book currentBook = getItem(position);

        TextView titleTextView  = (TextView) listItemView.findViewById(R.id.title_id);
        titleTextView.setText(currentBook.getmTitle());

        TextView authorTextView  = (TextView) listItemView.findViewById(R.id.author_id);
        authorTextView.setText(currentBook.getmAuthor());

        TextView publisherTextView  = (TextView) listItemView.findViewById(R.id.publisher_id);
        publisherTextView.setText(currentBook.getmPublisher());

        TextView publishingDateTextView  = (TextView) listItemView.findViewById(R.id.publishinDate_id);
        publishingDateTextView.setText(currentBook.getmPublishingDate());


        return listItemView;
    }
}
