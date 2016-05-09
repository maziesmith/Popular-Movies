package Utils;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import com.example.pavan.moviesapp.MovieSQLiteDatabase.MovieContract;
import com.example.pavan.moviesapp.MovieSQLiteDatabase.MoviesDatabaseHelper;

/**
 * Created by pavan on 5/6/2016.
 */
public class DeleteMovieRecords {

    private final String LOG_TAG = getClass().getSimpleName();
    private Context context;
    private MoviesDatabaseHelper moviesDatabaseHelper;
    private ValuesForDatabase valuesForDatabase = new ValuesForDatabase();
    private ContentValues contentValues;
    private Cursor cursor;
    private SQLiteDatabase sqLiteDatabase;

    public DeleteMovieRecords(Context context) {
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void deleteAllFavoriteMovieRecods() {
        moviesDatabaseHelper = new MoviesDatabaseHelper(context, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);

        sqLiteDatabase = moviesDatabaseHelper.getWritableDatabase();

        sqLiteDatabase.enableWriteAheadLogging();

        sqLiteDatabase.beginTransaction();

        int flag = sqLiteDatabase.delete(MovieContract.FavoriteMovie.TABLE_NAME, null, null);

        Log.i(LOG_TAG, "flag : " + flag);

        Log.i(LOG_TAG, "favorite movie table records deleted");
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        sqLiteDatabase.disableWriteAheadLogging();
        moviesDatabaseHelper.close();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void deleteFavoriteMovieRecord(long movieID) {
        moviesDatabaseHelper = new MoviesDatabaseHelper(context, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);

        sqLiteDatabase = moviesDatabaseHelper.getWritableDatabase();

        sqLiteDatabase.enableWriteAheadLogging();

        sqLiteDatabase.beginTransaction();

        sqLiteDatabase.rawQuery("DELETE FROM " + MovieContract.FavoriteMovie.TABLE_NAME + " WHERE " + MovieContract.FavoriteMovie.COLUMN_FAVORITE_MOVIES_ID + " = " + movieID, null);

        Log.i(LOG_TAG, "record : " + movieID + "is deleted");

        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        sqLiteDatabase.disableWriteAheadLogging();
        moviesDatabaseHelper.close();
    }


}