package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.provider.BaseColumns;

/**
 * Created by pavan on 3/25/2016.
 */
public class MovieContract {

    public static final class MoviesDatabase implements BaseColumns {
        public static final String TABLE_NAME = "movies_database";

        public static final String COLUMN_MOVIE_ID = "movie_id"; // primary key

        public static final String COLUMN_MOVIE_TITLE = "movie_title";


        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "movie_vote_average";

        public static final String COLUMN_MOVIE_RELEASE_DATE = "movie_release_date";

        public static final String COLUMN_MOVIE_POSTER = "movie_poster";

        public static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";


    }

    public static final class FavoriteMovie implements BaseColumns {

        public static final String TABLE_NAME = "favorite_movies";

        public static final String COLUMN_FAVORITE_MOVIES_ID = "favorite_movies_id"; // foreign key
    }

    public static final class MovieReviewsDB implements BaseColumns {

        public static final String TABLE_NAME = "reviews_table";

        public static final String COLUMN_MOVIE_ID = "movies_id";

        public static final String COLUMN_MOVIE_REVIEWS = "reviews";

        public static final String COLUMN_REVIEW_AUTHOR_NAME = "author_name";
    }
}
