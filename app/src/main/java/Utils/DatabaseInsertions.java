package Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pavan.moviesapp.MovieSQLiteDatabase.MovieContract;
import com.example.pavan.moviesapp.MovieSQLiteDatabase.MoviesDatabaseHelper;

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


        long movieRowId = liteDatabase.insert(MovieContract.MoviesDatabase.TABLE_NAME, null, contentValues);

        if (movieRowId != -1)  // movieRowId value will be -1 if the insertion fails.
            Log.d(LOG_TAG, "data successfully inserted into row : " + movieRowId);
        else
            Log.d(LOG_TAG, "data insertion failed, error code : " + movieRowId);


        liteDatabase.setTransactionSuccessful();
        liteDatabase.endTransaction();
        databaseHelper.close();

        return movieRowId;
    }

    public long insertDataIntoFavoriteMoviesTable() {

        MoviesDatabaseHelper databaseHelper = new MoviesDatabaseHelper(con, null, null, 1);
        SQLiteDatabase liteDatabase = databaseHelper.getWritableDatabase();
        liteDatabase.beginTransaction();

        ContentValues contentValues = valuesForDatabase.getFavoriteMoviesTableValues();

        long movieRowId = liteDatabase.insert(MovieContract.FavoriteMovie.TABLE_NAME, null, contentValues);

        if (movieRowId != -1)  // movieRowId value will be -1 if the insertion fails.
            Log.d(LOG_TAG, "data successfully inserted into row : " + movieRowId);
        else
            Log.d(LOG_TAG, "data insertion failed, error code : " + movieRowId);

        liteDatabase.endTransaction();
        return movieRowId;

    }

    public long insertDataIntoMovieReviewsTable() {

        MoviesDatabaseHelper databaseHelper = new MoviesDatabaseHelper(con, "movies.db", null, 1);
        SQLiteDatabase liteDatabase = databaseHelper.getWritableDatabase();

        liteDatabase.beginTransaction();

        ContentValues contentValues = valuesForDatabase.getMovieReviewsTableValues();

        long movieRowId = liteDatabase.insert(MovieContract.MovieReviewsDB.TABLE_NAME, null, contentValues);

        if (movieRowId != -1)  // movieRowId value will be -1 if the insertion fails.
            Log.d(LOG_TAG, "data successfully inserted into row : " + movieRowId);
        else
            Log.d(LOG_TAG, "data insertion failed, error code : " + movieRowId);

        liteDatabase.endTransaction();

        return movieRowId;
    }
}
