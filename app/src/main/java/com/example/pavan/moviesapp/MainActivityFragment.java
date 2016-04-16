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

<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> origin/master
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


    ArrayList<String> Posters = new ArrayList<>();
    ArrayList movieOverViews = new ArrayList();
    ArrayList releaseDates = new ArrayList();
    ArrayList titles = new ArrayList();
    ArrayList voteAverageArray = new ArrayList();
    ArrayList movie_ids_for_trailers_and_reviews = new ArrayList();
    Intent intent;
    GridView gridView = null;
    private String clickedPoster, releaseDate, movieOverView, movieTitle, voteAverage, sortByPrefValue;
    private Long movie_id_for_trailers;
    private String BASE_URL = "http://api.themoviedb.org";
    private String API_KEY = "f9b69f2b96bfaa9b1748f12afbe14cea";
    private List<MoviesResultsJSON> moviesResultsJSONs;
    private AndroidUtil checkConnectivityStatus;
    private AlertDialog.Builder builder;
    private SharedPreferences sortByPref;
<<<<<<< HEAD
    private MovieDetail movieDetail = new MovieDetail();
    private MoviesListData moviesListData = new MoviesListData();
=======
    private String sortByPrefValue;
    private String BASE_URL = "http://api.themoviedb.org";
    private String API_KEY = "f9b69f2b96bfaa9b1748f12afbe14cea";

    private MovieDetail movieDetail = new MovieDetail();
    private MoviesListData moviesListData = new MoviesListData();
    private List<MoviesResultsJSON> moviesResultsJSONs;

>>>>>>> origin/master
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();


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

        gridView = (GridView) rootView.findViewById(R.id.movie_grid_view);

        checkConnectivityStatus = new AndroidUtil(getContext());
        builder = new AlertDialog.Builder(getContext());

        sortByPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        sortByPrefValue = sortByPref.getString(getString(R.string.SortBy_key),
                getString(R.string.SortBy_default));
        Log.i("sortByPrefValue", sortByPrefValue);

<<<<<<< HEAD
=======
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



>>>>>>> origin/master
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "position : " + position, Toast.LENGTH_SHORT).show();

                Log.i("poster string", Posters.get(position));

                clickedPoster = Posters.get(position);
                releaseDate = releaseDates.get(position).toString();
                movieOverView = (String) movieOverViews.get(position);
                voteAverage = voteAverageArray.get(position).toString();
                movieTitle = titles.get(position).toString();
                movie_id_for_trailers = (Long) movie_ids_for_trailers_and_reviews.get(position);

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

<<<<<<< HEAD
                    for (MoviesResultsJSON moviesResultsJSON : moviesResultsJSONs) {

                        movie_ids_for_trailers_and_reviews.add(moviesResultsJSON.getId());
                        movieOverViews.add(moviesResultsJSON.getOverView());
                        Posters.add(moviesResultsJSON.getPoster_path());
                        titles.add(moviesResultsJSON.getTitle());
                        voteAverageArray.add(moviesResultsJSON.getVote_average());
                        releaseDates.add(moviesResultsJSON.getRelease_date());
                    }

                    System.out.println("titles array : " + titles);
                    System.out.println("posters path : " + Posters);
                    System.out.println("vote avg : " + voteAverageArray);
                    System.out.println("release date : " + releaseDates);
                    System.out.println("over views : " + movieOverViews);
                    System.out.println("movie IDs : " + movie_ids_for_trailers_and_reviews);
                    gridView.setAdapter(new ImageAdapter(getContext(), Posters));
=======
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
>>>>>>> origin/master
                }
            }

            @Override
            public void onFailure(Throwable t) {

<<<<<<< HEAD
                System.out.println("failed to fetch the data");
                getActivity().finish();

=======
>>>>>>> origin/master
            }
        });

    }
<<<<<<< HEAD

    @Override
    public void onStart() {
        super.onStart();

        if (checkConnectivityStatus.isOnline())
            getMoviesListData();
        else
            builder.setMessage("Please check your INTERNET Connection").setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            killActivity();
                        }
                    }).create().show();

    }

    @Override
    public void onPause() {
        super.onPause();

    }
=======
>>>>>>> origin/master
}
