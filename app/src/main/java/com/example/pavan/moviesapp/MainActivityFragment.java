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

import com.example.pavan.moviesapp.NetworkActivity.MoviesListData;
import com.example.pavan.moviesapp.NetworkActivity.MoviesResultsJSON;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

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
    private String BASE_URL = "http://api.themoviedb.org";
    private String API_KEY = "f9b69f2b96bfaa9b1748f12afbe14cea";

    private MovieDetail movieDetail = new MovieDetail();
    private MoviesListData moviesListData = new MoviesListData();
    private List<MoviesResultsJSON> moviesResultsJSONs;

    private Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

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

        if (checkConnectivityStatus.isOnline()) {
        fetchMovieData.execute(sortByPrefValue);
            getMoviesListData();
        }
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

    public void getMoviesListData() {

        Call<MoviesListData> moviesListDataCall = movieDetail.api.MOVIES_LIST_DATA_CALL(sortByPrefValue, API_KEY);

        moviesListDataCall.enqueue(new Callback<MoviesListData>() {
            @Override
            public void onResponse(Response<MoviesListData> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    moviesListData = response.body();
                    moviesResultsJSONs = response.body().getResults();

                    System.out.println("response.body() : " + response.body());
                    System.out.println("response.raw() : " + response.raw());

                    System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                    System.out.println("moviesListData.getPage() : " + moviesListData.getPage());
                    System.out.println("moviesListData.getTotal_pages() : " + moviesListData.getTotal_pages());
                    System.out.println("moviesListData.getTotal_results() :  " + moviesListData.getTotal_results());

                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                    for (MoviesResultsJSON moviesResultsJSON : moviesResultsJSONs) {

                        System.out.println("moviesResultsJSON.getId() : " + moviesResultsJSON.getId());
                        System.out.println("moviesResultsJSON.getGenreIDsList() : " + moviesResultsJSON.getGenreIDsList());
                        System.out.println("moviesResultsJSON.getBackdrop_path() : " + moviesResultsJSON.getBackdrop_path());
                        System.out.println("moviesResultsJSON.getOriginal_language() : " + moviesResultsJSON.getOriginal_language());
                        System.out.println("moviesResultsJSON.getOriginal_title() : " + moviesResultsJSON.getOriginal_title());
                        System.out.println("moviesResultsJSON.getOverView() : " + moviesResultsJSON.getOverView());
                        System.out.println("moviesResultsJSON.getPopularity() : " + moviesResultsJSON.getPopularity());
                        System.out.println("moviesResultsJSON.getPoster_path() : " + moviesResultsJSON.getPoster_path());
                        System.out.println("moviesResultsJSON.getTitle() : " + moviesResultsJSON.getTitle());
                        System.out.println("moviesResultsJSON.getVote_average() : " + moviesResultsJSON.getVote_average());
                        System.out.println("moviesResultsJSON.getVote_count() : " + moviesResultsJSON.getVote_count());
                        System.out.println("moviesResultsJSON.isAdult() : " + moviesResultsJSON.isAdult());
                        System.out.println("moviesResultsJSON.isVideo() : " + moviesResultsJSON.isVideo());
                    }
                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }
}
