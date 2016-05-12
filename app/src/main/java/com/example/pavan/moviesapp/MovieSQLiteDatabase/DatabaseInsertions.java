package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by pavan on 4/28/2016.
 */
public class DatabaseInsertions {
    private final String LOG_TAG = getClass().getSimpleName();
    private Context con;
    private ValuesForDatabase valuesForDatabase = new ValuesForDatabase();

    public DatabaseInsertions(Context con) {
        this.con = con;
    }

    public long insertDataIntoMoviesTable() {
        MoviesDatabaseHelper databaseHelper = new MoviesDatabaseHelper(con, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);
        SQLiteDatabase liteDatabase = databaseHelper.getWritableDatabase();
        liteDatabase.beginTransaction();


        ContentValues contentValues = valuesForDatabase.getMovieTableValues();


        long movieRowId = liteDatabase.insert(MovieContract.FavoriteMoviesDatabase.TABLE_NAME, null, contentValues);

        if (movieRowId != -1)  // movieRowId value will be -1 if the insertion fails.
        {
            Log.d(LOG_TAG, "data successfully inserted into row : " + movieRowId);
            liteDatabase.setTransactionSuccessful();
        } else {
            Log.d(LOG_TAG, "data insertion failed, error code : " + movieRowId);
            liteDatabase.endTransaction();
            databaseHelper.close();
            return movieRowId;
        }


        liteDatabase.endTransaction();
        databaseHelper.close();

        return movieRowId;
    }

}
