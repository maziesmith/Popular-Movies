package com.example.pavan.moviesapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    //    JSONObject jsonObject;
//    JSONObject objectsInJSONArray;
//    ArrayAdapter<String> moviePoster;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

//        new ImageAdater.FetchMovieData().execute("POPULARITY.desc");



        final GridView gridView = (GridView) rootView.findViewById(R.id.movie_grid_view);


        new FetchMovieData().execute("POPULARITY.desc");

        gridView.setAdapter(new ImageAdapter(getContext()));

//        gridView.setAdapter(new ImageAdater());



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"position : " + position, Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }


}
