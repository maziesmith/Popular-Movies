package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by pavan on 4/29/2016.
 */
public class checkDatabaseRecords {
    // check if the selected movie data is in the database. and also the complete movie grid data.

    private final String LOG_TAG = getClass().getSimpleName();
    private Context context;

    private MoviesDatabaseHelper moviesDatabaseHelper;

    private SQLiteDatabase sqLiteDatabase;

    private Cursor cursor;

    public checkDatabaseRecords(Context context) {
        this.context = context;
    }

    public String checkAllMovieRecordsWithDBRecords(long movie_ID) {

        moviesDatabaseHelper = new MoviesDatabaseHelper(context, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);

        sqLiteDatabase = moviesDatabaseHelper.getReadableDatabase();


        String query = "SELECT movie_id FROM " + MovieContract.FavoriteMoviesDatabase.TABLE_NAME + " WHERE movie_id = " + movie_ID;


        Log.i(LOG_TAG, "Query : " + query);

        cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.getCount() == 0) {
            Log.i(LOG_TAG, "movie is not marked as favorite movie");
            cursor.close();
            sqLiteDatabase.close();
            moviesDatabaseHelper.close();
            return "not marked yet";
        } else {
            Log.i(LOG_TAG, "movie id : " + movie_ID + " is already inserted");
            cursor.close();
            sqLiteDatabase.close();
            moviesDatabaseHelper.close();
            return "already marked favorite";
        }

    }



}
