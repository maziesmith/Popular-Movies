package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.content.ContentValues;

/**
 * Created by pavan on 4/28/2016.
 */
public class ValuesForDatabase {


    private static ContentValues MovieTableValues = new ContentValues();
    private final String LOG_TAG = getClass().getSimpleName();


    public void createMoviesDatabaseValues(long movie_ID, String movie_title, double vote_average,
                                           String release_date, String movie_poster, String movie_overview) {


        MovieTableValues.put(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_ID, movie_ID);
        MovieTableValues.put(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_TITLE, movie_title);
        MovieTableValues.put(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_VOTE_AVERAGE, vote_average);
        MovieTableValues.put(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_RELEASE_DATE, release_date);
        MovieTableValues.put(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_POSTER, movie_poster);
        MovieTableValues.put(MovieContract.FavoriteMoviesDatabase.COLUMN_MOVIE_OVERVIEW, movie_overview);
        setMovieTableValues(MovieTableValues);
    }

    public ContentValues getMovieTableValues() {
        return MovieTableValues;
    }

    public void setMovieTableValues(ContentValues movieTableValues) {
        MovieTableValues = movieTableValues;
    }
}
