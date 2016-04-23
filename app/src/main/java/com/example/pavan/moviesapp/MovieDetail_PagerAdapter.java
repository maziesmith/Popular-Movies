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
    private static String clickedPoster;
    private static String releaseDate;
    private static String movieOverView;
    private static String movieTitle;
    private static String voteAverage;
    private static Long movie_id_for_trailers;
    final int PAGE_COUNT = 3;
    private Bundle bundle = new Bundle();
    private String tabTitles[] = new String[]{"Details", "Trailers", "Reviews"};
    private Context context;


    public MovieDetail_PagerAdapter(FragmentManager fm) {
        super(fm);

    }

    public MovieDetail_PagerAdapter(FragmentManager fm, Context context, Bundle bundle) {
        super(fm);
        this.context = context;
        this.bundle = bundle;
        clickedPoster = bundle.getString("posterURL");
        releaseDate = bundle.getString("releaseDate");
        movieOverView = bundle.getString("movieOverview");
        movieTitle = bundle.getString("movieTitle");
        voteAverage = bundle.getString("voteAverage");
        movie_id_for_trailers = bundle.getLong("movieID");
    }

    public static String getClickedPoster() {
        return clickedPoster;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getMovieOverView() {
        return movieOverView;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public Long getMovie_id_for_trailers() {
        return movie_id_for_trailers;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MovieDetail_tab.newInstance(getClickedPoster(), getMovieTitle(), getReleaseDate(), getMovieOverView(), getVoteAverage());
            case 1:
                return Trailers_tab.newInstance(getMovie_id_for_trailers());
            case 2:
                return Reviews_tab.newInstance(getMovie_id_for_trailers());
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
