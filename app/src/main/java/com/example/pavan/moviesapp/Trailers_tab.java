package com.example.pavan.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Trailers_tab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Trailers_tab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Trailers_tab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String BASE_TRAILERS_AND_REVIEWS_URL = "http://api.themoviedb.org/3/movie/";
    private String BASE_YOUTUBE_URL = "http://www.youtube.com/watch";

    private OnFragmentInteractionListener mListener;
    private ListView trailersListView;
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
    private MovieTrailerAdapter movieTrailerAdapter = new MovieTrailerAdapter(getContext(), Name, Key);

    public Trailers_tab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p/>
     * //     * @param param1 Parameter 1.
     * //     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment Trailers_tab.
     */
    // TODO: Rename and change types and number of parameters
    public static Trailers_tab newInstance(Long movieID) {
        Trailers_tab fragment = new Trailers_tab();
        Bundle args = new Bundle();
        args.putLong("movieID", movieID);
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
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
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
            movieID = getArguments().getLong("movieID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trailers_tab, container, false);

        trailersListView = (ListView) view.findViewById(R.id.trailers_list_view);

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

                    for (final MovieTrailerResponse movieTrailerResponses1 : movieTrailerResponses) {
//                        System.out.println("movieTrailerResponse.getId() : " + movieTrailerResponses1.getId());
//                        System.out.println("movieTrailerResponse.getIso31661() : " + movieTrailerResponses1.getIso_3166_1());
//                        System.out.println("movieTrailerResponse.getIso6391() : " + movieTrailerResponses1.getIso_639_1());
//                        System.out.println("movieTrailerResponse.getKey() : " + movieTrailerResponses1.getKey());
//                        System.out.println("movieTrailerResponse.getName() : " + movieTrailerResponses1.getName());
//                        System.out.println("movieTrailerResponse.getSite() : " + movieTrailerResponses1.getSite());
//                        System.out.println("movieTrailerResponses1.getSize() : " + movieTrailerResponses1.getSize());
//                        System.out.println("movieTrailerResponses1.getType() : " + movieTrailerResponses1.getType());
//                        System.out.println("----------------------------------------------------------");


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
                            YOUTUBE_INTENT = new Intent(Intent.ACTION_VIEW, Uri.parse(BASE_YOUTUBE_URL).buildUpon()
                                    .appendQueryParameter("v", movieTrailerAdapter.Key.get(position)).build());
                            startActivity(YOUTUBE_INTENT);
                        }
                    });


                    System.out.println(">>>>>>>>>>>>" + movieTrailerData.getResults());
                    System.out.println("response results getID : " + movieTrailerData.getId());
                    System.out.println("response raw : " + response.raw());
                    System.out.println("response body : " + response.body());


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


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
