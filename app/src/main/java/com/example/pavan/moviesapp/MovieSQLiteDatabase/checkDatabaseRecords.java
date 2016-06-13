package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public String checkAllMovieRecordsWithDBRecords(long movie_ID) {

        moviesDatabaseHelper = new MoviesDatabaseHelper(context, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);

        sqLiteDatabase = moviesDatabaseHelper.getReadableDatabase();


        cursor = context.getContentResolver().query(
                MovieContract.FavoriteMoviesDatabase.CONTENT_URI,
                new String[]{MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID},
                MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID + " = " + movie_ID,
                null, null, null);

        Log.d(LOG_TAG, "checkDatabaseRecords provider size : " + cursor.getCount());

//        String query = "SELECT movie_id FROM " + MovieContract.FavoriteMoviesDatabase.TABLE_NAME + " WHERE movie_id = " + movie_ID;


//        cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.getCount() == 0) {
            cursor.close();
            sqLiteDatabase.close();
            moviesDatabaseHelper.close();
            return "not marked yet";
        } else {
            cursor.close();
            sqLiteDatabase.close();
            moviesDatabaseHelper.close();
            return "already marked favorite";
        }

    }



}
