package Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pavan.moviesapp.MovieSQLiteDatabase.MovieContract;
import com.example.pavan.moviesapp.MovieSQLiteDatabase.MoviesDatabaseHelper;

/**
 * Created by pavan on 4/28/2016.
 */
public class DatabaseInsertions {
    private Context con;

    private ValuesForDatabase ValuesForDatabase = new ValuesForDatabase();

    public DatabaseInsertions(Context con) {
        this.con = con;
    }

    public long insertDataIntoMoviesTable() {
        MoviesDatabaseHelper databaseHelper = new MoviesDatabaseHelper(con, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);
        SQLiteDatabase liteDatabase = databaseHelper.getWritableDatabase();
        liteDatabase.beginTransaction();


        ContentValues contentValues = ValuesForDatabase.getMovieTableValues();


        long movieRowId = liteDatabase.insert(MovieContract.MoviesDatabase.TABLE_NAME, null, contentValues);

        if (movieRowId != -1)  // movieRowId value will be -1 if the insertion fails.
            System.out.println("data successfully inserted into row : " + movieRowId);
        else
            System.out.println("data insertion failed, error code : " + movieRowId);

        Cursor cursor = liteDatabase.query(
                MovieContract.MoviesDatabase.TABLE_NAME,
                null, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            System.out.println("Error: No Records returned from favorite movies database query.");
        }
        else {
            while (cursor.moveToNext()) {
                System.out.println("cursor value : " + cursor.moveToNext());
            }
        }

        AndroidUtil.validateCurrentRecord("Error: Favorite Movies query validation failed", cursor, contentValues);


        cursor.close();

        liteDatabase.setTransactionSuccessful();
        liteDatabase.endTransaction();
        databaseHelper.close();

        return movieRowId;
    }

    public long insertDataIntoFavoriteMoviesTable() {

        MoviesDatabaseHelper databaseHelper = new MoviesDatabaseHelper(con, "movies.db", null, 1);
        SQLiteDatabase liteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = ValuesForDatabase.getFavoriteMoviesTableValues();

        long movieRowId = liteDatabase.insert(MovieContract.FavoriteMovie.TABLE_NAME, null, contentValues);

        if (movieRowId != -1)  // movieRowId value will be -1 if the insertion fails.
            System.out.println("data successfully inserted into row : " + movieRowId);
        else
            System.out.println("data insertion failed, error code : " + movieRowId);

        Cursor cursor = liteDatabase.query(
                MovieContract.FavoriteMovie.TABLE_NAME,
                null, null, null, null, null, null);

        if (cursor.moveToFirst())
            System.out.println("Error: No Records returned from favorite movies database query.");
        else {
            while (cursor.moveToNext()) {
                System.out.println("cursor value : " + cursor.moveToNext());
            }
        }

        AndroidUtil.validateCurrentRecord("Error: Favorite Movies query validation failed", cursor, contentValues);

        cursor.close();

        return movieRowId;

    }

    public long insertDataIntoMovieReviewsTable() {

        MoviesDatabaseHelper databaseHelper = new MoviesDatabaseHelper(con, "movies.db", null, 1);
        SQLiteDatabase liteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = ValuesForDatabase.getMovieReviewsTableValues();

        long movieRowId = liteDatabase.insert(MovieContract.MovieReviewsDB.TABLE_NAME, null, contentValues);

        if (movieRowId != -1)  // movieRowId value will be -1 if the insertion fails.
            System.out.println("data successfully inserted into row : " + movieRowId);
        else
            System.out.println("data insertion failed, error code : " + movieRowId);

        Cursor cursor = liteDatabase.query(
                MovieContract.MovieReviewsDB.TABLE_NAME,
                null, null, null, null, null, null);

        if (cursor.moveToFirst())
            System.out.println("Error: No Records returned from favorite movies database query.");
        else {
            while (cursor.moveToNext()) {
                System.out.println("cursor value : " + cursor.moveToNext());
            }
        }

        AndroidUtil.validateCurrentRecord("Error: Favorite Movies query validation failed", cursor, contentValues);

        cursor.close();

        return movieRowId;
    }
}
