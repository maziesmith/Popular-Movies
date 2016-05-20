package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
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
        MoviesDatabaseHelper databaseHelper = new MoviesDatabaseHelper(con, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);
        SQLiteDatabase liteDatabase = databaseHelper.getWritableDatabase();
        liteDatabase.beginTransaction();

        movie_title = DatabaseUtils.sqlEscapeString(movie_title);
        release_date = DatabaseUtils.sqlEscapeString(release_date);
        movie_poster = DatabaseUtils.sqlEscapeString(movie_poster);
        movie_overview = DatabaseUtils.sqlEscapeString(movie_overview);

        String query = "INSERT INTO " + MovieContract.FavoriteMoviesDatabase.TABLE_NAME + "("
                + MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID + ","
                + MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_TITLE + ","
                + MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE + ","
                + MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_RELEASE_DATE + ","
                + MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_POSTER + ","
                + MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_OVERVIEW + ") VALUES("
                + movie_ID + "," + movie_title + "," + vote_average + "," + release_date + "," + movie_poster + "," + movie_overview + ")";

        liteDatabase.execSQL(query);

        cursor = liteDatabase.rawQuery("SELECT * FROM " + MovieContract.FavoriteMoviesDatabase.TABLE_NAME + " WHERE "
                + MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID + "= " + movie_ID, null);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

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
            liteDatabase.setTransactionSuccessful();
            liteDatabase.endTransaction();
            databaseHelper.close();
            return "inserted successfully";
        } else {
            cursor.close();
            liteDatabase.endTransaction();
            databaseHelper.close();
            return "insertion unsuccessful";
        }
    }
}
