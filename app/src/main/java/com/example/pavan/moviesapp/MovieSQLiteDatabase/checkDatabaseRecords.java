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
            cursor.close();
            sqLiteDatabase.close();
            moviesDatabaseHelper.close();
            return "not marked yet";
        } else {
            Log.i(LOG_TAG, "movie id : " + movie_ID + " is already inserted");
            cursor.close();
            sqLiteDatabase.close();
            moviesDatabaseHelper.close();
            return "already marked favorite";
        }

    }



}
