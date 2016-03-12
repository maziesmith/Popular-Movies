package com.example.pavan.moviesapp;

import android.net.Uri;
import android.os.AsyncTask;
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
import java.util.ArrayList;


/**
 * Created by pavan on 3/11/2016.
 */
public class FetchMovieData extends AsyncTask {

    JSONObject jsonObject;
    JSONObject objectsInJSONArray;
    ArrayList<String> Posters = new ArrayList<>();

    @Override
    protected Object doInBackground(Object[] params) {
        if (params.length == 0)
            return null;

        String API_KEY = "xxxxxxxxxxx";
//        String IMAGE_SIZE = "w185";
        String BASE_URL = " http://api.themoviedb.org/3/discover/movie?";
        // String SORT_BY = "POPULARITY.desc";
        String SORT_BY = (String) params[0];

        String SORT_BY_PARAM = "sort_by";
        String API_KEY_PARAM = "api_key";
        Uri.Builder uri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(SORT_BY_PARAM, SORT_BY)
                .appendQueryParameter(API_KEY_PARAM, API_KEY);

        uri.build();


        String MoviesJSONdata = null;
        try {
            URL url = new URL(uri.toString());

            //Creating a request to the moviedb api
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //Reading the input stream
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();

            if (inputStream == null){
                //do nothing
                Log.e("input stream is null ", inputStream.toString());
                return null;}

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuffer.append(line + "\n");
            if (stringBuffer.length() == 0)
                return null;

            MoviesJSONdata = stringBuffer.toString();



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return MoviesJSONdata;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);


        try {

            jsonObject = new JSONObject(result.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("results");


            for (int i = 0; i < jsonArray.length(); i++) {
                objectsInJSONArray = jsonArray.getJSONObject(i);
                for (int j = 0; j < objectsInJSONArray.length(); j++) {
                    Posters.add(objectsInJSONArray.getString("poster_path"));
//                    System.out.println("string list content: " + imageAdater.moviePoster.get(j));
                }
            }

            new ImageAdapter(Posters);
//            System.out.println("moviePoster in image adapter : "+ imageAdater.moviePoster);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}