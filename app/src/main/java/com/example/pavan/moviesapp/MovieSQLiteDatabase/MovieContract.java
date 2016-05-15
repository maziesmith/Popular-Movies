package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.provider.BaseColumns;

/**
 * Created by pavan on 3/25/2016.
 */
public class MovieContract {

    private final String LOG_TAG = getClass().getSimpleName();

    public static final class FavoriteMoviesDatabase implements BaseColumns {

        public static final String TABLE_NAME = "favorite_movies_database";

        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static final String COLUMN_MOVIE_TITLE = "movie_title";


        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "movie_vote_average";

        public static final String COLUMN_MOVIE_RELEASE_DATE = "movie_release_date";

        public static final String COLUMN_MOVIE_POSTER = "movie_poster";

        public static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";


    }
}
