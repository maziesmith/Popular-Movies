package com.example.pavan.moviesapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;



public class MovieDetail_tab extends Fragment {


    private ImageView Poster;
    private String poster_path;
    private String releaseDate;
    private String movieOverview;
    private String movieTitle;
    private String voteAverage;

    private ListView trailersListView;
    private String BASE_TRAILERS_AND_REVIEWS_URL = "http://api.themoviedb.org/3/movie/";
    private TextView release_date, movie_overview, movie_title, vote_average;
    private String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";


    public MovieDetail_tab() {
        // Required empty public constructor
    }

    public static MovieDetail_tab newInstance(String clickedPoster, String movieTitle, String releaseDate, String movieOverView, String voteAverage) {
        MovieDetail_tab fragment = new MovieDetail_tab();
        Bundle args = new Bundle();

        args.putString("posterURL", clickedPoster);
        args.putString("releaseDate", releaseDate);
        args.putString("movieOverview", movieOverView);
        args.putString("movieTitle", movieTitle);
        args.putString("voteAverage", voteAverage);

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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail_tab, container, false);


        Poster = (ImageView) view.findViewById(R.id.movie_poster_in_movie_detail_activity);

//        trailersListView = (ListView) findViewById(R.id.trailers_list_view);
//        reviews_list_view = (ListView) findViewById(R.id.reviews_list_view);

        release_date = (TextView) view.findViewById(R.id.release_year);
        movie_overview = (TextView) view.findViewById(R.id.movie_overview);
        movie_title = (TextView) view.findViewById(R.id.movie_title);
        vote_average = (TextView) view.findViewById(R.id.vote_average);


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
}
