package com.example.pavan.moviesapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.pavan.moviesapp.NetworkActivity.RetrofitAPI;

import java.util.ArrayList;
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
    GridView gridView = null;

    private String clickedPoster, releaseDate, movieOverView, movieTitle, voteAverage, sortByPrefValue;
    private Long movie_id_for_trailers;
    private Bundle bundle = new Bundle();


    private AndroidUtil checkConnectivityStatus;
    private AlertDialog.Builder builder;
    private SharedPreferences sortByPref;

    private String BASE_URL = "http://api.themoviedb.org";
    private String API_KEY = "f9b69f2b96bfaa9b1748f12afbe14cea";

    private MoviesListData moviesListData = new MoviesListData();
    private MovieDetail_PagerAdapter movieDetail_pagerAdapter;
    private MovieDetailFragment movieDetailFragment = new MovieDetailFragment();

    private List<MoviesResultsJSON> moviesResultsJSONs;
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    protected RetrofitAPI api = retrofit.create(RetrofitAPI.class);

    public String getAPI_KEY() {
        return API_KEY;
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
                             final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        gridView = (GridView) rootView.findViewById(R.id.movie_grid_view);

        checkConnectivityStatus = new AndroidUtil(getContext());
        builder = new AlertDialog.Builder(getContext());

        sortByPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        sortByPrefValue = sortByPref.getString(getString(R.string.SortBy_key),
                getString(R.string.SortBy_default));
        Log.i("sortByPrefValue", sortByPrefValue);


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


                bundle.putString("posterURL", clickedPoster);
                bundle.putString("releaseDate", releaseDate);
                bundle.putString("movieOverview", movieOverView);
                bundle.putString("movieTitle", movieTitle);
                bundle.putLong("movieID", movie_id_for_trailers);
                bundle.putString("voteAverage", voteAverage);

                System.out.println("bundle data : " + bundle);

                movieDetail_pagerAdapter = new MovieDetail_PagerAdapter(getFragmentManager(), getContext(), bundle);

                getChildFragmentManager().beginTransaction().add(R.id.movie_detail_fragment_placeholder, movieDetailFragment, "movie details")
                        .addToBackStack("MainActivityFragment").commit();
            }
        });

        return rootView;
    }

    protected void killActivity(){
        getActivity().finish();
    }

    public void getMoviesListData() {

        Call<MoviesListData> moviesListDataCall = api.MOVIES_LIST_DATA_CALL(sortByPrefValue, API_KEY);

        moviesListDataCall.enqueue(new Callback<MoviesListData>() {
            @Override
            public void onResponse(Response<MoviesListData> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    moviesListData = response.body();
                    moviesResultsJSONs = response.body().getResults();

                    System.out.println("response.body() : " + response.body());
                    System.out.println("response.raw() : " + response.raw());


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
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("failed to fetch the data");
                builder.setMessage("Sorry, We couldn't fetch the movies information. Please try after sometime. Inconvenience regretted").setCancelable(false)
                        .setPositiveButton("It's Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                killActivity();
                            }
                        }).create().show();


            }
        });

    }


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

}
