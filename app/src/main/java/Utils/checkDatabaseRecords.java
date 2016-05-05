package Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

        movie_overview.equalsIgnoreCase("\"");

        movie_title.equalsIgnoreCase("\"");


        moviesDatabaseHelper = new MoviesDatabaseHelper(context, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);

        sqLiteDatabase = moviesDatabaseHelper.getReadableDatabase();

        contentValues = ValuesForDatabase.getMovieTableValues();

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MovieContract.MoviesDatabase.TABLE_NAME + " WHERE movie_id = " + movie_ID
                + " AND movie_title = \'" + movie_title + "\' "
                + " AND movie_vote_average = " + vote_average
                + " AND movie_release_date = '" + release_date + "' "
                + " AND movie_poster = ' " + movie_poster + "' "
                + " AND movie_overview = \'" + movie_overview + "\' ", null);

        valueSet = contentValues.valueSet();


        for (Map.Entry<String, Object> entry : valueSet) {

            columnName = entry.getKey();

            index = cursor.getColumnIndex(columnName);

            cursor.moveToFirst();

            if (index == -1) {
                Log.d(LOG_TAG, MovieContract.MoviesDatabase.TABLE_NAME + " column not found");
                Log.d(LOG_TAG, "column name :" + columnName);
            } else {

                switch (cursor.getColumnName(index)) {
                    case "movie_id":
                        if (movie_ID != (long) entry.getValue()) {
                            Log.i(LOG_TAG, "new movie id available for insertions");
                            movieId.add(movie_ID);
                        } else
                            Log.i(LOG_TAG, "movie id : " + movie_ID + " of the movie " + movie_title + " is already inserted");

                        break;

                    case "movie_release_date":
                        if (release_date != entry.getValue()) {
                            Log.i(LOG_TAG, "new movie release date available for insertions");
                            releaseDate.add(release_date);
                        } else
                            Log.i(LOG_TAG, "movie release date : " + release_date + " of the movie " + movie_title + " is already inserted");

                        break;

                    case "movie_title":
                        if (movie_title != entry.getValue()) {
                            Log.i(LOG_TAG, "new movie title available for insertion");
                            movieTitle.add(movie_title);
                        } else
                            Log.i(LOG_TAG, "movie title : " + movie_title + " is already inserted");

                        break;


                    case "movie_overview":
                        if (movie_overview != entry.getValue())

                        {
                            Log.i(LOG_TAG, "new movie_overview available for insertion");
                            movieOverview.add(movie_overview);
                        } else
                            Log.i(LOG_TAG, "movie movie_overview : " + movie_overview + " of the movie " + movie_title + " is already inserted");

                        break;

                    case "movie_poster":
                        if (movie_poster != entry.getValue()) {
                            Log.i(LOG_TAG, "new movie release date available for insertion ");
                            moviePoster.add(movie_poster);
                        } else
                            Log.i(LOG_TAG, "movie poster : " + movie_poster + " of the movie " + movie_title + " is already inserted");

                        break;
                    case "movie_vote_average":
                        if (vote_average != Double.parseDouble(entry.getValue().toString())) {
                            Log.i(LOG_TAG, "new movie vote_average available for insertion");
                            voteAverage.add(vote_average);
                        } else
                            Log.i(LOG_TAG, "movie vote_average : " + vote_average + " of the movie " + movie_title + " is already inserted");

                        break;
                }
            }
        }
        if (movieId.isEmpty() && releaseDate.isEmpty() && movieTitle.isEmpty() && movieOverview.isEmpty() && movie_poster
                .isEmpty() && voteAverage.isEmpty()) {
            Log.i(LOG_TAG, "all lists are empty");
            moviesDatabaseHelper.close();
            return "checked all the records and no insertions required";
        } else {
            for (int i = 0; i < movieId.size(); i++) {
                valuesForDatabase.createMoviesDatabaseValues(movieId.get(i), movieTitle.get(i), voteAverage.get(i), releaseDate.get(i),
                        moviePoster.get(i), movieOverview.get(i));
                databaseInsertions.insertDataIntoMoviesTable();
            }
        }
        moviesDatabaseHelper.close();
        return "new records are inserted";

    }

    public String checkFavoriteMovieRecords(long movie_ID) {
        moviesDatabaseHelper = new MoviesDatabaseHelper(context, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);

        sqLiteDatabase = moviesDatabaseHelper.getReadableDatabase();

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
                    moviesDatabaseHelper.close();
                    return "already marked favorite";
                } else {
                    valuesForDatabase.createFavoriteMoviesDatabaseValues(movie_ID);
                }
            }
        }

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
