package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

        cursor = sqLiteDatabase.query(MovieContract.FavoriteMoviesDatabase.TABLE_NAME, null, null, null, null, null, null);

        valueSet = contentValues.valueSet();


        for (Map.Entry<String, Object> entry : valueSet) {

            columnName = entry.getKey();

            index = cursor.getColumnIndex(columnName);

            cursor.moveToFirst();
            if (index == -1) {
                Log.d(LOG_TAG, MovieContract.FavoriteMoviesDatabase.TABLE_NAME + " column not found");
                Log.d(LOG_TAG, "column name :" + columnName);
            } else {
                Log.i(LOG_TAG, "favorite movies table value : " + entry.getValue().toString());
                Log.i(LOG_TAG, "col name : " + cursor.getColumnName(index));
            }
        }
    }
}
