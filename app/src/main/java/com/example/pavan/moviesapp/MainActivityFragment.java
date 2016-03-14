package com.example.pavan.moviesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private SharedPreferences sortOrder;
    Bundle bundle;
    Intent movieDetail;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);



        bundle = new Bundle();

        movieDetail  = new Intent(getContext(),MovieDetail.class);

        final GridView gridView = (GridView) rootView.findViewById(R.id.movie_grid_view);

        final FetchMovieData fetchMovieData = new FetchMovieData(getContext(),gridView);

        fetchMovieData.execute("POPULARITY.desc");

//        new FetchMovieData(getContext(),gridView).execute(sortOrder);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"position : " + position, Toast.LENGTH_SHORT).show();

                Log.i("poster string", fetchMovieData.Posters.get(position));

                bundle.putString("posterURL", fetchMovieData.Posters.get(position));
                new MovieDetailFragment().setArguments(bundle);
                startActivity(movieDetail);
            }
        });
        return rootView;
    }


}
