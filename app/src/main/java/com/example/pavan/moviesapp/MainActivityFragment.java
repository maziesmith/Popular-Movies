package com.example.pavan.moviesapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    Intent intent;
    String clickedPoster,releaseDate,movieOverView,movieTitle,voteAverage;
    int movie_id_for_trailers;
    AndroidUtil checkConnectivityStatus;
    AlertDialog.Builder builder;
    private SharedPreferences sortByPref;
    private String sortByPrefValue;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        intent = new Intent(getContext(),MovieDetail.class);
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final GridView gridView = (GridView) rootView.findViewById(R.id.movie_grid_view);

        final FetchMovieData fetchMovieData = new FetchMovieData(getContext(),gridView);

        checkConnectivityStatus = new AndroidUtil(getContext());
        builder = new AlertDialog.Builder(getContext());

        sortByPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        sortByPrefValue = sortByPref.getString(getString(R.string.SortBy_key),
                getString(R.string.SortBy_default));
        Log.i("sortByPrefValue", sortByPrefValue);

        if (checkConnectivityStatus.isOnline())
        fetchMovieData.execute(sortByPrefValue);
        else
            builder.setMessage("Please check your INTERNET Connection").setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            killActivity();
                        }
                    }).create().show();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "position : " + position, Toast.LENGTH_SHORT).show();

                Log.i("poster string", fetchMovieData.Posters.get(position));

                clickedPoster = fetchMovieData.Posters.get(position);
                releaseDate = fetchMovieData.releaseDates.get(position).toString();
                movieOverView = fetchMovieData.movieOverViews.get(position).toString();
                voteAverage   = fetchMovieData.voteAverage.get(position).toString();
                movieTitle   = fetchMovieData.titles.get(position).toString();
                movie_id_for_trailers = (int) fetchMovieData.movie_ids_for_trailers.get(position);

                Log.i("tapped movie id", String.valueOf((movie_id_for_trailers)));
                Log.i("release date 1",releaseDate);
                Log.i("movieOverview",movieOverView);

                intent.putExtra("posterURL", clickedPoster);
                intent.putExtra("releaseDate", releaseDate);
                intent.putExtra("movieOverview",movieOverView);
                intent.putExtra("movieTitle",movieTitle);
                intent.putExtra("voteAverage",voteAverage);
                intent.putExtra("movieID", movie_id_for_trailers);

                startActivity(intent);
            }
        });
        return rootView;
    }

    protected void killActivity(){
        getActivity().finish();
    }
}
