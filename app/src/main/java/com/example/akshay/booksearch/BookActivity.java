package com.example.akshay.booksearch;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.app.LoaderManager.LoaderCallbacks;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akshay on 15-07-2017.
 */

public class BookActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>>{

    private String REQUEST_URL;
    private BookAdapter mAdapter;
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        Bundle bundle = getIntent().getExtras();
        String book = bundle.getString("book");
        String new_nook = book.replace(" ", "+");
        REQUEST_URL="https://www.googleapis.com/books/v1/volumes?q="+new_nook+"&maxResults=15";


        ListView bookListView = (ListView) findViewById(R.id.list);
        mAdapter = new BookAdapter(this,new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();



        emptyTextView = (TextView) findViewById(R.id.emptyView_id);
        bookListView.setEmptyView(emptyTextView);

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book currentbook = mAdapter.getItem(position);
                Uri bookReaderuri = Uri.parse(currentbook.getMwebReader());
                Intent website = new Intent(Intent.ACTION_VIEW,bookReaderuri);
                startActivity(website);
            }
        });
        if(networkInfo!=null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID,null,this);
        }
        else{
            View loadingIndicator = findViewById(R.id.progressIndicator_id);
            loadingIndicator.setVisibility(View.GONE);
            emptyTextView.setText(R.string.no_internet);
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new BookLoader(this,REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        View loadingIndicator = findViewById(R.id.progressIndicator_id);
        loadingIndicator.setVisibility(View.GONE);
        emptyTextView.setText(R.string.no_results);
        mAdapter.clear();
        if(data!=null && !data.isEmpty()){
            mAdapter.addAll(data);
        }
    }


    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }
}
