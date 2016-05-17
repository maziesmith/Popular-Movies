package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
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

        MoviesDatabaseHelper databaseHelper = new MoviesDatabaseHelper(context, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.beginTransaction();

        fileName = DatabaseUtils.sqlEscapeString(fileName);

        String query = "UPDATE " + MovieContract.FavoriteMoviesDatabase.TABLE_NAME + " SET "
                + MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_POSTER + " = " + fileName
                + " WHERE " + MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID + " = " + movieID;

        sqLiteDatabase.execSQL(query);

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MovieContract.FavoriteMoviesDatabase.TABLE_NAME + " WHERE "
                + MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID + " = " + movieID, null);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }
        do {
            Log.i(LOG_TAG, "cursor.getString(cursor.getPosition()) : " + cursor.getColumnName(cursor.getPosition()));
            Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID)));
            Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_TITLE)));
            Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE)));
            Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_RELEASE_DATE)));
            Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_POSTER)));
            Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_OVERVIEW)));
        } while (cursor.moveToNext());


        cursor.close();
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        sqLiteDatabase.close();
        databaseHelper.close();
    }
}
