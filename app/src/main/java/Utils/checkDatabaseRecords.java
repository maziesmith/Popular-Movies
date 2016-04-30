package Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.pavan.moviesapp.MovieSQLiteDatabase.MoviesDatabaseHelper;

/**
 * Created by pavan on 4/29/2016.
 */
public class checkDatabaseRecords {
    // check if the selected movie data is in the database. and also the complete movie grid data.

    Context context;

    public checkDatabaseRecords(Context context) {
        this.context = context;
    }

    public void checkAllMovieRecords() {

        MoviesDatabaseHelper moviesDatabaseHelper = new MoviesDatabaseHelper(context, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);

        SQLiteDatabase sqLiteDatabase = moviesDatabaseHelper.getReadableDatabase();

        ContentValues contentValues = ValuesForDatabase.createMoviesDatabaseValues()
    }
}
