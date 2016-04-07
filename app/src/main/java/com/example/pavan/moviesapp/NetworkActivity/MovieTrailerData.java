package com.example.pavan.moviesapp.NetworkActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pavan on 4/4/2016.
 */
public class MovieTrailerData {

    //    MovieDetail movieDetail = new MovieDetail();
    private long id;
    private List<MovieTrailerResponse> results;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    /**
     * @return The id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return The results
     */
    public List<MovieTrailerResponse> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<MovieTrailerResponse> results) {
        this.results = results;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
