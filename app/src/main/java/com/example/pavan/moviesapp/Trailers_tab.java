package com.example.pavan.moviesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pavan.moviesapp.NetworkActivity.MovieTrailerData;
import com.example.pavan.moviesapp.NetworkActivity.MovieTrailerResponse;
import com.example.pavan.moviesapp.NetworkActivity.RetrofitAPI;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;



public class Trailers_tab extends Fragment {

    private String BASE_TRAILERS_AND_REVIEWS_URL = "http://api.themoviedb.org/3/movie/";
    private String BASE_YOUTUBE_URL = "http://www.youtube.com/watch";


    private ListView trailersListView;
    private TextView no_trailers_msg, no_of_trailers;
    private long movieID;


    private List<MovieTrailerResponse> movieTrailerResponses;
    private ArrayList<String> Key = new ArrayList<String>();
    private ArrayList<String> id = new ArrayList<String>();
    private ArrayList<Long> size = new ArrayList<Long>();
    private ArrayList<String> iso_3166_1 = new ArrayList<String>();
    private ArrayList<String> Name = new ArrayList<String>();
    private ArrayList<String> iso_639_1 = new ArrayList<String>();


    private Intent YOUTUBE_INTENT;
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_TRAILERS_AND_REVIEWS_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();
    private RetrofitAPI api = retrofit.create(RetrofitAPI.class);
    private MainActivityFragment mainActivityFragment = new MainActivityFragment();
    private MovieTrailerData movieTrailerData = new MovieTrailerData();
    private MovieDetail_tab movieDetail_tab = new MovieDetail_tab();
    private MovieTrailerAdapter movieTrailerAdapter;
    private Uri uri;

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

                    System.out.println("movieTrailerData.getResults().size() : " + movieTrailerData.getResults().size());

                    if (movieTrailerData.getResults().size() == 0)
                        no_trailers_msg.setText("No Trailers Found for this Movie");
                    else
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
                    System.out.println("KEY ArrayList : " + Key);
                    System.out.println(size);
                    System.out.println(Name);
                    System.out.println(iso_639_1);
                    System.out.println(iso_3166_1);
                    System.out.println(id);


                    trailersListView.setAdapter(movieTrailerAdapter);

                    trailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            uri = Uri.parse(BASE_YOUTUBE_URL).buildUpon()
                                    .appendQueryParameter("v", movieTrailerAdapter.Key.get(position)).build();
                            YOUTUBE_INTENT = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(YOUTUBE_INTENT);
                        }
                    });

                }
            }

            @Override
            public void onFailure(Throwable t) {

                System.out.println("failed to fetch trailer data....");
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

    Intent shareMovieAndTrailersInfo() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET).setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, movieDetail_tab.getMovieTitle());
        shareIntent.putExtra(Intent.EXTRA_TEXT, uri);
        return shareIntent;
    }
}
