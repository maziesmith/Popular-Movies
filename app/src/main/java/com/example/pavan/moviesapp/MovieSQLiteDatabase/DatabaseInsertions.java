package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.util.Log;

/**
 * Created by pavan on 4/28/2016.
 */
public class DatabaseInsertions {
    private final String LOG_TAG = getClass().getSimpleName();
    private Context con;
    private Cursor cursor;

    public DatabaseInsertions(Context con) {
        this.con = con;
    }

    public String insertDataIntoMoviesTable(long movie_ID, String movie_title, double vote_average,
                                            String release_date, String movie_poster, String movie_overview) {


        movie_title = DatabaseUtils.sqlEscapeString(movie_title);
        release_date = DatabaseUtils.sqlEscapeString(release_date);
        movie_poster = DatabaseUtils.sqlEscapeString(movie_poster);
        movie_overview = DatabaseUtils.sqlEscapeString(movie_overview);


        ContentValues values = new ContentValues();

        values.put(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID, movie_ID);
        values.put(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_TITLE, movie_title);
        values.put(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE, vote_average);
        values.put(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_RELEASE_DATE, release_date);
        values.put(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_POSTER, movie_poster);
        values.put(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_OVERVIEW, movie_overview);


        Log.e(LOG_TAG, " content provider insert start");

        Uri insertedUri = con.getContentResolver().insert(MovieContract.FavoriteMoviesDatabase.CONTENT_URI, values);

        long id = ContentUris.parseId(insertedUri);

        Log.e(LOG_TAG, " content provider insert : " + id);

        if (id > 0 && id != -1)
            return "inserted successfully";
        else
            return "insertion unsuccessful";
    }

}
