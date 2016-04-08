package com.example.pavan.moviesapp.NetworkActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pavan on 4/2/2016.
 */
public class ReviewsData {

    private Long id;
    private Long page;
    private Long total_pages;
    private Long total_results;
    private List<MovieReviewsResponse> results;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Long total_pages) {
        this.total_pages = total_pages;
    }

    public Long getTotal_results() {
        return total_results;
    }

    public void setTotal_results(Long total_results) {
        this.total_results = total_results;
    }

    public List<MovieReviewsResponse> getReviewsResponse() {
        return results;
    }

    public void setReviewsResponse(List<MovieReviewsResponse> results) {
        this.results = results;
    }


}
