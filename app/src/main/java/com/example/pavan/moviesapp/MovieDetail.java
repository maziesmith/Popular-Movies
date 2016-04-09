package com.example.pavan.moviesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pavan.moviesapp.NetworkActivity.MovieReviewsResponse;
import com.example.pavan.moviesapp.NetworkActivity.MovieTrailerData;
import com.example.pavan.moviesapp.NetworkActivity.MovieTrailerResponse;
import com.example.pavan.moviesapp.NetworkActivity.RetrofitAPI;
import com.example.pavan.moviesapp.NetworkActivity.ReviewsData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


//import com.example.pavan.moviesapp.NetworkActivity.MovieTrailersResponse;

public class MovieDetail extends AppCompatActivity {


    Intent YOUTUBE_INTENT;
    private Long movieID;
    private Bundle bundle;
    private ImageView Poster;
    private TextView release_date,movie_overview,movie_title,vote_average;
    private ListView trailersListView, reviews_list_view;
    private String poster_path, releaseDate, movieOverview, movieTitle, voteAverage;
    private String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";
    private String BASE_TRAILERS_AND_REVIEWS_URL = "http://api.themoviedb.org/3/movie/";
    private String BASE_YOUTUBE_URL = "http://www.youtube.com/";
    private List<MovieTrailerResponse> movieTrailerResponses;
    private List<MovieReviewsResponse> movieReviewsResponses;
    private ReviewsData reviewsData = new ReviewsData();
    private FetchMovieData fetchMovieData = new FetchMovieData(getApplication(), null);
    private MovieTrailerData movieTrailerData = new MovieTrailerData();
    private MovieTrailerAdapter movieTrailerAdapter = new MovieTrailerAdapter(this);
    private MovieReviewsAdapter movieReviewsAdapter = new MovieReviewsAdapter(this);
//    private MovieTrailerResponse movieTrailerResponse = new MovieTrailerResponse();


