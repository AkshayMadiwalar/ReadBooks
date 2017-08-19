package com.example.akshay.booksearch;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public String bookName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView searchBook = (ImageView) findViewById(R.id.search_id);
        searchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText getBookName = (EditText) findViewById(R.id.bookName_id);
                bookName = getBookName.getText().toString();
                Intent i = new Intent(MainActivity.this,BookActivity.class);
                i.putExtra("book",bookName);
                startActivity(i);
            }
        });
    }
}
