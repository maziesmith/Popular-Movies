package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pavan.moviesapp.NetworkActivity.MoviesResultsJSON;

import java.util.Map;
import java.util.Set;

/**
 * Created by pavan on 4/29/2016.
 */
public class ReadDatabaseRecords {
    // select statements

    private final String LOG_TAG = getClass().getSimpleName();

    private MoviesDatabaseHelper moviesDatabaseHelper;
    private com.example.pavan.moviesapp.MovieSQLiteDatabase.ValuesForDatabase valuesForDatabase = new ValuesForDatabase();
    private ValuesForDatabase ValuesForDatabase = new ValuesForDatabase();
    private SQLiteDatabase sqLiteDatabase;
    private ContentValues contentValues;
    private Cursor cursor;
    private Context context;

    private MoviesResultsJSON moviesResultsJSON = new MoviesResultsJSON();

    private Set<Map.Entry<String, Object>> valueSet;
    private int index;
    private String columnName;

    public ReadDatabaseRecords(Context context) {
        this.context = context;
    }

    public void fetchAllMovieDatabaseRecords() {

        moviesDatabaseHelper = new MoviesDatabaseHelper(context, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);


        sqLiteDatabase = moviesDatabaseHelper.getReadableDatabase();

        contentValues = ValuesForDatabase.getMovieTableValues();

        cursor = sqLiteDatabase.rawQuery("SELECT " +
                MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID + "," +
                MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_TITLE + "," +
                MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE + "," +
                MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_RELEASE_DATE + "," +
                MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_POSTER + "," +
                MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_OVERVIEW + " FROM " + MovieContract.FavoriteMoviesDatabase.TABLE_NAME, null);


        Log.i(LOG_TAG, "cursor.getCount() : " + cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            do {
                Log.i(LOG_TAG, "cursor.getString(cursor.getPosition()) : " + cursor.getColumnName(cursor.getPosition()));
                Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID)));
                moviesResultsJSON.setId(Long.valueOf(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID)));
                Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_TITLE)));
                moviesResultsJSON.setTitle(String.valueOf(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_TITLE)));
                Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE)));
                moviesResultsJSON.setVote_average(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE));
                Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_RELEASE_DATE)));
                moviesResultsJSON.setRelease_date(String.valueOf(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_RELEASE_DATE)));
                Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_POSTER)));
                moviesResultsJSON.setPoster_path(String.valueOf(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_POSTER)));
                Log.i(LOG_TAG, "cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID) :" + cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_OVERVIEW)));
                moviesResultsJSON.setOverview(String.valueOf(cursor.getColumnIndex(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_OVERVIEW)));
            } while (cursor.moveToNext());

        }

    }
}
