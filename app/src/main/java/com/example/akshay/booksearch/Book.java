package com.example.akshay.booksearch;

/**
 * Created by Akshay on 15-07-2017.
 */

public class Book {

    private String mTitle;
    private String mAuthor;
    private String mPublisher;
    private String mPublishingDate;
    private String mwebReader;

    public Book(String title,String author,String publisher,String publishingDate,String webReader){
        mTitle = title;
        mAuthor = author;
        mPublisher = publisher;
        mPublishingDate = publishingDate;
        mwebReader = webReader;
    }


    public String getmTitle(){
        return mTitle;
    }
    public String getmAuthor(){
        return mAuthor;
    }
    public String getmPublisher(){
        return mPublisher;
    }
    public String getmPublishingDate(){
        return mPublishingDate;
    }
    public String getMwebReader(){return mwebReader;}

}
