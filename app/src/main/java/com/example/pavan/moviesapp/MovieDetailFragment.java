package com.example.pavan.moviesapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {



    ImageView Poster;

    String poster_path;
    String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";

    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);


        poster_path = getArguments().getString("posterURL"); // getting null



        Poster = (ImageView) rootView.findViewById(R.id.movie_poster_in_movie_detail_activity);

        System.out.println("poster path in movie detail fragment :" + poster_path);

        Picasso.with(getContext())
                .load(BASE_POSTER_URL + poster_path)
                .resize(185 * 2, 278 * 2)
                .into(Poster);





        return rootView;
    }
}
