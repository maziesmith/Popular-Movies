package com.example.pavan.moviesapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.pavan.moviesapp.NetworkActivity.MovieReviewsResponse;
import com.example.pavan.moviesapp.NetworkActivity.ReviewsData;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Reviews_tab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Reviews_tab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reviews_tab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private long movieID;

    private List<MovieReviewsResponse> movieReviewsResponses;

    private Trailers_tab trailers_tab = new Trailers_tab();
    private MainActivityFragment mainActivityFragment = new MainActivityFragment();
    private MovieReviewsAdapter movieReviewsAdapter = new MovieReviewsAdapter(getContext());
    private ReviewsData reviewsData = new ReviewsData();


    private OnFragmentInteractionListener mListener;
    private ListView reviews_list_view;

    public Reviews_tab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p/>
     * //     * @param param1 Parameter 1.
     * //     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment Reviews_tab.
     */
    // TODO: Rename and change types and number of parameters
    public static Reviews_tab newInstance(Long movieID) {
        Reviews_tab fragment = new Reviews_tab();
        Bundle args = new Bundle();
        args.putLong("movieID", movieID);
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_reviews_tab, container, false);


        reviews_list_view = (ListView) view.findViewById(R.id.reviews_list_view);


        fetchReviewsData();

        return view;
    }

    public void fetchReviewsData() {

        Call<ReviewsData> reviewsDataCall = trailers_tab.getApi().REVIEWS_DATA_CALL(movieID, mainActivityFragment.getAPI_KEY());

        reviewsDataCall.enqueue(new Callback<ReviewsData>() {
            @Override
            public void onResponse(Response<ReviewsData> response, Retrofit retrofit) {

                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("response status reviews : " + response.isSuccess());

                System.out.println("response.raw() reviews : " + response.raw());

                System.out.println("response.body() reviews : " + response.body());

                System.out.println("response.message() reviews : " + response.message());

                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                reviewsData = response.body();
                movieReviewsResponses = reviewsData.getReviewsResponse();

                movieReviewsAdapter.noOfReviews = reviewsData.getReviewsResponse().size();
                System.out.println("response.body().getReviewsResponse() : " + response.body().getReviewsResponse().size());


                System.out.println("/////////////////////////////////////////////////////////////");

                System.out.println("reviewsData.getId() : " + reviewsData.getId());
                System.out.println("reviewsData.getPage() : " + reviewsData.getPage());
                System.out.println("reviewsData.getTotal_pages() : " + reviewsData.getTotal_pages());
                System.out.println("reviewsData.getReviewsResponse() : " + reviewsData.getReviewsResponse());

                System.out.println("//////////////////////////////////////////////////////////////////////////////");


                System.out.println("------------------------------------------------------------------------");

                for (MovieReviewsResponse movieReviewsResponse : movieReviewsResponses) {
                    System.out.println("movieReviewsResponse.getAuthor() : " + movieReviewsResponse.getAuthor());
                    movieReviewsAdapter.author_name.add(movieReviewsResponse.getAuthor());
                    System.out.println("movieReviewsResponse.getContent() : " + movieReviewsResponse.getContent());
                    movieReviewsAdapter.author_review.add(movieReviewsResponse.getContent());
                    System.out.println("movieReviewsResponse.getId() : " + movieReviewsResponse.getId());
                    System.out.println("movieReviewsResponse.getUrl() : " + movieReviewsResponse.getUrl());

                }
                System.out.println("---------------------------------------------------------------------------");
                reviews_list_view.setAdapter(movieReviewsAdapter);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("failed to fetch reviews data....");
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
