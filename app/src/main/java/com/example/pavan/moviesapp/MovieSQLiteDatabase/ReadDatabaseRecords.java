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
    private MainActivityFragment mainActivityFragment = new MainActivityFragment();

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
                MainActivityFragment.movie_ids_for_trailers_and_reviews.add(Long.parseLong(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID))));

//                mainActivityFragment.setMovie_ids_for_trailers_and_reviews(Long.parseLong(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID))));

                MainActivityFragment.titles.add(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_TITLE)));

//                mainActivityFragment.setTitles(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_TITLE)));

                Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE)) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE)));

                MainActivityFragment.voteAverageArray.add(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE)));

//                mainActivityFragment.setVoteAverageArray(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE)));

                Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_RELEASE_DATE)) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_RELEASE_DATE)));

                MainActivityFragment.releaseDates.add(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_RELEASE_DATE)));

//                mainActivityFragment.setReleaseDates(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_RELEASE_DATE)));

                Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_POSTER)) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_POSTER)));

                MainActivityFragment.Posters.add(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_POSTER)));

//                mainActivityFragment.setPosters(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_POSTER)));

                Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_OVERVIEW)) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_OVERVIEW)));

                MainActivityFragment.movieOverViews.add(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_OVERVIEW)));

//                mainActivityFragment.setMovieOverViews(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_OVERVIEW)));

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
