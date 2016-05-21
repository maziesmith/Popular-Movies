package com.example.pavan.moviesapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import com.example.pavan.moviesapp.MovieSQLiteDatabase.MovieContract;

import java.util.Map;
import java.util.Set;

/**
 * Created by pavan on 4/28/2016.
 */
public class TestDatabaseUtil extends AndroidTestCase {

    public static ContentValues createMoviesDatabaseValues() {

        ContentValues testValues = new ContentValues();

        testValues.put(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID, 271110);
        testValues.put(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_TITLE, "test Movie Title");
        testValues.put(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE, 5.81);
        testValues.put(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_RELEASE_DATE, "2016-04-27");
        testValues.put(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_POSTER, "/rDT86hJCxnoOs4ARjrCiRej7pOi.jpg");
        testValues.put(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_OVERVIEW, "qwertyuiopasdfghjkzxcvbnm");

        return testValues;
    }


    public static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();

        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();

            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column ' " + columnName + " ' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value ' " + entry.getValue().toString() + " ' did not match the expected value '  " + expectedValue + " '. " + error, expectedValue, valueCursor.getString(idx));
        }
    }
}
