package com.example.pavan.moviesapp.NetworkActivity;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by pavan on 3/28/2016.
 */
public interface RetrofitAPI {


    @GET("{id}/videos")
    Call<MovieTrailerData> TRAILERS_DATA_CALL(@Path("id") Long id, @Query("api_key") String API_KEY);


    @GET("{id}/reviews")
    Call<ReviewsData> REVIEWS_DATA_CALL(@Path("id") Long id, @Query("api_key") String API_KEY);

    @GET("/3/discover/movie")
    Call<MoviesListData> MOVIES_LIST_DATA_CALL(@Query("sort_by") String SORT_BY_PREF_VALUE, @Query("api_key") String API_KEY);
}
