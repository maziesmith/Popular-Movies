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
public class checkDatabaseRecords {
    // check if the selected movie data is in the database. and also the complete movie grid data.

    private Context context;
    private String columnName;// = {"movie_id","movie_title","movie_vote_average","movie_release_date","movie_poster","movie_overview"};
    private int index, i = 0;
    private long movieID;
    private String movieTitle;
    private double voteAverage;
    private String releaseDate;
    private String moviePoster;
    private String movieOverview;
    private String movieReview;
    private String reviewAuthorName;
    private Set<Map.Entry<String, Object>> valueSet;

    private MoviesDatabaseHelper moviesDatabaseHelper;
    private Utils.ValuesForDatabase valuesForDatabase = new ValuesForDatabase();
    private ValuesForDatabase ValuesForDatabase = new ValuesForDatabase();
    private SQLiteDatabase sqLiteDatabase;
    private ContentValues contentValues;
    private Cursor cursor;

    public checkDatabaseRecords(Context context) {
        this.context = context;
    }

    public String checkAllMovieRecordsWithDBRecords(long movie_ID, String movie_title, double vote_average,
                                                    String release_date, String movie_poster, String movie_overview) {

        moviesDatabaseHelper = new MoviesDatabaseHelper(context, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);


//        valuesForDatabase = new ValuesForDatabase();

        sqLiteDatabase = moviesDatabaseHelper.getReadableDatabase();

        contentValues = ValuesForDatabase.getMovieTableValues();

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MovieContract.MoviesDatabase.TABLE_NAME + " WHERE movie_id = ", null);

        valueSet = contentValues.valueSet();


        for (Map.Entry<String, Object> entry : valueSet) {

            index = cursor.getColumnIndex(entry.getKey());
            System.out.println("column name :" + entry.getKey());
            System.out.println("index value : " + index);
            cursor.moveToFirst();
            if (index == -1)
                Log.d(MovieContract.MoviesDatabase.TABLE_NAME, "column not found");

            else if (cursor != null) {

                do {

                    switch (entry.getKey()) {
                        case "movie_id":
                            if (movie_ID == cursor.getLong(index)) {
                                System.out.println("value already exists in the table");
                            } else
                                movieID = movie_ID;
                            break;

                        case "movie_title":
                            if (movie_title == cursor.getString(index))
                                System.out.println("movie title already exists");
                            else
                                movieTitle = movie_title;
                            break;

                        case "movie_vote_average":
                            if (vote_average == cursor.getDouble(index))
                                System.out.println("vote_average already exists");
                            else
                                voteAverage = vote_average;
                            break;


                        case "movie_release_date":
                            if (release_date == cursor.getString(index))
                                System.out.println("release date already exists");
                            else
                                releaseDate = release_date;
                            break;

                        case "movie_poster":
                            if (movie_poster == cursor.getString(index))
                                System.out.println("movie poster already exits");
                            else
                                moviePoster = movie_poster;
                            break;

                        case "movie_overview":
                            if (movie_overview == null)
                                movieOverview = " Synopsis for this movie is not available";
                            else if (movie_overview == cursor.getString(index))
                                System.out.println("synopsis already exits");
                            else movieOverview = movie_overview;
                            break;
                    }

                } while (cursor.moveToNext());

                if (movieID != 0 && movieTitle != null && voteAverage != 0 && releaseDate != null && moviePoster != null && movieOverview != null)
                    valuesForDatabase.createMoviesDatabaseValues(movieID, movieTitle, voteAverage, releaseDate, moviePoster, movieOverview);
                else {
                    System.out.println("checked all the records in database");
                    moviesDatabaseHelper.close();
                    return "checked all the records and no insertions required";
                }
            }
        }
        System.out.println("checked all the records in database and insertions are needed.");
        moviesDatabaseHelper.close();
        return "records that need to be inserted are in valuesForDatabase.createMoviesDatabaseValues()";
    }

    public String checkFavoriteMovieRecords(long movie_ID) {
        moviesDatabaseHelper = new MoviesDatabaseHelper(context, MoviesDatabaseHelper.DATABASE_NAME, null, MoviesDatabaseHelper.DATABASE_VERSION);

        sqLiteDatabase = moviesDatabaseHelper.getReadableDatabase();

        contentValues = valuesForDatabase.getFavoriteMoviesTableValues();

        valueSet = contentValues.valueSet();

        for (Map.Entry<String, Object> entry : valueSet) {
            columnName = entry.getKey();
            index = cursor.getColumnIndex(columnName);
            if (index == -1)
                Log.d(MovieContract.MoviesDatabase.TABLE_NAME, "column not found");
            else if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                if (columnName == "favorite_movies_id" && movie_ID == cursor.getLong(index)) {
                    System.out.println("already marked favorite");
                    moviesDatabaseHelper.close();
                    return "already marked favorite";
                } else
                    valuesForDatabase.createFavoriteMoviesDatabaseValues(movie_ID);
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
                Log.d(MovieContract.MoviesDatabase.TABLE_NAME, "column not found");
            else if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                switch (columnName) {
                    case "movies_id":
                        if (movie_ID == cursor.getLong(index)) {
                            System.out.println("value already exists in the table");
                        } else
                            movieID = movie_ID;

                    case "reviews":
                        if (movie_reviews == cursor.getString(index))
                            System.out.println("reviews value already exists");
                        else
                            movieReview = movie_reviews;
                        break;

                    case "author_name":
                        if (review_author == cursor.getString(index))
                            System.out.println("author name already exists");
                        else
                            reviewAuthorName = review_author;
                        break;

                }
                if (movieID != 0 && movieReview != null && reviewAuthorName != null)
                    valuesForDatabase.createMovieReviewsDatabaseValues(movieID, movieReview, reviewAuthorName);
                else {
                    moviesDatabaseHelper.close();
                    return "checked all the records and no insertions required in reviews table";
                }
            }
        }

        moviesDatabaseHelper.close();
        return "records that need to be inserted are in valuesForDatabase.createMovieReviewsDatabaseValues()";
    }
}
