package com.example.pavan.moviesapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.pavan.moviesapp.MovieSQLiteDatabase.ReadDatabaseRecords;
import com.example.pavan.moviesapp.MovieSQLiteDatabase.checkDatabaseRecords;
import com.example.pavan.moviesapp.NetworkActivity.MoviesListData;
import com.example.pavan.moviesapp.NetworkActivity.MoviesResultsJSON;
import com.example.pavan.moviesapp.NetworkActivity.RetrofitAPI;

import java.util.ArrayList;
import java.util.List;

import Utils.AndroidUtil;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public static ArrayList Movie_ids_for_trailers_and_reviews_D = new ArrayList();
    public static ArrayList<String> Posters_D = new ArrayList<>();
    public static ArrayList<String> MovieOverViews_D = new ArrayList<>();
    public static ArrayList<String> ReleaseDates_D = new ArrayList<>();
    public static ArrayList<String> titles_D = new ArrayList<>();
    public static ArrayList VoteAverageArray_D = new ArrayList();
    private final String LOG_TAG = getClass().getSimpleName();
    GridView gridView;
    private ArrayList movie_ids_for_trailers_and_reviews = new ArrayList();
    private ArrayList<String> Posters = new ArrayList<>();
    private ArrayList<String> movieOverViews = new ArrayList<>();
    private ArrayList<String> releaseDates = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList voteAverageArray = new ArrayList();
    private String clickedPoster, releaseDate, movieOverView, movieTitle, voteAverage, sortByPrefValue;
    private Long movie_id_for_trailers;
    private Bundle bundle = new Bundle();
    private AndroidUtil checkConnectivityStatus;
    private AlertDialog.Builder builder;
    private SharedPreferences sortByPref;
    private String BASE_URL = "http://api.themoviedb.org";
    private String API_KEY = "f9b69f2b96bfaa9b1748f12afbe14cea";
    private int confirmation;
    private MoviesListData moviesListData = new MoviesListData();
    private List<MoviesResultsJSON> moviesResultsJSONs = new ArrayList<>();
    private checkDatabaseRecords checkDatabaseRecords;
    private ReadDatabaseRecords readDatabaseRecords;
    private MovieDetail_PagerAdapter movieDetail_pagerAdapter;
    private MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            movie_ids_for_trailers_and_reviews = (ArrayList) savedInstanceState.getSerializable("movie_ids_for_trailers_and_reviews");
            movieOverViews = savedInstanceState.getStringArrayList("movieOverViews");
            Posters = savedInstanceState.getStringArrayList("Posters");
            titles = savedInstanceState.getStringArrayList("titles");
            voteAverageArray = savedInstanceState.getStringArrayList("voteAverageArray");
            releaseDates = savedInstanceState.getStringArrayList("releaseDates");

            gridView.setAdapter(new ImageAdapter(getContext(), Posters, sortByPrefValue));

        }

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
        readDatabaseRecords = new ReadDatabaseRecords(getContext());
        builder = new AlertDialog.Builder(getContext());


        sortByPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        sortByPrefValue = sortByPref.getString(getString(R.string.SortBy_key),
                getString(R.string.SortBy_default));


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "position : " + position, Toast.LENGTH_SHORT).show();


                clickedPoster = Posters.get(position);
                releaseDate = releaseDates.get(position);
                movieOverView = movieOverViews.get(position);
                voteAverage = voteAverageArray.get(position).toString();
                movieTitle = titles.get(position).toString();
                movie_id_for_trailers = (Long) movie_ids_for_trailers_and_reviews.get(position);

                bundle.putString("posterURL", clickedPoster);
                bundle.putString("releaseDate", releaseDate);
                bundle.putString("movieOverview", movieOverView);
                bundle.putString("movieTitle", movieTitle);
                bundle.putLong("movieID", movie_id_for_trailers);
                bundle.putString("voteAverage", voteAverage);
                bundle.putString("sortPreference", sortByPrefValue);


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

    protected void killActivity() {
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

                    for (MoviesResultsJSON moviesResultsJSON : moviesResultsJSONs) {

                        movie_ids_for_trailers_and_reviews.add(moviesResultsJSON.getId());
                        movieOverViews.add(moviesResultsJSON.getOverView());
                        Posters.add(moviesResultsJSON.getPoster_path());
                        titles.add(moviesResultsJSON.getTitle());
                        voteAverageArray.add(moviesResultsJSON.getVote_average());
                        releaseDates.add(moviesResultsJSON.getRelease_date());
                    }

                }

                gridView.setAdapter(new ImageAdapter(getContext(), Posters, sortByPrefValue));
            }


            @Override
            public void onFailure(Throwable t) {

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

    public void favoriteMoviesInfo() {
        confirmation = readDatabaseRecords.fetchAllMovieDatabaseRecords();

        movie_ids_for_trailers_and_reviews = Movie_ids_for_trailers_and_reviews_D;
        titles = titles_D;
        Posters = Posters_D;
        voteAverageArray = VoteAverageArray_D;
        releaseDates = ReleaseDates_D;
        movieOverViews = MovieOverViews_D;

        if (confirmation == 0 && checkConnectivityStatus.isOnline())
            builder.setMessage("There are no movies marked favorite.").setCancelable(false)
                    .setPositiveButton("Change Sort preference", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getContext(), SettingsActivity.class));
                        }
                    }).setNegativeButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    killActivity();
                }
            }).show();
        else if (confirmation == 0 && checkConnectivityStatus.isOnline() == false) {
            builder.setMessage("Sorry,We couldn't detect an INTERNET Connectivity to your device & there are no movies marked favorite to display when the device OFFLINE.\n click OK to close the app.")
                    .setCancelable(false).setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            killActivity();
                        }
                    }).create().show();

        } else if (confirmation != 0 && (checkConnectivityStatus.isOnline() == true || checkConnectivityStatus.isOnline() != true)) {

            gridView.setAdapter(new ImageAdapter(getContext(), Posters, sortByPrefValue));
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArrayList("Posters", Posters);
        outState.putStringArrayList("releaseDates", releaseDates);
        outState.putStringArrayList("movieOverViews", movieOverViews);
        outState.putStringArrayList("titles", titles);
        outState.putSerializable("movie_ids_for_trailers_and_reviews", movie_ids_for_trailers_and_reviews);
        outState.putStringArrayList("voteAverageArray", voteAverageArray);
        outState.putString("sortPreference", sortByPrefValue);

    }

    @Override
    public void onStart() {
        super.onStart();

        if (sortByPrefValue.equals(getString(R.string.favorites_value)) && movie_ids_for_trailers_and_reviews.isEmpty())
            favoriteMoviesInfo();
        else if (checkConnectivityStatus.isOnline() && (movie_ids_for_trailers_and_reviews.isEmpty() || movie_ids_for_trailers_and_reviews == null))
            getMoviesListData();
        else if (!checkConnectivityStatus.isOnline() && movie_ids_for_trailers_and_reviews.isEmpty())
            favoriteMoviesInfo();
    }
}