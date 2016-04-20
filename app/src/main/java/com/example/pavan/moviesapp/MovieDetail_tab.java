package com.example.pavan.moviesapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieDetail_tab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieDetail_tab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetail_tab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ImageView Poster;
    private String poster_path;
    private String releaseDate;
    private String movieOverview;
    private String movieTitle;
    private String voteAverage;

    private ListView trailersListView;
    private String BASE_TRAILERS_AND_REVIEWS_URL = "http://api.themoviedb.org/3/movie/";
    private TextView release_date, movie_overview, movie_title, vote_average;
    private String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";


    public MovieDetail_tab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p/>
     * //     * @param param1 Parameter 1.
     * //     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment MovieDetail_tab.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetail_tab newInstance(String clickedPoster, String movieTitle, String releaseDate, String movieOverView, String voteAverage) {
        MovieDetail_tab fragment = new MovieDetail_tab();
        Bundle args = new Bundle();

        args.putString("posterURL", clickedPoster);
        args.putString("releaseDate", releaseDate);
        args.putString("movieOverview", movieOverView);
        args.putString("movieTitle", movieTitle);
        args.putString("voteAverage", voteAverage);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            poster_path = getArguments().getString("posterURL");
            releaseDate = getArguments().getString("releaseDate");
            movieOverview = getArguments().getString("movieOverview");
            movieTitle = getArguments().getString("movieTitle");
            voteAverage = getArguments().getString("voteAverage");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail_tab, container, false);


        Poster = (ImageView) view.findViewById(R.id.movie_poster_in_movie_detail_activity);

//        trailersListView = (ListView) findViewById(R.id.trailers_list_view);
//        reviews_list_view = (ListView) findViewById(R.id.reviews_list_view);

        release_date = (TextView) view.findViewById(R.id.release_year);
        movie_overview = (TextView) view.findViewById(R.id.movie_overview);
        movie_title = (TextView) view.findViewById(R.id.movie_title);
        vote_average = (TextView) view.findViewById(R.id.vote_average);


        Picasso.with(getContext())
                .load(BASE_POSTER_URL + poster_path)
                .resize(175 * 2, 250 * 2)
                .into(Poster);


        release_date.setText(releaseDate);
        movie_overview.setText(movieOverview);
        movie_title.setText(movieTitle);
        vote_average.setText(voteAverage + "/10");


        return view;
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
