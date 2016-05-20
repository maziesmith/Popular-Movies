package com.example.pavan.moviesapp.MovieSQLiteDatabase;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by pavan on 3/25/2016.
 */
public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.example.pavan.moviesapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_FAVORITE_MOVIES = "favorite_movies_database";
    private final String LOG_TAG = getClass().getSimpleName();

    public static final class FavoriteMoviesDatabase implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE_MOVIES).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE_MOVIES;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE_MOVIES;


        public static final String TABLE_NAME = "favorite_movies_database";

        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static final String COLUMN_MOVIE_TITLE = "movie_title";


        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "movie_vote_average";

        public static final String COLUMN_MOVIE_RELEASE_DATE = "movie_release_date";

        public static final String COLUMN_MOVIE_POSTER = "movie_poster";

        public static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";


        public static Uri buildFavoriteMoviesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}
