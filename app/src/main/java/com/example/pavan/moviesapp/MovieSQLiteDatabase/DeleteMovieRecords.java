package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by pavan on 5/6/2016.
 */
public class DeleteMovieRecords {

    private final String LOG_TAG = getClass().getSimpleName();
    private Context context;
    private MoviesDatabaseHelper moviesDatabaseHelper;
    private Cursor cursor;
    private SQLiteDatabase sqLiteDatabase;

    public DeleteMovieRecords(Context context) {
        this.context = context;
    }


    public String deleteFavoriteMovieRecord(long movieID) {
        moviesDatabaseHelper = new MoviesDatabaseHelper(context, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);

        sqLiteDatabase = moviesDatabaseHelper.getWritableDatabase();

        int rowsDeleted = context.getContentResolver().delete(
                MovieContract.FavoriteMoviesDatabase.buildFavoriteMoviesUri(movieID),
                MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID + " = " + movieID,
                null);

        Log.d(LOG_TAG, "deleted provider rows : " + rowsDeleted);

        if (rowsDeleted > 0) {
            Log.i(LOG_TAG, "record : " + movieID + "is deleted");
            return "movie record deleted";
        } else {
            Log.i(LOG_TAG, "record : " + movieID + "is not deleted");
            return "movie record not deleted";
        }
    }
}
