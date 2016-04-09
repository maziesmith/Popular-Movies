package com.example.pavan.moviesapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

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
    ArrayList movieOverViews = new ArrayList();
    ArrayList releaseDates = new ArrayList();
    ArrayList titles = new ArrayList();
    ArrayList voteAverage = new ArrayList();
    ArrayList movie_ids_for_trailers = new ArrayList();
    Context context;
    GridView gridView;
    String BASE_URL, API_KEY = "f9b69f2b96bfaa9b1748f12afbe14cea";
    String API_KEY_PARAM, SORT_BY_PARAM;


    public FetchMovieData(Context context,GridView gridView) {
        this.context = context;
        this.gridView = gridView;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        if (params.length == 0)
            return null;


        BASE_URL = " http://api.themoviedb.org/3/discover/movie?";

        String SORT_BY = (String) params[0];

        SORT_BY_PARAM = "sort_by";
        API_KEY_PARAM = "api_key";
        Uri.Builder uri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(SORT_BY_PARAM, SORT_BY)
                .appendQueryParameter(API_KEY_PARAM, API_KEY);

        uri.build();

        System.out.println(uri);

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

//            System.out.println("jsonArray data : " + jsonArray);

            for (int i = 0; i < jsonArray.length() ; i++) {
                objectsInJSONArray = jsonArray.optJSONObject(i);

                if (objectsInJSONArray.get("poster_path").equals(null))
                    Log.i("poster_path",objectsInJSONArray.get("poster_path").toString());
                else
                Posters.add(objectsInJSONArray.get("poster_path").toString());

                releaseDates.add(objectsInJSONArray.get("release_date").toString());
                movieOverViews.add(objectsInJSONArray.get("overview"));
                titles.add(objectsInJSONArray.get("original_title"));
                voteAverage.add(objectsInJSONArray.get("vote_average"));
                movie_ids_for_trailers.add(objectsInJSONArray.get("id"));
            }

            Log.i("vote_average ",voteAverage.toString());
            Log.i("movie titles",titles.toString());
            Log.i("releaseDates",releaseDates.toString());
            Log.i("movie Posters",Posters.toString());
            Log.i("movie_ids_for_trailers", movie_ids_for_trailers.toString());

//            gridView.setAdapter(new ImageAdapter(context, Posters));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}