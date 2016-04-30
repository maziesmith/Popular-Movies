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

    public DatabaseInsertions(Context con) {
        this.con = con;
    }

    public long insertDataIntoMoviesTable(long movie_ID, String movie_title, double vote_average,
                                          String release_date, String movie_poster, String movie_overview) {
        MoviesDatabaseHelper databaseHelper = new MoviesDatabaseHelper(con, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);
        SQLiteDatabase liteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = ValuesForDatabase.createMoviesDatabaseValues(movie_ID, movie_title, vote_average, release_date, movie_poster, movie_overview);

        long movieRowId = liteDatabase.insert(MovieContract.MoviesDatabase.TABLE_NAME, null, contentValues);

        if (movieRowId != -1)  // movieRowId value will be -1 if the insertion fails.
            System.out.println("data successfully inserted into row : " + movieRowId);
        else
            System.out.println("data insertion failed, error code : " + movieRowId);

        Cursor cursor = liteDatabase.query(
                MovieContract.MoviesDatabase.TABLE_NAME,
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

    public long insertDataIntoFavoriteMoviesTable(long movie_ID) {

        MoviesDatabaseHelper databaseHelper = new MoviesDatabaseHelper(con, "movies.db", null, 1);
        SQLiteDatabase liteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = ValuesForDatabase.createFavoriteMoviesDatabaseValues(movie_ID);

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

    public long insertDataIntoMovieReviewsTable(long movie_ID, String movie_reviews, String review_author) {

        MoviesDatabaseHelper databaseHelper = new MoviesDatabaseHelper(con, "movies.db", null, 1);
        SQLiteDatabase liteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = ValuesForDatabase.createMovieReviewsDatabaseValues(movie_ID, movie_reviews, review_author);

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
