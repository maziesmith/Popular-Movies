package com.example.pavan.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pavan.moviesapp.NetworkActivity.MovieTrailerData;
import com.example.pavan.moviesapp.NetworkActivity.RetrofitAPI;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


//import com.example.pavan.moviesapp.NetworkActivity.MovieTrailersResponse;

public class MovieDetail extends AppCompatActivity {


    private int movieID;
    private Bundle bundle;
    private TextView release_date,movie_overview,movie_title,vote_average;
    private String poster_path, releaseDate, movieOverview, movieTitle, voteAverage, movieReviewsInfo, movieTrailerInfo;
    private ImageView Poster;
    private String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";
    private String BASE_TRAILERS_AND_REVIEWS_URL = "http://api.themoviedb.org/3/movie/";


    private FetchMovieTrailerAndReviewInfo trailerAndReviewInfo = new FetchMovieTrailerAndReviewInfo();
    private FetchMovieData fetchMovieData = new FetchMovieData(getApplication(), null);
    private MovieTrailerData trailerData = new MovieTrailerData();


    private OkHttpClient okHttpClient = new OkHttpClient();
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_TRAILERS_AND_REVIEWS_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build();
    private RetrofitAPI api = retrofit.create(RetrofitAPI.class);

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
        movieID = bundle.getInt("movieID");

        System.out.println("movieID :  " + movieID);


//        fetchTrailerData();

        Poster         = (ImageView)findViewById(R.id.movie_poster_in_movie_detail_activity);
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
//        fetchReviewsData();

//        System.out.println("trailerData.getId() : " + trailerData.getId());
//        System.out.println("getTrailerData()  : " + trailerData.getTrailerData());
//        System.out.println("getIso_639_1() : " + trailerData.getTrailerData());

    }

    public void fetchTrailerData() {

        okHttpClient.interceptors().add(new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                com.squareup.okhttp.Response response = chain.proceed(chain.request());
                return response;
            }
        });

        Call<MovieTrailerData> responseCall = api.TRAILERS_DATA_CALL(movieID, fetchMovieData.API_KEY);


        responseCall.enqueue(new Callback<MovieTrailerData>() {
            @Override
            public void onResponse(Response<MovieTrailerData> response, Retrofit retrofit) {
                System.out.println("response status : " + response.isSuccess());

                System.out.println("response body : " + response.body());

                System.out.println("response msg : " + response.message());
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("failed.......");
            }
        });


    }

    public void fetchReviewsData() {

        api.REVIEWS_DATA_CALL(movieID, fetchMovieData.API_KEY);
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
