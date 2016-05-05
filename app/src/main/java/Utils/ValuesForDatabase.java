package Utils;

import android.content.ContentValues;

import com.example.pavan.moviesapp.MovieSQLiteDatabase.MovieContract;

/**
 * Created by pavan on 4/28/2016.
 */
public class ValuesForDatabase {


    private static ContentValues MovieTableValues = new ContentValues();
    private static ContentValues FavoriteMoviesTableValues = new ContentValues();
    private static ContentValues MovieReviewsTableValues = new ContentValues();
    private final String LOG_TAG = getClass().getSimpleName();

    public ContentValues getMovieTableValues() {
        return MovieTableValues;
    }

    public void setMovieTableValues(ContentValues movieTableValues) {
        MovieTableValues = movieTableValues;
    }

    public ContentValues getFavoriteMoviesTableValues() {
        return FavoriteMoviesTableValues;
    }

    public void setFavoriteMoviesTableValues(ContentValues favoriteMoviesTableValues) {
        FavoriteMoviesTableValues = favoriteMoviesTableValues;
    }

    public ContentValues getMovieReviewsTableValues() {
        return MovieReviewsTableValues;
    }

    public void setMovieReviewsTableValues(ContentValues movieReviewsTableValues) {
        MovieReviewsTableValues = movieReviewsTableValues;
    }

    public void createMoviesDatabaseValues(long movie_ID, String movie_title, double vote_average,
                                                           String release_date, String movie_poster, String movie_overview) {


        MovieTableValues.put(MovieContract.MoviesDatabase.COLUMN_MOVIE_ID, movie_ID);
        MovieTableValues.put(MovieContract.MoviesDatabase.COLUMN_MOVIE_TITLE, movie_title);
        MovieTableValues.put(MovieContract.MoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE, vote_average);
        MovieTableValues.put(MovieContract.MoviesDatabase.COLUMN_MOVIE_RELEASE_DATE, release_date);
        MovieTableValues.put(MovieContract.MoviesDatabase.COLUMN_MOVIE_POSTER, movie_poster);
        MovieTableValues.put(MovieContract.MoviesDatabase.COLUMN_MOVIE_OVERVIEW, movie_overview);

        setMovieTableValues(MovieTableValues);
        //        return testValues;
    }


    public void createFavoriteMoviesDatabaseValues(long movie_ID) {


        FavoriteMoviesTableValues.put(MovieContract.FavoriteMovie.COLUMN_FAVORITE_MOVIES_ID, movie_ID);

        setFavoriteMoviesTableValues(FavoriteMoviesTableValues);
//        return testValues;
    }

    public void createMovieReviewsDatabaseValues(long movie_ID, String movie_reviews, String review_author) {


        MovieReviewsTableValues.put(MovieContract.MovieReviewsDB.COLUMN_MOVIE_ID, movie_ID);
        MovieReviewsTableValues.put(MovieContract.MovieReviewsDB.COLUMN_MOVIE_REVIEWS, movie_reviews);
        MovieReviewsTableValues.put(MovieContract.MovieReviewsDB.COLUMN_REVIEW_AUTHOR_NAME, review_author);

        setMovieReviewsTableValues(MovieReviewsTableValues);
//        return testValues;
    }
}
