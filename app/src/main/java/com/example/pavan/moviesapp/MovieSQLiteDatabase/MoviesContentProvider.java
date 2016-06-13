package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class MoviesContentProvider extends ContentProvider {

    static final int MOVIES = 1;
    static final int MOVIE_WITH_IMAGE_FILE_NAME_AND_MOVIE_ID = 2;
    static final int FAVORITE_MOVIE_WITH_MOVIE_ID = 3;
    // The URI Matcher used by this content Provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static SQLiteQueryBuilder sMoviesQueryBuilder;

    static {
        sMoviesQueryBuilder = new SQLiteQueryBuilder();

        sMoviesQueryBuilder.setTables(MovieContract.FavoriteMoviesDatabase.TABLE_NAME);
    }

    private MoviesDatabaseHelper mOpenHelper;


    public MoviesContentProvider() {
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.PATH_FAVORITE_MOVIES, MOVIES);
        matcher.addURI(authority, MovieContract.PATH_FAVORITE_MOVIES + "/#", FAVORITE_MOVIE_WITH_MOVIE_ID);
        matcher.addURI(authority, MovieContract.PATH_FAVORITE_MOVIES + "/*/#", MOVIE_WITH_IMAGE_FILE_NAME_AND_MOVIE_ID);

        return matcher;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if (selection == null) selection = "1"; // makes all rows to delete

        switch (match) {
            case FAVORITE_MOVIE_WITH_MOVIE_ID:
                db.beginTransaction();
                rowsDeleted = db.delete(MovieContract.FavoriteMoviesDatabase.TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted != 0) {
                    db.setTransactionSuccessful();
                    db.endTransaction();
                } else
                    throw new android.database.SQLException("Failed to delete a row : " + uri);
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }

        if (rowsDeleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIES:
                return MovieContract.FavoriteMoviesDatabase.CONTENT_TYPE;
            case FAVORITE_MOVIE_WITH_MOVIE_ID:
                return MovieContract.FavoriteMoviesDatabase.CONTENT_ITEM_TYPE;
            case MOVIE_WITH_IMAGE_FILE_NAME_AND_MOVIE_ID:
                return MovieContract.FavoriteMoviesDatabase.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOVIES:
                db.beginTransaction();
                long _id = db.insert(MovieContract.FavoriteMoviesDatabase.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = MovieContract.FavoriteMoviesDatabase.buildFavoriteMoviesUri(_id);
                    db.setTransactionSuccessful();
                    db.endTransaction();
                } else
                    throw new android.database.SQLException("Failed to insert a roe into : " + uri);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        mOpenHelper = new MoviesDatabaseHelper(getContext(), MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.

        Cursor returnCursor;

        switch (sUriMatcher.match(uri)) {

            // ""
            case MOVIES:
                returnCursor = sMoviesQueryBuilder.query(mOpenHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case FAVORITE_MOVIE_WITH_MOVIE_ID:
                returnCursor = sMoviesQueryBuilder.query(mOpenHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        return returnCursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        final SQLiteDatabase sqLiteDatabase = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case MOVIE_WITH_IMAGE_FILE_NAME_AND_MOVIE_ID:
                sqLiteDatabase.beginTransaction();
                Log.e(getClass().getSimpleName(), "content values :" + values);
                rowsUpdated = sqLiteDatabase.update(MovieContract.FavoriteMoviesDatabase.TABLE_NAME, values, selection, selectionArgs);
                if (rowsUpdated > 0) {
                    sqLiteDatabase.setTransactionSuccessful();
                    sqLiteDatabase.endTransaction();
                } else
                    throw new android.database.SQLException("Failed to update the row : " + uri);
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }

        if (rowsUpdated != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }
}
