package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by pavan on 4/29/2016.
 */
public class checkDatabaseRecords {
    // check if the selected movie data is in the database. and also the complete movie grid data.

    private final String LOG_TAG = getClass().getSimpleName();
    private Context context;
    private String columnName;// = {"movie_id","movie_title","movie_vote_average","movie_release_date","movie_poster","movie_overview"};
    private int index;

    private List<Long> movieId = new ArrayList<>();
    private List<String> movieTitle = new ArrayList<>();
    private List<Double> voteAverage = new ArrayList<>();
    private List<String> releaseDate = new ArrayList<>();
    private List<String> moviePoster = new ArrayList<>();
    private List<String> movieOverview = new ArrayList<>();
    private List<String> movieReview = new ArrayList<>();
    private List<String> reviewAuthorName = new ArrayList<>();


    private Set<Map.Entry<String, Object>> valueSet;

    private MoviesDatabaseHelper moviesDatabaseHelper;
    private DatabaseUtils databaseUtils = new DatabaseUtils();
    private com.example.pavan.moviesapp.MovieSQLiteDatabase.ValuesForDatabase valuesForDatabase = new ValuesForDatabase();
    private DatabaseInsertions databaseInsertions = new DatabaseInsertions(context);
    private ValuesForDatabase ValuesForDatabase = new ValuesForDatabase();
    private SQLiteDatabase sqLiteDatabase;
    private ContentValues contentValues;
    private Cursor cursor;

    public checkDatabaseRecords(Context context) {
        this.context = context;
    }

    public String checkAllMovieRecordsWithDBRecords(long movie_ID) {

        moviesDatabaseHelper = new MoviesDatabaseHelper(context, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);

        sqLiteDatabase = moviesDatabaseHelper.getReadableDatabase();

        contentValues = ValuesForDatabase.getMovieTableValues();

        String query = "SELECT movie_id FROM " + MovieContract.FavoriteMoviesDatabase.TABLE_NAME + " WHERE movie_id = " + movie_ID;

        Log.i(LOG_TAG, "Query : " + query);

        cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.getCount() == 0) {
            Log.i(LOG_TAG, "movie is not marked as favorite movie");
            moviesDatabaseHelper.close();
            return "not marked yet";
        } else {
            Log.i(LOG_TAG, "movie id : " + movie_ID + " is already inserted");
            moviesDatabaseHelper.close();
            return "already marked favorite";
        }
    }


    public String checkAllMovieReviewRecords(long movieID) {

        moviesDatabaseHelper = new MoviesDatabaseHelper(context, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);

        sqLiteDatabase = moviesDatabaseHelper.getReadableDatabase();

        contentValues = valuesForDatabase.getMovieTableValues();

        String query = "SELECT " + MovieContract.FavoriteMoviesDatabase.COLUMN_REVIEW_AUTHOR_NAME + ","
                + MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_REVIEWS +
                " FROM " + MovieContract.FavoriteMoviesDatabase.TABLE_NAME + " WHERE "
                + MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID + "= " + movieID;

        cursor = sqLiteDatabase.rawQuery(query, null);

        valueSet = contentValues.valueSet();

        Log.i(LOG_TAG, "cursor.getCount() : " + String.valueOf(cursor.getCount()));

        for (Map.Entry<String, Object> entry : valueSet) {
            columnName = entry.getKey();
            index = cursor.getColumnIndex(columnName);

            if (index != -1) {
                Log.d(LOG_TAG, MovieContract.FavoriteMoviesDatabase.TABLE_NAME + " column not found");
                Log.d(LOG_TAG, "column name :" + columnName);
            } else {
                if (entry.getKey() == MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_REVIEWS || entry.getKey() == MovieContract.FavoriteMoviesDatabase.COLUMN_REVIEW_AUTHOR_NAME) {
                    Log.i(LOG_TAG, entry.getKey() + " :" + entry.getValue());
                    return "reviews data is available in the database";
                }

            }
        }
        Log.i(LOG_TAG, "reviews data not available");
        return "reviews data not available";
    }
}
