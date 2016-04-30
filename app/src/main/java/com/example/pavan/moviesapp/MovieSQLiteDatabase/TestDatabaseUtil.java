package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import java.util.Map;
import java.util.Set;

/**
 * Created by pavan on 4/28/2016.
 */
public class TestDatabaseUtil extends AndroidTestCase {

    public static ContentValues createMoviesDatabaseValues() {

        ContentValues testValues = new ContentValues();

        testValues.put(MovieContract.MoviesDatabase.COLUMN_MOVIE_ID, 271110);
        testValues.put(MovieContract.MoviesDatabase.COLUMN_MOVIE_TITLE, "test Movie Title");
        testValues.put(MovieContract.MoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE, 5.81);
        testValues.put(MovieContract.MoviesDatabase.COLUMN_MOVIE_RELEASE_DATE, "2016-04-27");
        testValues.put(MovieContract.MoviesDatabase.COLUMN_MOVIE_POSTER, "/rDT86hJCxnoOs4ARjrCiRej7pOi.jpg");
        testValues.put(MovieContract.MoviesDatabase.COLUMN_MOVIE_OVERVIEW, "qwertyuiopasdfghjkzxcvbnm");

        return testValues;
    }

    public static ContentValues createFavoriteMoviesDatabaseValues() {

        ContentValues testValues = new ContentValues();
        testValues.put(MovieContract.FavoriteMovie.COLUMN_FAVORITE_MOVIES_ID, 271110);

        return testValues;
    }


    public static ContentValues createMovieReviewsDatabaseValues() {

        ContentValues testValues = new ContentValues();
        testValues.put(MovieContract.MovieReviewsDB.COLUMN_MOVIE_ID, 271110);
        testValues.put(MovieContract.MovieReviewsDB.COLUMN_MOVIE_REVIEWS, "movie reviews content");

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
