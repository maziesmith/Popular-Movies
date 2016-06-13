package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.util.Log;

/**
 * Created by pavan on 5/17/2016.
 */
public class UpdateMovieRecord {

    final String LOG_TAG = getClass().getSimpleName();
    private Context context;

    public UpdateMovieRecord(Context context) {
        this.context = context;
    }


    public void UpdateMoviePoster(String fileName, long movieID) {

        fileName = DatabaseUtils.sqlEscapeString(fileName);

        ContentValues values = new ContentValues();

        values.put(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_POSTER, fileName);

        int _id = context.getContentResolver().update(
                MovieContract.FavoriteMoviesDatabase.buildFavoriteMoviesUriWithFileNameAndMovieID(fileName, movieID),
                values,
                MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID + " = " + movieID,
                null);

        Log.e(LOG_TAG, "update provider : " + _id);

    }
}
