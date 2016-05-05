package com.example.pavan.moviesapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by pavan on 3/11/2016.
 */
public class ImageAdapter extends BaseAdapter {

    private final String LOG_TAG = getClass().getSimpleName();

    ArrayList<String> moviePosters = new ArrayList<>();
    Context context;
    ImageView movie_posters_imageView;
    String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";


    public ImageAdapter(Context context, ArrayList<String> Posters) {
        this.context = context;
        this.moviePosters = Posters;
    }


    @Override
    public int getCount() {
        return moviePosters.size();
    }

    @Override
    public Object getItem(int position) {

        return moviePosters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return moviePosters.indexOf(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.movie_image, parent, false);
            movie_posters_imageView = (ImageView) convertView.findViewById(R.id.movie_poster_image_view);
        }

        else
            movie_posters_imageView = (ImageView) convertView;

         Picasso.with(context)
                 .load(BASE_POSTER_URL + moviePosters.get(position))
                .noFade()
                .resize(185 * 2, 278 * 2)
                .into(movie_posters_imageView);

        return movie_posters_imageView;
    }


}

