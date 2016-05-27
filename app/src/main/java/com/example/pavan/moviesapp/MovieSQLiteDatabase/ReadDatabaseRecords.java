package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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


    public int fetchAllMovieDatabaseRecords() {

        moviesDatabaseHelper = new MoviesDatabaseHelper(context, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);


        sqLiteDatabase = moviesDatabaseHelper.getReadableDatabase();

        cursor = sqLiteDatabase.query(MovieContract.FavoriteMoviesDatabase.TABLE_NAME, null, null, null, null, null, null);


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
            sqLiteDatabase.close();
            moviesDatabaseHelper.close();
            return cursor.getCount();
        } else {
            cursor.close();
            sqLiteDatabase.close();
            moviesDatabaseHelper.close();
            return cursor.getCount();
        }
    }
}
