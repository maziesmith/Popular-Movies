package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pavan on 4/26/2016.
 */
public class MoviesDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "movies.db";

    private final String LOG_TAG = getClass().getSimpleName();


    public MoviesDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_MOVIES_TABLE = " CREATE TABLE IF NOT EXISTS " + MovieContract.FavoriteMoviesDatabase.TABLE_NAME + "("
                + MovieContract.FavoriteMoviesDatabase._ID + " INTEGER, "
                + MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY,"
                + MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_TITLE + " TEXT NOT NULL,"
                + MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL,"
                + MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_OVERVIEW + " TEXT,"
                + MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE + " REAL NOT NULL,"
                + MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_POSTER + " TEXT NOT NULL "
                + ");";


        db.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.FavoriteMoviesDatabase.TABLE_NAME);
// Create tables again
        onCreate(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.FavoriteMoviesDatabase.TABLE_NAME);
// Create tables again
        onCreate(db);
    }


}
