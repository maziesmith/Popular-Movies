package com.example.pavan.moviesapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import Utils.DatabaseInsertions;
import Utils.checkDatabaseRecords;



public class MovieDetail_tab extends Fragment {


    private final String LOG_TAG = getClass().getSimpleName();

    private ImageView Poster;
    private String poster_path;
    private String releaseDate;
    private String movieOverview;
    private String movieTitle;
    private String voteAverage;
    private Long movieID;

    private ListView trailersListView;
    private String BASE_TRAILERS_AND_REVIEWS_URL = "http://api.themoviedb.org/3/movie/";
    private TextView release_date, movie_overview, movie_title, vote_average, mark_favorite_button;
    private String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";

    private checkDatabaseRecords checkDatabaseRecords;
    private DatabaseInsertions databaseInsertions;

    public MovieDetail_tab() {
        // Required empty public constructor
    }

    public static MovieDetail_tab newInstance(String clickedPoster, String movieTitle, String releaseDate, String movieOverView, String voteAverage, Long movieID) {
        MovieDetail_tab fragment = new MovieDetail_tab();
        Bundle args = new Bundle();

        args.putString("posterURL", clickedPoster);
        args.putString("releaseDate", releaseDate);
        args.putString("movieOverview", movieOverView);
        args.putString("movieTitle", movieTitle);
        args.putString("voteAverage", voteAverage);
        args.putLong("movieID", movieID);

        fragment.setArguments(args);
        return fragment;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieOverview() {
        return movieOverview;
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail_tab, container, false);


        Poster = (ImageView) view.findViewById(R.id.movie_poster_in_movie_detail_activity);

        checkDatabaseRecords = new checkDatabaseRecords(getContext());
        databaseInsertions = new DatabaseInsertions(getContext());

        release_date = (TextView) view.findViewById(R.id.release_year);
        movie_overview = (TextView) view.findViewById(R.id.movie_overview);
        movie_title = (TextView) view.findViewById(R.id.movie_title);
        vote_average = (TextView) view.findViewById(R.id.vote_average);
        mark_favorite_button = (TextView) view.findViewById(R.id.mark_favorite);

        String confirmation = checkDatabaseRecords.checkFavoriteMovieRecords(movieID);

        if (confirmation == "already marked favorite")
            FavoriteButtonMarked();
        else if (confirmation == "not marked yet")
            FavoriteButtonUnmarked();


        mark_favorite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long rowId = databaseInsertions.insertDataIntoFavoriteMoviesTable();
                if (rowId != -1) {
                    Log.i(LOG_TAG, "row id in detail tab : " + rowId);
                    Toast.makeText(getContext(), "added into your favorite movies list.", Toast.LENGTH_SHORT).show();
                } else {
                    FavoriteButtonUnmarked();
                    Log.i(LOG_TAG, "row id of inserted record : " + rowId);
                    Toast.makeText(getContext(), " problem occurred while adding it to your favorite movies list.", Toast.LENGTH_SHORT).show();
                }

            }

        });


        Picasso.with(getContext())
                .load(BASE_POSTER_URL + poster_path)
                .resize(165 * 2, 250 * 2)
                .into(Poster);


        release_date.setText(releaseDate);
        movie_overview.setText(movieOverview);
        movie_title.setText(movieTitle);
        vote_average.setText(voteAverage + "/10");


        return view;
    }

    public void FavoriteButtonUnmarked() {
        mark_favorite_button.setText("Mark as Favorite");
        mark_favorite_button.setBackgroundColor(Color.parseColor("#029789"));
        mark_favorite_button.setEnabled(true);
    }

    public void FavoriteButtonMarked() {
        mark_favorite_button.setText("Marked Favorite");
        mark_favorite_button.setBackgroundColor(Color.parseColor("#969696"));
        mark_favorite_button.setEnabled(false);
    }
}



