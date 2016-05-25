package com.example.pavan.moviesapp;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pavan.moviesapp.MovieSQLiteDatabase.DatabaseInsertions;
import com.example.pavan.moviesapp.MovieSQLiteDatabase.DeleteMovieRecords;
import com.example.pavan.moviesapp.MovieSQLiteDatabase.ReadDatabaseRecords;
import com.example.pavan.moviesapp.MovieSQLiteDatabase.checkDatabaseRecords;
import com.squareup.picasso.Picasso;

import java.io.File;

import Utils.AndroidUtil;


public class MovieDetail_tab extends Fragment {


    private final String LOG_TAG = getClass().getSimpleName();

    TextView release_date;
    TextView movie_overview;
    TextView movie_title;
    TextView vote_average;
    TextView mark_favorite_button;
    private ImageView Poster;
    private String poster_path;
    private String releaseDate;
    private String movieOverview;
    private String movieTitle;
    private String voteAverage;
    private String preference;
    private Long movieID;
    private String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";
    private checkDatabaseRecords checkDatabaseRecords;
    private DeleteMovieRecords deleteMovieRecords;
    private DatabaseInsertions databaseInsertions;

    private AndroidUtil androidUtil;
    private Picasso picasso;
    private ReadDatabaseRecords readDatabaseRecords;
    private String confirmation, confirmation2;


    public MovieDetail_tab() {
        // Required empty public constructor
    }

    public static MovieDetail_tab newInstance(String clickedPoster, String movieTitle, String releaseDate, String movieOverView, String voteAverage, Long movieID, String Preference) {
        MovieDetail_tab fragment = new MovieDetail_tab();
        Bundle args = new Bundle();

        args.putString("posterURL", clickedPoster);
        args.putString("releaseDate", releaseDate);
        args.putString("movieOverview", movieOverView);
        args.putString("movieTitle", movieTitle);
        args.putString("voteAverage", voteAverage);
        args.putLong("movieID", movieID);
        args.putString("sortPreference", Preference);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i(LOG_TAG, "onSaveInstanceState fired");

        outState.putString("posterURL", poster_path);
        outState.putString("releaseDate", releaseDate);
        outState.putString("movieOverview", movieOverview);
        outState.putString("movieTitle", movieTitle);
        outState.putString("voteAverage", voteAverage);
        outState.putLong("movieID", movieID);
        outState.putString("sortPreference", preference);

        Log.i(LOG_TAG, " outState bundle of savedInstanceState() :  " + outState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i(LOG_TAG, "onActivityCreated fired");

        Log.i(LOG_TAG, "savedInstanceState of onActivityCreated() :  " + savedInstanceState);

        if (savedInstanceState != null) {
            poster_path = savedInstanceState.getString("posterURL");
            releaseDate = savedInstanceState.getString("releaseDate");
            movieOverview = savedInstanceState.getString("movieOverview");
            movieTitle = savedInstanceState.getString("movieTitle");
            voteAverage = savedInstanceState.getString("voteAverage");
            movieID = savedInstanceState.getLong("movieID");
            preference = savedInstanceState.getString("sortPreference");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            poster_path = getArguments().getString("posterURL");
            releaseDate = getArguments().getString("releaseDate");
            movieOverview = getArguments().getString("movieOverview");
            movieTitle = getArguments().getString("movieTitle");
            voteAverage = getArguments().getString("voteAverage");
            movieID = getArguments().getLong("movieID");
            preference = getArguments().getString("sortPreference");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail_tab, container, false);

        Poster = (ImageView) view.findViewById(R.id.movie_poster_in_movie_detail_activity);

        checkDatabaseRecords = new checkDatabaseRecords(getContext());
        databaseInsertions = new DatabaseInsertions(getContext());

        deleteMovieRecords = new DeleteMovieRecords(getContext());
        readDatabaseRecords = new ReadDatabaseRecords(getContext());
        androidUtil = new AndroidUtil(getContext());
        picasso = Picasso.with(getContext());


        release_date = (TextView) view.findViewById(R.id.release_year);
        movie_overview = (TextView) view.findViewById(R.id.movie_overview);
        movie_title = (TextView) view.findViewById(R.id.movie_title);
        vote_average = (TextView) view.findViewById(R.id.vote_average);
        mark_favorite_button = (TextView) view.findViewById(R.id.mark_favorite);



        confirmation = checkDatabaseRecords.checkAllMovieRecordsWithDBRecords(movieID);

        if (confirmation == "already marked favorite")
            FavoriteButtonMarked();
        else if (confirmation == "not marked yet")
            FavoriteButtonNotMarked();


        mark_favorite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (confirmation == "already marked favorite") {

                    Toast.makeText(getContext(), "Movie is already added in your favorites.", Toast.LENGTH_SHORT).show();
                } else {
                    confirmation = "already marked favorite";
                    confirmation2 = databaseInsertions.insertDataIntoMoviesTable(movieID, movieTitle, Double.parseDouble(voteAverage), releaseDate, poster_path, movieOverview);
                    picasso.load(BASE_POSTER_URL + poster_path)
                            .resize(185 * 2, 278 * 2).into(androidUtil.getTarget(BASE_POSTER_URL + poster_path, movieID));
                    if (confirmation2 == "inserted successfully")
                    {
                        FavoriteButtonMarked();
                        Toast.makeText(getContext(), "added into your favorite movies list.", Toast.LENGTH_SHORT).show();
                    } else {
                        FavoriteButtonNotMarked();

                        Toast.makeText(getContext(), " problem occurred while adding it to your favorite movies list.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        if (preference.equals("favorites") || androidUtil.isOnline() != true)
            picasso
                    .load(Uri.fromFile(new File(poster_path)))
                    .noFade()
                    .resize(185 * 2, 278 * 2)
                    .into(Poster);

        else if (androidUtil.isOnline())
            picasso
                    .load(BASE_POSTER_URL + poster_path)
                    .resize(165 * 2, 250 * 2)
                    .into(Poster);


        release_date.setText(releaseDate);
        movie_overview.setText(movieOverview);
        movie_title.setText(movieTitle);
        vote_average.setText(voteAverage + "/10");


        return view;
    }

    public void FavoriteButtonNotMarked() {
        mark_favorite_button.setText("Mark as Favorite");
        mark_favorite_button.setBackgroundColor(Color.parseColor("#029789"));
    }

    public void FavoriteButtonMarked() {
        mark_favorite_button.setText("Marked Favorite");
        mark_favorite_button.setBackgroundColor(Color.parseColor("#FF160A"));

        mark_favorite_button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                androidUtil.deleteMoviePosterFromFileSystem(poster_path);
                String confirmation = deleteMovieRecords.deleteFavoriteMovieRecord(movieID);
                if (confirmation == "movie record deleted") {
                    FavoriteButtonNotMarked();
                    Toast.makeText(getContext(), "Movie is removed from your favorite movies list", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getContext(), "Failed to remove movie from your favorite movies list", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

    }
}



