package com.example.pavan.moviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pavan.moviesapp.NetworkActivity.MovieTrailerResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pavan on 4/10/2016.
 */
public class MovieTrailerAdapter extends BaseAdapter {


    List<MovieTrailerResponse> movieTrailerResponseList;

    int noOfTrailers, size;
    Context context;
    ImageView trailer_thumbnail_image;
    TextView trailer_title;
    private String YOUTUBE_THUMBNAIL_BASE_URL = "http://img.youtube.com/vi/", KEY, name, site, type, iso_639_1, iso_3166_1, id;

    public MovieTrailerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return noOfTrailers;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.trailers, parent, false);
            trailer_thumbnail_image = (ImageView) convertView.findViewById(R.id.trailer_thumbnail_image);
            trailer_title = (TextView) convertView.findViewById(R.id.trailer_title);


            for (MovieTrailerResponse movieTrailerResponse : movieTrailerResponseList
                    ) {
                name = movieTrailerResponse.getName();
                type = movieTrailerResponse.getType();
                site = movieTrailerResponse.getSite();
                iso_639_1 = movieTrailerResponse.getIso_639_1();
                iso_3166_1 = movieTrailerResponse.getIso_3166_1();
                id = movieTrailerResponse.getId();
                size = (int) movieTrailerResponse.getSize();

                System.out.println(YOUTUBE_THUMBNAIL_BASE_URL + movieTrailerResponse.getKey() + "/maxresdefault.jpg");
                Picasso.with(context)
                        .load(YOUTUBE_THUMBNAIL_BASE_URL + movieTrailerResponse.getKey() + "/maxresdefault.jpg")
                        .centerCrop()
                        .noFade()
                        .resize(150, 150).error(R.drawable.ic_play_arrow_black_84dp)
                        .into(trailer_thumbnail_image);


                break;
            }
        }


        return convertView;
    }
}
