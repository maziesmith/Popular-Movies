package com.example.pavan.moviesapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {

    private Bundle bundle;
    private TextView release_date,movie_overview,movie_title,vote_average;
    private String poster_path,releaseDate,year[],movieOverview,movieTitle,voteAverage;
    private ImageView Poster;
    private String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

//        getActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);


        bundle = getIntent().getExtras();

        poster_path   = bundle.getString("posterURL");
        releaseDate   = bundle.getString("releaseDate");
        movieOverview = bundle.getString("movieOverview");
        movieTitle    = bundle.getString("movieTitle");
        voteAverage   = bundle.getString("voteAverage");

        Poster         = (ImageView)findViewById(R.id.movie_poster_in_movie_detail_activity);
        release_date   = (TextView) findViewById(R.id.release_year);
        movie_overview = (TextView) findViewById(R.id.movie_overview);
        movie_title    = (TextView)findViewById(R.id.movie_title);
        vote_average   = (TextView) findViewById(R.id.vote_average);

        System.out.println("poster path in movie detail fragment :" + poster_path);

        Picasso.with(getApplicationContext())
                .load(BASE_POSTER_URL + poster_path)
                .resize(175 * 2, 250 * 2)
                .into(Poster);

//        year = releaseDate.split("-");
//        System.out.println("year : " + year[0] + " month : "+ year[1] + "date : " +year[2]);
        release_date.setText(releaseDate);
        movie_overview.setText(movieOverview);
        movie_title.setText(movieTitle);
        vote_average.setText(voteAverage+"/10");

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
            return true;}


        return super.onOptionsItemSelected(item);

    }
}
