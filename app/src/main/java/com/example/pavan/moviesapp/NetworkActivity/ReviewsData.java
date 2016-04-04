package com.example.pavan.moviesapp.NetworkActivity;

import java.util.ArrayList;

/**
 * Created by pavan on 4/2/2016.
 */
public class ReviewsData {

    private Long id;
    private int page;
    private int total_pages;
    private int total_results;
    private ArrayList<MovieReviewsResponse> reviewsResponse = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public ArrayList<MovieReviewsResponse> getReviewsResponse() {
        return reviewsResponse;
    }

    public void setReviewsResponse(ArrayList<MovieReviewsResponse> reviewsResponse) {
        this.reviewsResponse = reviewsResponse;
    }


}
