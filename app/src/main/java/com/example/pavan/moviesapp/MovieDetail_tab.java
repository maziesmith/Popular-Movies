package com.example.pavan.moviesapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pavan.moviesapp.MovieSQLiteDatabase.DatabaseInsertions;
import com.example.pavan.moviesapp.MovieSQLiteDatabase.DeleteMovieRecords;
import com.example.pavan.moviesapp.MovieSQLiteDatabase.ReadDatabaseRecords;
import com.example.pavan.moviesapp.MovieSQLiteDatabase.ValuesForDatabase;
import com.example.pavan.moviesapp.MovieSQLiteDatabase.checkDatabaseRecords;
import com.squareup.picasso.Picasso;

import java.io.File;

import Utils.AndroidUtil;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieDetail_tab extends Fragment {


    private final String LOG_TAG = getClass().getSimpleName();
    @BindView(R.id.release_year)
    TextView release_date;
    @BindView(R.id.movie_overview)
    TextView movie_overview;
    @BindView(R.id.movie_title)
    TextView movie_title;
    @BindView(R.id.vote_average)
    TextView vote_average;
    @BindView(R.id.mark_favorite)
    TextView mark_favorite_button;
    private ImageView Poster;
    private String poster_path;
    private String releaseDate;
    private String movieOverview;
    private String movieTitle;
    private String voteAverage;
    private Long movieID;
    private ListView trailersListView;
    private String BASE_TRAILERS_AND_REVIEWS_URL = "http://api.themoviedb.org/3/movie/";
    //    private TextView release_date, movie_overview, movie_title, vote_average, mark_favorite_button;
    private String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";
    private checkDatabaseRecords checkDatabaseRecords;
    private DeleteMovieRecords deleteMovieRecords;
    private DatabaseInsertions databaseInsertions;
    private ValuesForDatabase valuesForDatabase;
    private AndroidUtil androidUtil;
    private Picasso picasso;
    private ReadDatabaseRecords readDatabaseRecords;
    private Uri uri;
    private String confirmation;

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

        ButterKnife.bind(view);

        Poster = (ImageView) view.findViewById(R.id.movie_poster_in_movie_detail_activity);

        checkDatabaseRecords = new checkDatabaseRecords(getContext());
        databaseInsertions = new DatabaseInsertions(getContext());
        valuesForDatabase = new ValuesForDatabase();
        deleteMovieRecords = new DeleteMovieRecords(getContext());
        readDatabaseRecords = new ReadDatabaseRecords(getContext());
        androidUtil = new AndroidUtil(getContext());
        picasso = Picasso.with(getContext());


        release_date = (TextView) view.findViewById(R.id.release_year);
        movie_overview = (TextView) view.findViewById(R.id.movie_overview);
        movie_title = (TextView) view.findViewById(R.id.movie_title);
        vote_average = (TextView) view.findViewById(R.id.vote_average);
        mark_favorite_button = (TextView) view.findViewById(R.id.mark_favorite);

//        deleteMovieRecords.deleteAllFavoriteMovieRecords();

        valuesForDatabase.createMoviesDatabaseValues(movieID, movieTitle, Double.parseDouble(voteAverage), releaseDate, poster_path, movieOverview);
//        readDatabaseRecords.fetchAllMovieDatabaseRecords();
        confirmation = checkDatabaseRecords.checkAllMovieRecordsWithDBRecords(movieID);

        if (confirmation == "already marked favorite")
            FavoriteButtonMarked();
        else if (confirmation == "not marked yet")
            FavoriteButtonNotMarked();



        mark_favorite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                long rowId = databaseInsertions.insertDataIntoMoviesTable(movieID,movieTitle,Double.parseDouble(voteAverage),releaseDate,poster_path,movie_overview);
                String confirmation = databaseInsertions.insertDataIntoMoviesTable(movieID, movieTitle, Double.parseDouble(voteAverage), releaseDate, poster_path, movieOverview);
//                if (rowId != -1) {

                picasso.load(BASE_POSTER_URL + poster_path)
                        .resize(185 * 2, 278 * 2).into(androidUtil.getTarget(BASE_POSTER_URL + poster_path, movieID));


                if (confirmation == "inserted successfully")
//                    Log.i(LOG_TAG, "row id in detail tab : " + rowId);
                {
                    FavoriteButtonMarked();
                    Toast.makeText(getContext(), "added into your favorite movies list.", Toast.LENGTH_SHORT).show();
                } else {
                    FavoriteButtonNotMarked();
//                    Log.i(LOG_TAG, "row id of inserted record : " + rowId);
                    Toast.makeText(getContext(), " problem occurred while adding it to your favorite movies list.", Toast.LENGTH_SHORT).show();
                }

            }

        });


        if (androidUtil.isOnline())
            picasso
                .load(BASE_POSTER_URL + poster_path)
                .resize(165 * 2, 250 * 2)
                .into(Poster);
        else
            picasso
                    .load(Uri.fromFile(new File(poster_path)))
                    .noFade()
                    .resize(185 * 2, 278 * 2)
                    .into(Poster);


        release_date.setText(releaseDate);
        movie_overview.setText(movieOverview);
        movie_title.setText(movieTitle);
        vote_average.setText(voteAverage + "/10");


        return view;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    Intent shareMovieAndTrailersInfo() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Movie Title : " + getMovieTitle() + ", \n\n" + "Overview : " + getMovieOverview() +
                ",\n\n" + "Trailer : " + uri);

        return shareIntent;
    }

    public void FavoriteButtonNotMarked() {
        mark_favorite_button.setText("Mark as Favorite");
        mark_favorite_button.setBackgroundColor(Color.parseColor("#029789"));
        mark_favorite_button.cancelLongPress();
    }

    public void FavoriteButtonMarked() {
        mark_favorite_button.setText("Marked Favorite");
        mark_favorite_button.setBackgroundColor(Color.parseColor("#FF160A"));

        mark_favorite_button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
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



