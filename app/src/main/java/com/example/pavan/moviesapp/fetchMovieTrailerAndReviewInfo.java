package com.example.pavan.moviesapp;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by pavan on 3/26/2016.
 */
public class FetchMovieTrailerAndReviewInfo {

    FetchMovieData fetchMovieData = new FetchMovieData(null, null);
    private String MOVIE_TRAILER_AND_REVIEWS_BASE_URL = "http://api.themoviedb.org/3/movie/";
    private String TRAILERS_SOURCE = "/videos";
    private String REVIEW_SOURCE = "/reviews";
    private HttpURLConnection httpURLConnection;
    private InputStream inputStream;
    private StringBuffer stringBuffer;
    private BufferedReader bufferedReader;
    private String response;

    public String fetchMovieTrailerData(int movieID) {

        Uri uri = Uri.parse(MOVIE_TRAILER_AND_REVIEWS_BASE_URL).buildUpon().appendPath(String.valueOf(movieID))
                .appendPath(TRAILERS_SOURCE).appendQueryParameter(fetchMovieData.API_KEY_PARAM, fetchMovieData.API_KEY).build();

        try {
            URL url = new URL(uri.toString());

            //HTTP request to remote source
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            //Reading the input stream
            inputStream = httpURLConnection.getInputStream();
            stringBuffer = new StringBuffer();

            if (inputStream == null) {
                //do nothing
                Log.e("input stream is null ", inputStream.toString());
                return null;
            }

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while ((response = bufferedReader.readLine()) != null)
                stringBuffer.append(response + "\n");

            if (stringBuffer.length() == 0)
                return null;

            response = stringBuffer.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String fetchMovieReviewsInfo(int movieID) {
        Uri uri = Uri.parse(MOVIE_TRAILER_AND_REVIEWS_BASE_URL).buildUpon().appendPath(String.valueOf(movieID))
                .appendPath(REVIEW_SOURCE).appendQueryParameter(fetchMovieData.API_KEY_PARAM, fetchMovieData.API_KEY).build();

        try {
            URL url = new URL(uri.toString());

            //HTTP request to remote source
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            //Reading the input stream
            inputStream = httpURLConnection.getInputStream();
            stringBuffer = new StringBuffer();

            if (inputStream == null) {
                //do nothing
                Log.e("input stream is null ", inputStream.toString());
                return null;
            }

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while ((response = bufferedReader.readLine()) != null)
                stringBuffer.append(response + "\n");

            if (stringBuffer.length() == 0)
                return null;

            response = stringBuffer.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;

    }
}
