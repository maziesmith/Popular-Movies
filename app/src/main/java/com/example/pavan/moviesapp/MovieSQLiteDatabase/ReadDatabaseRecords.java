package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pavan.moviesapp.MainActivityFragment;

import java.util.ArrayList;

/**
 * Created by pavan on 4/29/2016.
 */
public class ReadDatabaseRecords {
    // select statements

    private final String LOG_TAG = getClass().getSimpleName();
    private MoviesDatabaseHelper moviesDatabaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private MainActivityFragment mainActivityFragment = new MainActivityFragment();

    private ArrayList movieID = new ArrayList();
    private ArrayList posters = new ArrayList();
    private ArrayList overView = new ArrayList();
    private ArrayList movieTiles = new ArrayList();
    private ArrayList voteAverage = new ArrayList();
    private ArrayList releaseDates = new ArrayList();

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

            do {
                Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID)));

                Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_TITLE) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_TITLE)));

//                movieID.add(Long.parseLong(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID))));

                MainActivityFragment.Movie_ids_for_trailers_and_reviews_D.add(Long.parseLong(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID))));


//                movieTiles.add(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_TITLE)));

                MainActivityFragment.titles_D.add(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_TITLE)));

                Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE)) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE)));

//                voteAverage.add(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE));

                MainActivityFragment.VoteAverageArray_D.add(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE)));

                Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_RELEASE_DATE)) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_RELEASE_DATE)));

//                releaseDates.add(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_RELEASE_DATE)));
                MainActivityFragment.ReleaseDates_D.add(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_RELEASE_DATE)));

                Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_POSTER)) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_POSTER)));

//                posters.add(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_POSTER)));
                MainActivityFragment.Posters_D.add(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_POSTER)));

                Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_OVERVIEW)) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_OVERVIEW)));

//                overView.add(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_OVERVIEW)));
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