    private Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_TRAILERS_AND_REVIEWS_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();
    protected RetrofitAPI api = retrofit.create(RetrofitAPI.class);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);



        bundle = getIntent().getExtras();

        poster_path   = bundle.getString("posterURL");
        releaseDate   = bundle.getString("releaseDate");
        movieOverview = bundle.getString("movieOverview");
        movieTitle    = bundle.getString("movieTitle");
        voteAverage   = bundle.getString("voteAverage");
        movieID = bundle.getLong("movieID");

        System.out.println("movieID :  " + movieID);


        Poster         = (ImageView)findViewById(R.id.movie_poster_in_movie_detail_activity);

        trailersListView = (ListView) findViewById(R.id.trailers_list_view);
        reviews_list_view = (ListView) findViewById(R.id.reviews_list_view);

        release_date   = (TextView) findViewById(R.id.release_year);
        movie_overview = (TextView) findViewById(R.id.movie_overview);
        movie_title    = (TextView)findViewById(R.id.movie_title);
        vote_average   = (TextView) findViewById(R.id.vote_average);



        Picasso.with(getApplicationContext())
                .load(BASE_POSTER_URL + poster_path)
                .resize(175 * 2, 250 * 2)
                .into(Poster);


        release_date.setText(releaseDate);
        movie_overview.setText(movieOverview);
        movie_title.setText(movieTitle);
        vote_average.setText(voteAverage + "/10");

        fetchTrailerData();
        fetchReviewsData();
    }

    public void fetchTrailerData() {



        Call<MovieTrailerData> responseCall = api.TRAILERS_DATA_CALL(movieID, fetchMovieData.API_KEY);


        responseCall.enqueue(new Callback<MovieTrailerData>() {
            @Override
            public void onResponse(Response<MovieTrailerData> response, Retrofit retrofit) {

                if (response.isSuccess()) {
                    movieTrailerData = response.body();

                    movieTrailerResponses = movieTrailerData.getResults();

                    movieTrailerAdapter.movieTrailerResponseList = movieTrailerResponses;
                    ArrayList<String> Key = new ArrayList<String>();
                    ArrayList<String> id = new ArrayList<String>();
                    ArrayList<Long> size = new ArrayList<Long>();
                    ArrayList<String> iso_3166_1 = new ArrayList<String>();
                    ArrayList<String> Name = new ArrayList<String>();
                    ArrayList<String> iso_639_1 = new ArrayList<String>();


                    System.out.println("movieTrailerData.getResults().size() : " + movieTrailerData.getResults().size());

                    for (final MovieTrailerResponse movieTrailerResponses1 : movieTrailerResponses) {
                        System.out.println("----------------------------------------------------------");

                        System.out.println("movieTrailerResponse.getId() : " + movieTrailerResponses1.getId());
                        id.add(movieTrailerResponses1.getId());
                        System.out.println("movieTrailerResponse.getIso31661() : " + movieTrailerResponses1.getIso_3166_1());
                        iso_3166_1.add(movieTrailerResponses1.getIso_3166_1());
                        System.out.println("movieTrailerResponse.getIso6391() : " + movieTrailerResponses1.getIso_639_1());
                        iso_639_1.add(movieTrailerResponses1.getIso_639_1());
                        System.out.println("movieTrailerResponse.getKey() : " + movieTrailerResponses1.getKey());
                        Key.add(movieTrailerResponses1.getKey());
                        System.out.println("movieTrailerResponse.getName() : " + movieTrailerResponses1.getName());
                        Name.add(movieTrailerResponses1.getName());
                        System.out.println("movieTrailerResponse.getSite() : " + movieTrailerResponses1.getSite());

                        System.out.println("movieTrailerResponses1.getSize() : " + movieTrailerResponses1.getSize());
                        size.add(movieTrailerResponses1.getSize());
                        System.out.println("movieTrailerResponses1.getType() : " + movieTrailerResponses1.getType());

                        System.out.println("----------------------------------------------------------");
                        trailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                YOUTUBE_INTENT = new Intent(Intent.ACTION_VIEW, Uri.parse(BASE_YOUTUBE_URL).buildUpon()
                                        .appendPath("vi").appendPath(movieTrailerResponses1.getKey()).build());
                                startActivity(YOUTUBE_INTENT);
                            }
                        });

                        movieTrailerAdapter.noOfTrailers = movieTrailerData.getResults().size();
                }

                    System.out.println("KEY ArrayList : " + Key);
                    System.out.println(size);
                    System.out.println(Name);
                    System.out.println(iso_639_1);
                    System.out.println(iso_3166_1);
                    System.out.println(id);


                    trailersListView.setAdapter(movieTrailerAdapter);


                    System.out.println(">>>>>>>>>>>>" + movieTrailerData.getResults());
                    System.out.println("response results getID : " + movieTrailerData.getId());
                    System.out.println("response raw : " + response.raw());
                    System.out.println("response body : " + response.body());


                }
            }

            @Override
            public void onFailure(Throwable t) {

                System.out.println("failed to fetch trailer data....");

            }
        });

    }

    public void fetchReviewsData() {

        Call<ReviewsData> reviewsDataCall = api.REVIEWS_DATA_CALL(movieID, fetchMovieData.API_KEY);

        reviewsDataCall.enqueue(new Callback<ReviewsData>() {
            @Override
            public void onResponse(Response<ReviewsData> response, Retrofit retrofit) {

                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("response status reviews : " + response.isSuccess());

                System.out.println("response.raw() reviews : " + response.raw());

                System.out.println("response.body() reviews : " + response.body());

                System.out.println("response.message() reviews : " + response.message());

                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                reviewsData = response.body();
                movieReviewsResponses = reviewsData.getReviewsResponse();

                movieReviewsAdapter.noOfReviews = reviewsData.getReviewsResponse().size();
                System.out.println("response.body().getReviewsResponse() : " + response.body().getReviewsResponse().size());


                System.out.println("/////////////////////////////////////////////////////////////");

                System.out.println("reviewsData.getId() : " + reviewsData.getId());
                System.out.println("reviewsData.getPage() : " + reviewsData.getPage());
                System.out.println("reviewsData.getTotal_pages() : " + reviewsData.getTotal_pages());
                System.out.println("reviewsData.getReviewsResponse() : " + reviewsData.getReviewsResponse());

                System.out.println("//////////////////////////////////////////////////////////////////////////////");


                System.out.println("------------------------------------------------------------------------");

                for (MovieReviewsResponse movieReviewsResponse : movieReviewsResponses) {
                    System.out.println("movieReviewsResponse.getAuthor() : " + movieReviewsResponse.getAuthor());
                    movieReviewsAdapter.author_name.add(movieReviewsResponse.getAuthor());
                    System.out.println("movieReviewsResponse.getContent() : " + movieReviewsResponse.getContent());
                    movieReviewsAdapter.author_review.add(movieReviewsResponse.getContent());
                    System.out.println("movieReviewsResponse.getId() : " + movieReviewsResponse.getId());
                    System.out.println("movieReviewsResponse.getUrl() : " + movieReviewsResponse.getUrl());

                }
                System.out.println("---------------------------------------------------------------------------");
                reviews_list_view.setAdapter(movieReviewsAdapter);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("failed to fetch reviews data....");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings){

            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);

    }
}
