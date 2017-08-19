package com.example.akshay.booksearch;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.akshay.booksearch.R.drawable.book;

/**
 * Created by Akshay on 15-07-2017.
 */

public class QueryUtils {


    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }


    //Make steps //
    public static List<Book> fetchBookData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makehttpResponse(url);
        }catch (IOException e){
            Log.e(LOG_TAG,"Problem making http request",e);
        }
        List<Book> books = extractFeatureFromJson(jsonResponse);
        return books;
    }


    //Create url//
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building url", e);
        }
        return url;
    }


    //Make HttpRequest//
    private static String makehttpResponse(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retreiving data from server", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    //Convert the inputstream from server into string//
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }



    //processing request, parsing json response//
    private static List<Book> extractFeatureFromJson(String extractJson){
        if(TextUtils.isEmpty(extractJson)){
            return null;
        }

        List<Book> books = new ArrayList<>();

        try{
            JSONObject baseJsonObject = new JSONObject(extractJson);
            JSONArray itemsArray = baseJsonObject.getJSONArray("items");
            for(int i=0;i<itemsArray.length();i++){
                JSONObject currentBook = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");
                JSONObject saleInfo = currentBook.getJSONObject("saleInfo");
                String title = volumeInfo.getString("title");
                JSONArray authors = volumeInfo.getJSONArray("authors");
                String author="";
                for(int j=0;j<authors.length();j++){
                    author+=authors.getString(j);
                }
                String publisher = volumeInfo.getString("publisher");
                String publishingDate = volumeInfo.getString("publishedDate");

                JSONObject accessInfo = currentBook.getJSONObject("accessInfo");
                String previewLink = accessInfo.getString("webReaderLink");


                Book book = new Book(title,author,publisher,publishingDate,previewLink);
                books.add(book);
            }
        }catch(JSONException e){
            Log.e(LOG_TAG,"Problem parsing json response",e);
        }
        return books;
    }
}
