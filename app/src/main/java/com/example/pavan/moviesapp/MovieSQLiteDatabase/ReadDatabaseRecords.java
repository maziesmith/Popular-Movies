package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import com.example.pavan.moviesapp.MainActivityFragment;

/**
 * Created by pavan on 4/29/2016.
 */
public class ReadDatabaseRecords {
    // select statements

    private final String LOG_TAG = getClass().getSimpleName();
    private MoviesDatabaseHelper moviesDatabaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    private Cursor cursor;
    private Context context;


    public ReadDatabaseRecords(Context context) {
        this.context = context;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public int fetchAllMovieDatabaseRecords() {

        cursor = context.getContentResolver().query(MovieContract.FavoriteMoviesDatabase.CONTENT_URI,
                null,
                null,
                null,
                null,
                null);

        Log.d(LOG_TAG, "selection provider : " + cursor.getCount());

        Log.i(LOG_TAG, "cursor.getCount() : " + cursor.getCount());


        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            if (!MainActivityFragment.Movie_ids_for_trailers_and_reviews_D.isEmpty()) {
                MainActivityFragment.Movie_ids_for_trailers_and_reviews_D.clear();
                MainActivityFragment.titles_D.clear();
                MainActivityFragment.Posters_D.clear();
                MainActivityFragment.VoteAverageArray_D.clear();
                MainActivityFragment.ReleaseDates_D.clear();
                MainActivityFragment.MovieOverViews_D.clear();
            }


            do {

                MainActivityFragment.Movie_ids_for_trailers_and_reviews_D.add(Long.parseLong(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID))));
                MainActivityFragment.titles_D.add(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_TITLE)));
                MainActivityFragment.VoteAverageArray_D.add(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE)));
                MainActivityFragment.ReleaseDates_D.add(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_RELEASE_DATE)));
                MainActivityFragment.Posters_D.add(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_POSTER)));
                MainActivityFragment.MovieOverViews_D.add(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_OVERVIEW)));

            } while (cursor.moveToNext());


            cursor.close();
            return cursor.getCount();
        } else {
            cursor.close();
            return cursor.getCount();
        }
    }
}
