package com.example.pavan.moviesapp.NetworkActivity;

import java.util.List;

/**
 * Created by pavan on 4/8/2016.
 */
public class MoviesListData {

    private Long page, total_results, total_pages;
    private List<MoviesResultsJSON> results;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getTotal_results() {
        return total_results;
    }

    public void setTotal_results(Long total_results) {
        this.total_results = total_results;
    }

    public Long getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Long total_pages) {
        this.total_pages = total_pages;
    }

    public List<MoviesResultsJSON> getResults() {
        return results;
    }

    public void setResults(List<MoviesResultsJSON> results) {
        this.results = results;
    }
}
