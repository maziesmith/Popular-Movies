package com.example.pavan.moviesapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.example.pavan.moviesapp.MovieSQLiteDatabase.MovieContract;
import com.example.pavan.moviesapp.MovieSQLiteDatabase.MoviesDatabaseHelper;
import com.example.pavan.moviesapp.MovieSQLiteDatabase.TestDatabaseUtil;

import java.util.HashSet;

/**
 * Created by pavan on 4/27/2016.
 */
public class TestDB extends AndroidTestCase {
    public final String LOG_TAG = getClass().getSimpleName();

    void deleteTheDatabase() {
        mContext.deleteDatabase("movies.db");
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteTheDatabase();
    }

    public void testCreateDb() throws Throwable {

        final HashSet<String> tableNameHashSet = new HashSet<>();
        tableNameHashSet.add(MovieContract.MoviesDatabase.TABLE_NAME);
        tableNameHashSet.add(MovieContract.MovieReviewsDB.TABLE_NAME);
        tableNameHashSet.add(MovieContract.FavoriteMovie.TABLE_NAME);


        mContext.deleteDatabase("movies.db");
        SQLiteDatabase sqLiteDatabase = new MoviesDatabaseHelper(this.mContext, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION).getWritableDatabase();
        assertEquals(true, sqLiteDatabase.isOpen());

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type = 'table'", null);

        assertTrue("Error: This means that the database has not been created correctly", cursor.moveToFirst());

        do {
            tableNameHashSet.remove(cursor.getString(0));
        } while (cursor.moveToNext());

        assertTrue("Error: Your database was created without movies table, reviews table, and favorite movies tables", tableNameHashSet.isEmpty());

        cursor = sqLiteDatabase.rawQuery("PRAGMA table_info (" + MovieContract.MoviesDatabase.TABLE_NAME + ")", null);

        assertTrue("Error: This means that we were unable to query the database for table information.", cursor.moveToFirst());


        final HashSet<String> moviesColumnHashSet = new HashSet<>();

        moviesColumnHashSet.add(MovieContract.MoviesDatabase._ID);
        moviesColumnHashSet.add(MovieContract.MoviesDatabase.COLUMN_MOVIE_ID);
        moviesColumnHashSet.add(MovieContract.MoviesDatabase.COLUMN_MOVIE_OVERVIEW);
        moviesColumnHashSet.add(MovieContract.MoviesDatabase.COLUMN_MOVIE_POSTER);
        moviesColumnHashSet.add(MovieContract.MoviesDatabase.COLUMN_MOVIE_RELEASE_DATE);
        moviesColumnHashSet.add(MovieContract.MoviesDatabase.COLUMN_MOVIE_TITLE);
        moviesColumnHashSet.add(MovieContract.MoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE);

        int columnNameIndex = cursor.getColumnIndex("name");
        do {
            String columnName = cursor.getString(columnNameIndex);
            System.out.println("database columnName : " + columnName);
            moviesColumnHashSet.remove(columnName);
        } while (cursor.moveToNext());

        assertTrue("Error: The database doesn't contain all the required movies table columns", moviesColumnHashSet.isEmpty());


        cursor = sqLiteDatabase.rawQuery("PRAGMA table_info (" + MovieContract.FavoriteMovie.TABLE_NAME + ")", null);

        assertTrue("Error: This means that we were unable to query the database for favorite movies table information.", cursor.moveToFirst());

        final HashSet<String> favoriteMoviesColumnHashSet = new HashSet<>();

        favoriteMoviesColumnHashSet.add(MovieContract.FavoriteMovie._ID);
        favoriteMoviesColumnHashSet.add(MovieContract.FavoriteMovie.COLUMN_FAVORITE_MOVIES_ID);

        columnNameIndex = cursor.getColumnIndex("name");
        do {
            String columnName = cursor.getString(columnNameIndex);
            System.out.println("favorite database columnName : " + columnName);
            favoriteMoviesColumnHashSet.remove(columnName);
        } while (cursor.moveToNext());

        assertTrue("Error: The database doesn't contain all the required favorite movies table columns", favoriteMoviesColumnHashSet.isEmpty());

        cursor = sqLiteDatabase.rawQuery("PRAGMA table_info (" + MovieContract.MovieReviewsDB.TABLE_NAME + ")", null);

        assertTrue("Error: This means that we were unable to query the database for movies reviews table information.", cursor.moveToFirst());

        final HashSet<String> reviewsTableColumnHashSet = new HashSet<>();

        reviewsTableColumnHashSet.add(MovieContract.MovieReviewsDB._ID);
        reviewsTableColumnHashSet.add(MovieContract.MovieReviewsDB.COLUMN_MOVIE_ID);
        reviewsTableColumnHashSet.add(MovieContract.MovieReviewsDB.COLUMN_MOVIE_REVIEWS);

        columnNameIndex = cursor.getColumnIndex("name");
        do {
            String columnName = cursor.getString(columnNameIndex);
            System.out.println("movie reviews database table columnName : " + columnName);
            reviewsTableColumnHashSet.remove(columnName);
        } while (cursor.moveToNext());

        assertTrue("Error: The database doesn't contain all the required movie reviews table columns", favoriteMoviesColumnHashSet.isEmpty());

        sqLiteDatabase.close();
    }

