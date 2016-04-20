package com.example.pavan.moviesapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by pavan on 4/18/2016.
 */
public class MovieDetail_PagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"Movie Details", "Movie Trailers", "Movie Reviews"};
    private Context context;
    private Bundle bundle;
    private String clickedPoster;
    private String releaseDate;
    private String movieOverView;
    private String movieTitle;
    private String voteAverage;
    private String sortByPrefValue;
    private Long movie_id_for_trailers;

    public MovieDetail_PagerAdapter(FragmentManager fm, Context context, String clickedPoster, String releaseDate, String movieOverView, String movieTitle, String voteAverage, Long movie_id_for_trailers) {
        super(fm);
        this.context = context;
        this.clickedPoster = clickedPoster;
        this.releaseDate = releaseDate;
        this.movieOverView = movieOverView;
        this.movieTitle = movieTitle;
        this.voteAverage = voteAverage;
        this.movie_id_for_trailers = movie_id_for_trailers;
    }


    public MovieDetail_PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public MovieDetail_PagerAdapter(FragmentManager fm, Context context, Bundle bundle) {
        super(fm);
        this.context = context;
        this.bundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MovieDetail_tab.newInstance(clickedPoster, movieTitle, releaseDate, movieOverView, voteAverage);
            case 1:
                return Trailers_tab.newInstance(movie_id_for_trailers);
            case 2:
                return Reviews_tab.newInstance(movie_id_for_trailers);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
