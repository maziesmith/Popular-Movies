package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by pavan on 5/14/2016.
 */
public class DatabaseUpdates {

    private final String LOG_TAG = getClass().getSimpleName();
    private Context con;
    private ValuesForDatabase valuesForDatabase = new ValuesForDatabase();

    public DatabaseUpdates(Context con) {
        this.con = con;
    }

    public void updateReviewsDataInTheTable(long movieID) {
        MoviesDatabaseHelper databaseHelper = new MoviesDatabaseHelper(con, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);
        SQLiteDatabase liteDatabase = databaseHelper.getWritableDatabase();
        liteDatabase.beginTransaction();


        ContentValues contentValues = valuesForDatabase.getMovieTableValues();

        String whereClause = MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID + " = " + movieID;

        int rowsAffected = liteDatabase.update(MovieContract.FavoriteMoviesDatabase.TABLE_NAME, contentValues, whereClause, null);

        if (rowsAffected > 0) {
            Log.i(LOG_TAG, rowsAffected + "rows affected");
            Log.i(LOG_TAG, "record updated for the movie with ID : " + movieID);
        } else {
            Log.i(LOG_TAG, rowsAffected + "rows affected");
            Log.i(LOG_TAG, "record not updated");
        }


    }
}