    public long testMoviesDatabaseTable() {
        MoviesDatabaseHelper databaseHelper = new MoviesDatabaseHelper(mContext, "movies.db", null, 1);
        SQLiteDatabase liteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = TestDatabaseUtil.createMoviesDatabaseValues();

        long movieRowId = liteDatabase.insert(MovieContract.MoviesDatabase.TABLE_NAME, null, contentValues);
        assertTrue(movieRowId != -1); // movieRowId value will be -1 if the insertion fails.

        Cursor cursor = liteDatabase.query(
                MovieContract.MoviesDatabase.TABLE_NAME,
                null, null, null, null, null, null);

        assertTrue("Error: No Records returned from movies database query.", cursor.moveToFirst());

        TestDatabaseUtil.validateCurrentRecord("Error: Movies query validation failed", cursor, contentValues);

        assertFalse("Error: More than one record returned from movies query", cursor.moveToNext());

        cursor.close();

        return movieRowId;
    }

    public long testFavoriteMoviesDatabaseTable() {
        MoviesDatabaseHelper databaseHelper = new MoviesDatabaseHelper(mContext, "movies.db", null, 1);
        SQLiteDatabase liteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = TestDatabaseUtil.createFavoriteMoviesDatabaseValues();

        long movieRowId = liteDatabase.insert(MovieContract.FavoriteMovie.TABLE_NAME, null, contentValues);
        assertTrue(movieRowId != -1); // movieRowId value will be -1 if the insertion fails.

        Cursor cursor = liteDatabase.query(
                MovieContract.FavoriteMovie.TABLE_NAME,
                null, null, null, null, null, null);

        assertTrue("Error: No Records returned from favorite movies database query.", cursor.moveToFirst());

        TestDatabaseUtil.validateCurrentRecord("Error: Favorite Movies query validation failed", cursor, contentValues);

        assertFalse("Error: More than one record returned from favorite movies query", cursor.moveToNext());

        cursor.close();

        return movieRowId;
    }


    public long testMovieReviewsDatabaseTable() {
        MoviesDatabaseHelper databaseHelper = new MoviesDatabaseHelper(mContext, "movies.db", null, 1);
        SQLiteDatabase liteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = TestDatabaseUtil.createMovieReviewsDatabaseValues();

        long movieRowId = liteDatabase.insert(MovieContract.FavoriteMovie.TABLE_NAME, null, contentValues);
        assertTrue(movieRowId != -1); // movieRowId value will be -1 if the insertion fails.

        Cursor cursor = liteDatabase.query(
                MovieContract.MovieReviewsDB.TABLE_NAME,
                null, null, null, null, null, null);

        assertTrue("Error: No Records returned from  movie reviews database query.", cursor.moveToFirst());

        TestDatabaseUtil.validateCurrentRecord("Error: Movie Reviews query validation failed", cursor, contentValues);

        assertFalse("Error: More than one record returned from movie reviews query", cursor.moveToNext());

        cursor.close();

        return movieRowId;
    }
}
