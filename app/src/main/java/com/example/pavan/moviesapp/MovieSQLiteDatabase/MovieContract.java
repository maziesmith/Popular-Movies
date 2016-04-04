package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.provider.BaseColumns;

/**
 * Created by pavan on 3/25/2016.
 */
public class MovieContract {

    public class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies_info";

        public static final String MOVIE_TITLE = "movie_title";

        public static final String MOVIE_VOTE_AVERAGE = "movie_vote_average";

        public static final String MOVIE_RELEASE_DATE = "movie_release_date";

        public static final String MOVIE_POSTER = "movie_poster";

        public static final String MOVIE_OVERVIEW = "movie_overview";


    }

}
