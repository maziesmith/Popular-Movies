package com.example.pavan.moviesapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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

import com.example.pavan.moviesapp.MovieSQLiteDatabase.DatabaseInsertions;
import com.example.pavan.moviesapp.MovieSQLiteDatabase.ReadDatabaseRecords;
import com.example.pavan.moviesapp.MovieSQLiteDatabase.ValuesForDatabase;
import com.example.pavan.moviesapp.MovieSQLiteDatabase.checkDatabaseRecords;
import com.example.pavan.moviesapp.NetworkActivity.MoviesListData;
import com.example.pavan.moviesapp.NetworkActivity.MoviesResultsJSON;
import com.example.pavan.moviesapp.NetworkActivity.RetrofitAPI;

import java.util.ArrayList;
import java.util.List;

import Utils.AndroidUtil;
import butterknife.BindView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private final String LOG_TAG = getClass().getSimpleName();

    ArrayList<String> Posters = new ArrayList<>();
    ArrayList movieOverViews = new ArrayList();
    ArrayList releaseDates = new ArrayList();
    ArrayList titles = new ArrayList();
    ArrayList voteAverageArray = new ArrayList();
    ArrayList movie_ids_for_trailers_and_reviews = new ArrayList();
    //    GridView gridView = null;
    @BindView(R.id.movie_grid_view)
    GridView gridView;

    private String clickedPoster, releaseDate, movieOverView, movieTitle, voteAverage, sortByPrefValue;
    private Long movie_id_for_trailers;
    private Bundle bundle = new Bundle();


    private AndroidUtil checkConnectivityStatus;
    private AlertDialog.Builder builder;
    private SharedPreferences sortByPref;

    private String BASE_URL = "http://api.themoviedb.org";
    private String API_KEY = "f9b69f2b96bfaa9b1748f12afbe14cea";

    private MoviesListData moviesListData = new MoviesListData();
    private checkDatabaseRecords checkDatabaseRecords;
    private ReadDatabaseRecords readDatabaseRecords;
    private DatabaseInsertions databaseInsertions;
    private MovieDetail_PagerAdapter movieDetail_pagerAdapter;
    private MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
    private ValuesForDatabase valuesForDatabase = new ValuesForDatabase();

    private List<MoviesResultsJSON> moviesResultsJSONs = new ArrayList<>();
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    protected RetrofitAPI api = retrofit.create(RetrofitAPI.class);

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

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
        checkDatabaseRecords = new checkDatabaseRecords(getContext());
        databaseInsertions = new DatabaseInsertions(getContext());
        readDatabaseRecords = new ReadDatabaseRecords(getContext());
        builder = new AlertDialog.Builder(getContext());


        sortByPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        sortByPrefValue = sortByPref.getString(getString(R.string.SortBy_key),
                getString(R.string.SortBy_default));
        Log.i("sortByPrefValue", sortByPrefValue);


        if (sortByPrefValue == getString(R.string.favorites_value)) {
            // TODO: offline data from the database.
        }


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

                Log.i(LOG_TAG, "bundle data : " + bundle);


                movieDetail_pagerAdapter = new MovieDetail_PagerAdapter(getFragmentManager(), getContext(), bundle);

                if (isTablet(getContext())) {
                    Log.i(LOG_TAG, "running on tablet");
                    ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
                    layoutParams.width = 0;

                    getFragmentManager().beginTransaction()
                            .replace(R.id.movie_detail_fragment_placeholder_for_tablet, new MovieDetailFragment(), "movie details")
                            .commit();
                } else {
                    Log.i(LOG_TAG, "running on phone");
                    getFragmentManager().beginTransaction().add(R.id.movie_detail_fragment_placeholder, movieDetailFragment, "movie details")
                            .addToBackStack("MainActivityFragment").commit();
                }
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

                    Log.i(LOG_TAG, "response.body() : " + response.body());
                    Log.i(LOG_TAG, "response.raw() : " + response.raw());


                    for (MoviesResultsJSON moviesResultsJSON : moviesResultsJSONs) {

                        movie_ids_for_trailers_and_reviews.add(moviesResultsJSON.getId());
                        movieOverViews.add(moviesResultsJSON.getOverView());
                        Posters.add(moviesResultsJSON.getPoster_path());
                        titles.add(moviesResultsJSON.getTitle());
                        voteAverageArray.add(moviesResultsJSON.getVote_average());
                        releaseDates.add(moviesResultsJSON.getRelease_date());
                        }
                    }

                Log.i(LOG_TAG, "titles array : " + titles);
                Log.i(LOG_TAG, "posters path : " + Posters);
                Log.i(LOG_TAG, "vote avg : " + voteAverageArray);
                Log.i(LOG_TAG, "release date : " + releaseDates);
                Log.i(LOG_TAG, "over views : " + movieOverViews);
                Log.i(LOG_TAG, "movie IDs : " + movie_ids_for_trailers_and_reviews);

                    gridView.setAdapter(new ImageAdapter(getContext(), Posters));
                }


            @Override
            public void onFailure(Throwable t) {
                Log.i(LOG_TAG, "failed to fetch the data");
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
            builder.setMessage("We couldn't detect an INTERNET Connectivity to your device. You can view your favorite movies without Internet Connectivity").setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            readDatabaseRecords.fetchAllMovieDatabaseRecords();

                            if (readDatabaseRecords.NoDBContent)
                                builder.setMessage("No favorite movies marked to show when the device is offline")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                killActivity();
                                            }
                                        });

                            for (MoviesResultsJSON moviesResultsJSON : moviesResultsJSONs) {
                                movie_ids_for_trailers_and_reviews.add(moviesResultsJSON.getId());
                                movieOverViews.add(moviesResultsJSON.getOverView());
                                Posters.add(moviesResultsJSON.getPoster_path());
                                titles.add(moviesResultsJSON.getTitle());
                                voteAverageArray.add(moviesResultsJSON.getVote_average());
                                releaseDates.add(moviesResultsJSON.getRelease_date());

                            }
                        }
                    }).create().show();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

}
