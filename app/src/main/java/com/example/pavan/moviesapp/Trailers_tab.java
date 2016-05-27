package com.example.pavan.moviesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pavan.moviesapp.NetworkActivity.MovieTrailerData;
import com.example.pavan.moviesapp.NetworkActivity.MovieTrailerResponse;
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



public class Trailers_tab extends Fragment {

    private final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.trailers_list_view)
    ListView trailersListView;

    @BindView(R.id.no_trailers_msg)
    TextView no_trailers_msg;
    @BindView(R.id.no_of_Trailers)
    TextView no_of_trailers;
    private String BASE_TRAILERS_AND_REVIEWS_URL = "http://api.themoviedb.org/3/movie/";
    private String BASE_YOUTUBE_URL = "http://www.youtube.com/watch";
    private long movieID;
    private MovieDetail_tab movieDetail_tab = new MovieDetail_tab();


    private List<MovieTrailerResponse> movieTrailerResponses;
    private ArrayList<String> Key = new ArrayList<String>();
    private ArrayList<String> id = new ArrayList<String>();
    private ArrayList<Integer> size = new ArrayList<>();
    private ArrayList<String> iso_3166_1 = new ArrayList<String>();
    private ArrayList<String> Name = new ArrayList<String>();
    private ArrayList<String> iso_639_1 = new ArrayList<String>();


    private Intent YOUTUBE_INTENT;
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_TRAILERS_AND_REVIEWS_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();
    private RetrofitAPI api = retrofit.create(RetrofitAPI.class);
    private MainActivityFragment mainActivityFragment = new MainActivityFragment();
    private MovieTrailerData movieTrailerData = new MovieTrailerData();
    private MovieTrailerAdapter movieTrailerAdapter = new MovieTrailerAdapter();
    private AndroidUtil androidUtil;
    private Uri uri;
    private LinearLayout header;
    private int noOfTrailers = 0;

    public Trailers_tab() {
        // Required empty public constructor
    }

    public static Trailers_tab newInstance(Long movieID) {
        Trailers_tab fragment = new Trailers_tab();
        Bundle args = new Bundle();
        args.putLong("movieID", movieID);
        fragment.setArguments(args);
        return fragment;
    }

    public Uri getUri() {
        return uri;
    }

    public RetrofitAPI getApi() {
        return api;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieID = getArguments().getLong("movieID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trailers_tab, container, false);

        no_trailers_msg = (TextView) view.findViewById(R.id.no_trailers_msg);
        trailersListView = (ListView) view.findViewById(R.id.trailers_list_view);
        no_of_trailers = (TextView) view.findViewById(R.id.no_of_Trailers);

        header = (LinearLayout) view.findViewById(R.id.header);

        androidUtil = new AndroidUtil(getContext());

        return view;
    }

    public void viewSetup() {

        if (noOfTrailers == 0) {
            header.setVisibility(View.GONE);
            no_trailers_msg.setText("No Trailers Found for this Movie");
        } else {
            no_of_trailers.setText("Number of Trailers available : " + noOfTrailers);
            uri = Uri.parse(BASE_YOUTUBE_URL).buildUpon().appendQueryParameter("v", Key.get(1)).build();
            sendMessage();
            trailersListView.setAdapter(new MovieTrailerAdapter(getContext(), Name, Key, noOfTrailers));

            trailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    uri = Uri.parse(BASE_YOUTUBE_URL).buildUpon()
                            .appendQueryParameter("v", Key.get(position)).build();
                    YOUTUBE_INTENT = new Intent(Intent.ACTION_VIEW, getUri());
                    startActivity(YOUTUBE_INTENT);
                }
            });
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        if (androidUtil.isOnline() && (Key == null || Key.isEmpty()))
            fetchTrailerData();
        else if (androidUtil.isOnline() != true) {
            header.setVisibility(View.GONE);
            no_trailers_msg.setText("Trailers cannot be displayed when there is no Internet Connectivity");
        }




    }

    public void fetchTrailerData() {


        Call<MovieTrailerData> responseCall = api.TRAILERS_DATA_CALL(movieID, mainActivityFragment.getAPI_KEY());


        responseCall.enqueue(new Callback<MovieTrailerData>() {
            @Override
            public void onResponse(Response<MovieTrailerData> response, Retrofit retrofit) {

                if (response.isSuccess()) {
                    movieTrailerData = response.body();

                    movieTrailerResponses = movieTrailerData.getResults();

                    noOfTrailers = movieTrailerData.getResults().size();

                    for (final MovieTrailerResponse movieTrailerResponses1 : movieTrailerResponses) {


                        Name.add(movieTrailerResponses1.getName());
                        Key.add(movieTrailerResponses1.getKey());

                        noOfTrailers = movieTrailerData.getResults().size();
                    }
                    viewSetup();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                no_trailers_msg.setText("We failed to fetch the List of trailers for this movie.\n Inconvenience regretted");
            }
        });

    }

    private void sendMessage() {
        Intent intent = new Intent("share-movie-data");
        intent.putExtra("Trailer", getUri().toString());
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong("movieID", movieID);
        outState.putStringArrayList("Name", Name);
        outState.putStringArrayList("Key", Key);
        outState.putInt("noOfTrailers", noOfTrailers);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            movieID = savedInstanceState.getLong("movieID");
            Name = savedInstanceState.getStringArrayList("Name");
            Key = savedInstanceState.getStringArrayList("Key");
            noOfTrailers = savedInstanceState.getInt("noOfTrailers");
            viewSetup();
        }
    }
}