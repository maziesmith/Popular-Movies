package com.example.pavan.moviesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
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
import com.squareup.picasso.Picasso;

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
    private MovieTrailerAdapter movieTrailerAdapter;
    private AndroidUtil androidUtil;
    private Uri uri;
    private LinearLayout header;

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

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public RetrofitAPI getApi() {
        return api;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i(LOG_TAG, "onSaveInstanceState fired");

        outState.putLong("movieID", movieID);
        outState.putStringArrayList("id", id);
        outState.putStringArrayList("iso_3166_1", iso_3166_1);
        outState.putStringArrayList("iso_639_1", iso_639_1);
        outState.putStringArrayList("Name", Name);
        outState.putStringArrayList("Key", Key);
        outState.putIntegerArrayList("size", size);

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

        if (androidUtil.isOnline() != true) {
            header.setVisibility(View.GONE);
            no_trailers_msg.setText("Trailers cannot be displayed when there is no Internet Connectivity");
            return view;
        }

        movieTrailerAdapter = new MovieTrailerAdapter(getContext(), Name, Key);


        fetchTrailerData();


        return view;
    }

    public void fetchTrailerData() {


        Call<MovieTrailerData> responseCall = api.TRAILERS_DATA_CALL(movieID, mainActivityFragment.getAPI_KEY());


        responseCall.enqueue(new Callback<MovieTrailerData>() {
            @Override
            public void onResponse(Response<MovieTrailerData> response, Retrofit retrofit) {

                if (response.isSuccess()) {
                    movieTrailerData = response.body();

                    movieTrailerResponses = movieTrailerData.getResults();

                    if (movieTrailerData.getResults().size() == 0) {
                        header.setVisibility(View.GONE);
                        no_trailers_msg.setText("No Trailers Found for this Movie");
                    } else
                        no_of_trailers.setText("Number of Trailers available : " + movieTrailerData.getResults().size());

                    for (final MovieTrailerResponse movieTrailerResponses1 : movieTrailerResponses) {

                        id.add(movieTrailerResponses1.getId());
                        iso_3166_1.add(movieTrailerResponses1.getIso_3166_1());
                        iso_639_1.add(movieTrailerResponses1.getIso_639_1());
                        Name.add(movieTrailerResponses1.getName());
                        Key.add(movieTrailerResponses1.getKey());
                        size.add(movieTrailerResponses1.getSize());

                        movieTrailerAdapter.noOfTrailers = movieTrailerData.getResults().size();
                    }

                    uri = Uri.parse(BASE_YOUTUBE_URL).buildUpon().appendQueryParameter("v", movieTrailerAdapter.Key.get(1)).build();

                    setUri(uri);

                    trailersListView.setAdapter(movieTrailerAdapter);

                    sendMessage();

                    trailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            uri = Uri.parse(BASE_YOUTUBE_URL).buildUpon()
                                    .appendQueryParameter("v", movieTrailerAdapter.Key.get(position)).build();
                            YOUTUBE_INTENT = new Intent(Intent.ACTION_VIEW, getUri());
                            startActivity(YOUTUBE_INTENT);
                        }
                    });

                }
            }

            @Override
            public void onFailure(Throwable t) {

                Log.i(LOG_TAG, "failed to fetch trailer data....");
                Picasso.with(getContext())
                        .load(R.drawable.ic_mood_bad_black_24dp)
                        .centerCrop()
                        .noFade()
                        .resize(150, 150)
                        .into(movieTrailerAdapter.trailer_thumbnail_image);
                movieTrailerAdapter.trailer_title.setText("Sorry, we couldn't fetch the Trailer information");
            }
        });

    }

    private void sendMessage() {
        Log.d(LOG_TAG, "Broadcasting message");
        Intent intent = new Intent("share-movie-data");
        intent.putExtra("Trailer", getUri().toString());
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

}