package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pavan on 4/26/2016.
 */
public class MoviesDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "movies.db";

    private final String LOG_TAG = getClass().getSimpleName();


    public MoviesDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_MOVIES_TABLE = " CREATE TABLE IF NOT EXISTS " + MovieContract.MoviesDatabase.TABLE_NAME + "("
                + MovieContract.MoviesDatabase._ID + " INTEGER, "
                + MovieContract.MoviesDatabase.COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY,"
                + MovieContract.MoviesDatabase.COLUMN_MOVIE_TITLE + " TEXT NOT NULL,"
                + MovieContract.MoviesDatabase.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL,"
                + MovieContract.MoviesDatabase.COLUMN_MOVIE_OVERVIEW + " TEXT,"
                + MovieContract.MoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE + " REAL NOT NULL,"
                + MovieContract.MoviesDatabase.COLUMN_MOVIE_POSTER + " TEXT NOT NULL" + ");";

        final String CREATE_FAVORITE_MOVIES_TABLE = "CREATE TABLE IF NOT EXISTS " + MovieContract.FavoriteMovie.TABLE_NAME
                + "(" + MovieContract.MoviesDatabase._ID + " INTEGER, "
                + MovieContract.FavoriteMovie.COLUMN_FAVORITE_MOVIES_ID + " INTEGER REFERENCES "
                + MovieContract.MoviesDatabase.TABLE_NAME + "(" + MovieContract.MoviesDatabase.COLUMN_MOVIE_ID + "));";

        final String CREATE_MOVIE_REVIEWS_TABLE = "CREATE TABLE IF NOT EXISTS " + MovieContract.MovieReviewsDB.TABLE_NAME
                + " (" + MovieContract.MoviesDatabase._ID + " INTEGER, "
                + MovieContract.MovieReviewsDB.COLUMN_MOVIE_REVIEWS + " TEXT, "
                + MovieContract.MovieReviewsDB.COLUMN_MOVIE_ID + " INTEGER REFERENCES "
                + MovieContract.MoviesDatabase.TABLE_NAME + "(" + MovieContract.MoviesDatabase.COLUMN_MOVIE_ID + "),"
                + MovieContract.MovieReviewsDB.COLUMN_REVIEW_AUTHOR_NAME + " TEXT" + ");";

        db.execSQL(CREATE_MOVIES_TABLE);
        db.execSQL(CREATE_FAVORITE_MOVIES_TABLE);
        db.execSQL(CREATE_MOVIE_REVIEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MoviesDatabase.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.FavoriteMovie.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieReviewsDB.TABLE_NAME);

// Create tables again
        onCreate(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }


}
