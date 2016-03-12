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






    public ArrayList<String> moviePoster = new ArrayList();
    Context context;
    ImageView movie_posters_imageView;
    //    View movie_posters_imageView;
    String BASE_POSTER_URL;

//    int[] imageId = {
//            R.drawable.one,
//            R.drawable.two,
//            R.drawable.three,
//            R.drawable.four,
//            R.drawable.five,
//            R.drawable.six
//    };


    public ImageAdapter(Context context,ArrayList Posters) {
        this.moviePoster = Posters;
        this.context = context;
    }


    @Override
    public int getCount() {
//        return imageId.length;
//        System.out.println("get count image adapter : " + moviePoster);
//        System.out.println("in get count using getter : " + moviePoster);

        return moviePoster.size();
    }

    @Override
    public Object getItem(int position) {
//        return imageId[position];
        return moviePoster.get(position);
    }

    @Override
    public long getItemId(int position) {

//        return 0;
        return moviePoster.indexOf(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.movie_image, parent, false);
            movie_posters_imageView = (ImageView) convertView.findViewById(R.id.movie_poster_image_view);
        }

        else
            movie_posters_imageView = (ImageView) convertView;

//        movie_posters_imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        movie_posters_imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        movie_posters_imageView.setPadding(8, 8, 8, 8);

        BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";

        System.out.println("poster url : " + BASE_POSTER_URL+moviePoster.get(position));

//        Picasso.with(context).load(imageId[position]).centerCrop().resize(185*2,278*2).into(movie_posters_imageView);

        Picasso.with(context)
                .load(BASE_POSTER_URL + moviePoster.get(position))
                .noFade()
                .resize(185 * 2, 278 * 2)
                .into(movie_posters_imageView);

        return movie_posters_imageView;
    }


}

