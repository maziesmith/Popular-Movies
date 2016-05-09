package Utils;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import com.example.pavan.moviesapp.MovieSQLiteDatabase.MovieContract;
import com.example.pavan.moviesapp.MovieSQLiteDatabase.MoviesDatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by pavan on 4/29/2016.
 */
public class checkDatabaseRecords {
    // check if the selected movie data is in the database. and also the complete movie grid data.

    private final String LOG_TAG = getClass().getSimpleName();
    private Context context;
    private String columnName;// = {"movie_id","movie_title","movie_vote_average","movie_release_date","movie_poster","movie_overview"};
    private int index;

    private List<Long> movieId = new ArrayList<>();
    private List<String> movieTitle = new ArrayList<>();
    private List<Double> voteAverage = new ArrayList<>();
    private List<String> releaseDate = new ArrayList<>();
    private List<String> moviePoster = new ArrayList<>();
    private List<String> movieOverview = new ArrayList<>();
    private List<String> movieReview = new ArrayList<>();
    private List<String> reviewAuthorName = new ArrayList<>();


    private Set<Map.Entry<String, Object>> valueSet;

    private MoviesDatabaseHelper moviesDatabaseHelper;
    private DatabaseUtils databaseUtils = new DatabaseUtils();
    private Utils.ValuesForDatabase valuesForDatabase = new ValuesForDatabase();
    private DatabaseInsertions databaseInsertions = new DatabaseInsertions(context);
    private ValuesForDatabase ValuesForDatabase = new ValuesForDatabase();
    private SQLiteDatabase sqLiteDatabase;
    private ContentValues contentValues;
    private Cursor cursor;

    public checkDatabaseRecords(Context context) {
        this.context = context;
    }

    public String checkAllMovieRecordsWithDBRecordsAndInsertIfRequired(long movie_ID, String movie_title, double vote_average,
                                                                       String release_date, String movie_poster, String movie_overview) {

        movie_title = DatabaseUtils.sqlEscapeString(movie_title);
        release_date = DatabaseUtils.sqlEscapeString(release_date);
        movie_overview = DatabaseUtils.sqlEscapeString(movie_overview);
        movie_poster = DatabaseUtils.sqlEscapeString(movie_poster);

        if (movie_overview.isEmpty())
            movie_overview = "Synopsis of this movie is not available";

        moviesDatabaseHelper = new MoviesDatabaseHelper(context, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);

        sqLiteDatabase = moviesDatabaseHelper.getReadableDatabase();

        contentValues = ValuesForDatabase.getMovieTableValues();
        String query = "SELECT * FROM " + MovieContract.MoviesDatabase.TABLE_NAME + " WHERE movie_id = " + movie_ID
                + " AND movie_title = " + movie_title
                + " AND movie_vote_average = " + vote_average
                + " AND movie_release_date = " + release_date
                + " AND movie_poster =  " + movie_poster
                + " AND movie_overview = " + movie_overview;

        Log.i(LOG_TAG, "Query : " + query);

        cursor = sqLiteDatabase.rawQuery(query, null);

        valueSet = contentValues.valueSet();


        for (Map.Entry<String, Object> entry : valueSet) {

            columnName = entry.getKey();

            index = cursor.getColumnIndex(columnName);



            if (index == -1) {
                Log.d(LOG_TAG, MovieContract.MoviesDatabase.TABLE_NAME + " column not found");
                Log.d(LOG_TAG, "column name :" + columnName);
            } else {
                String name = cursor.getColumnName(index);
                switch (name) {
                    case "movie_id":
                        if (movie_ID != (long) entry.getValue()) {
                            Log.i(LOG_TAG, "new movie id available for insertions");
                            movieId.add(movie_ID);
                        } else
                            Log.i(LOG_TAG, "movie id : " + movie_ID + " of the movie " + movie_title + " is already inserted");

                        break;

                }
            }
        }

        if (movieId.size() == 0) {
            Log.i(LOG_TAG, "all lists are empty");
            moviesDatabaseHelper.close();
            return "checked all the records and no insertions required";
        } else {
            for (int i = 0; i < movieId.size(); i++) {
                valuesForDatabase.createMoviesDatabaseValues(movieId.get(i), movieTitle.get(i), voteAverage.get(i), releaseDate.get(i),
                        moviePoster.get(i), movieOverview.get(i));
                databaseInsertions.insertDataIntoMoviesTable();
                Log.i(LOG_TAG, "record has been inserted");
            }
        }
        moviesDatabaseHelper.close();
        return "new records are inserted";

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public String checkFavoriteMovieRecords(long movie_ID) {

        moviesDatabaseHelper = new MoviesDatabaseHelper(context, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);

        sqLiteDatabase = moviesDatabaseHelper.getReadableDatabase();

        sqLiteDatabase.enableWriteAheadLogging();

        contentValues = valuesForDatabase.getFavoriteMoviesTableValues();

        cursor = sqLiteDatabase.rawQuery("SELECT favorite_movies_id FROM " + MovieContract.FavoriteMovie.TABLE_NAME +
                " WHERE favorite_movies_id = " + movie_ID, null);

        valueSet = contentValues.valueSet();


        for (Map.Entry<String, Object> entry : valueSet) {
            columnName = entry.getKey();
            cursor.moveToFirst();
            index = cursor.getColumnIndex(columnName);
            if (index == -1)
                Log.d(MovieContract.FavoriteMovie.TABLE_NAME, "column not found");
            else {

                if (movie_ID == (long) entry.getValue()) {
                    Log.d(LOG_TAG, "already marked favorite");
                    sqLiteDatabase.disableWriteAheadLogging();
                    moviesDatabaseHelper.close();
                    return "already marked favorite";
                } else {
                    valuesForDatabase.createFavoriteMoviesDatabaseValues(movie_ID);
                }
            }
        }

        sqLiteDatabase.disableWriteAheadLogging();
        moviesDatabaseHelper.close();
        return "not marked yet";
    }

    public String checkMovieReviewRecords(long movie_ID, String movie_reviews, String review_author) {

        moviesDatabaseHelper = new MoviesDatabaseHelper(context, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);

        sqLiteDatabase = moviesDatabaseHelper.getReadableDatabase();
        contentValues = valuesForDatabase.getMovieReviewsTableValues();
        valueSet = contentValues.valueSet();

        for (Map.Entry<String, Object> entry : valueSet) {
            columnName = entry.getKey();
            index = cursor.getColumnIndex(columnName);
            if (index == -1)
                Log.d(MovieContract.MovieReviewsDB.TABLE_NAME, "column not found");
            else if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

            }
                else {
                    moviesDatabaseHelper.close();
                    return "checked all the records and no insertions required in reviews table";
                }
            }

        moviesDatabaseHelper.close();
        return "records that need to be inserted are in valuesForDatabase.createMovieReviewsDatabaseValues()";
    }
}
