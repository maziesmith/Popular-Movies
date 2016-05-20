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


        sqLiteDatabase.beginTransaction();

        sqLiteDatabase.execSQL("DELETE FROM " + MovieContract.FavoriteMoviesDatabase.TABLE_NAME + " WHERE " + MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID + " = " + movieID);

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MovieContract.FavoriteMoviesDatabase.TABLE_NAME + " WHERE " + MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID + " = " + movieID, null);

        if (cursor.getCount() == 0) {
            Log.i(LOG_TAG, "record : " + movieID + "is deleted");
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();
            moviesDatabaseHelper.close();
            return "movie record deleted";
        } else {
            Log.i(LOG_TAG, "record : " + movieID + "is not deleted");
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();
            moviesDatabaseHelper.close();
            return "movie record not deleted";
        }
    }
}
