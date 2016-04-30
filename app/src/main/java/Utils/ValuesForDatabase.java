package Utils;

import android.content.ContentValues;

import com.example.pavan.moviesapp.MovieSQLiteDatabase.MovieContract;

/**
 * Created by pavan on 4/28/2016.
 */
public class ValuesForDatabase {


    public static ContentValues createMoviesDatabaseValues(long movie_ID, String movie_title, double vote_average,
                                                           String release_date, String movie_poster, String movie_overview) {

        ContentValues testValues = new ContentValues();

        testValues.put(MovieContract.MoviesDatabase.COLUMN_MOVIE_ID, movie_ID);
        testValues.put(MovieContract.MoviesDatabase.COLUMN_MOVIE_TITLE, movie_title);
        testValues.put(MovieContract.MoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE, vote_average);
        testValues.put(MovieContract.MoviesDatabase.COLUMN_MOVIE_RELEASE_DATE, release_date);
        testValues.put(MovieContract.MoviesDatabase.COLUMN_MOVIE_POSTER, movie_poster);
        testValues.put(MovieContract.MoviesDatabase.COLUMN_MOVIE_OVERVIEW, movie_overview);

        return testValues;
    }


    public static ContentValues createFavoriteMoviesDatabaseValues(long movie_ID) {

        ContentValues testValues = new ContentValues();
        testValues.put(MovieContract.FavoriteMovie.COLUMN_FAVORITE_MOVIES_ID, movie_ID);

        return testValues;
    }

    public static ContentValues createMovieReviewsDatabaseValues(long movie_ID, String movie_reviews, String review_author) {

        ContentValues testValues = new ContentValues();
        testValues.put(MovieContract.MovieReviewsDB.COLUMN_MOVIE_ID, movie_ID);
        testValues.put(MovieContract.MovieReviewsDB.COLUMN_MOVIE_REVIEWS, movie_reviews);
        testValues.put(MovieContract.MovieReviewsDB.COLUMN_REVIEW_AUTHOR_NAME, review_author);
        return testValues;
    }
}
