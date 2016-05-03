package Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pavan.moviesapp.MovieSQLiteDatabase.MovieContract;
import com.example.pavan.moviesapp.MovieSQLiteDatabase.MoviesDatabaseHelper;

import java.util.Map;
import java.util.Set;

/**
 * Created by pavan on 4/29/2016.
 */
public class ReadDatabaseRecords {
    // select statements

    private MoviesDatabaseHelper moviesDatabaseHelper;
    private Utils.ValuesForDatabase valuesForDatabase = new ValuesForDatabase();
    private ValuesForDatabase ValuesForDatabase = new ValuesForDatabase();
    private SQLiteDatabase sqLiteDatabase;
    private ContentValues contentValues;
    private Cursor cursor;
    private Context context;

    private Set<Map.Entry<String, Object>> valueSet;
    private int index;

    public ReadDatabaseRecords(Context context) {
        this.context = context;
    }

    public void fetchMovieDatabaseRecords() {
        moviesDatabaseHelper = new MoviesDatabaseHelper(context, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);


//        valuesForDatabase = new ValuesForDatabase();

        sqLiteDatabase = moviesDatabaseHelper.getReadableDatabase();

        contentValues = ValuesForDatabase.getMovieTableValues();

        cursor = sqLiteDatabase.query(MovieContract.MoviesDatabase.TABLE_NAME, null, null, null, null, null, null);

        valueSet = contentValues.valueSet();


        for (Map.Entry<String, Object> entry : valueSet) {

            index = cursor.getColumnIndex(entry.getKey());
            System.out.println("column name :" + entry.getKey());
            System.out.println("index value : " + index);
            cursor.moveToFirst();
            if (index == -1)
                Log.d(MovieContract.MoviesDatabase.TABLE_NAME, "column not found");
            else {
                System.out.println("col name : " + cursor.getColumnName(index));
            }
        }
    }
}
