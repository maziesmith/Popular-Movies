package com.example.pavan.moviesapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by pavan on 4/10/2016.
 */
public class MovieTrailerAdapter extends BaseAdapter {


    private final String LOG_TAG = getClass().getSimpleName();

    ArrayList<String> Name, Key;

    int noOfTrailers;
    Context context;
    ImageView trailer_thumbnail_image;
    TextView trailer_title;
    private String YOUTUBE_THUMBNAIL_BASE_URL = "http://img.youtube.com/vi/";


    public MovieTrailerAdapter(Context context, ArrayList<String> name, ArrayList<String> key) {
        this.context = context;
        this.Name = name;
        this.Key = key;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.trailers, parent, false);
            trailer_thumbnail_image = (ImageView) convertView.findViewById(R.id.trailer_thumbnail_image);
            trailer_title = (TextView) convertView.findViewById(R.id.trailer_title);

            Log.i(LOG_TAG, "position : " + position);

            Log.i(LOG_TAG, "names in adapter pos : " + Name.get(position));
            Log.i(LOG_TAG, "keys in adapter pos : " + Key.get(position));

            Log.i(LOG_TAG, "names in adapter : " + Name);
            Log.i(LOG_TAG, "keys in adapter : " + Key);

            trailer_title.setText(Name.get(position));

            Picasso.with(context)
                    .load(YOUTUBE_THUMBNAIL_BASE_URL + Key.get(position) + "/maxresdefault.jpg")
                    .centerCrop()
                    .noFade()
                    .resize(150, 150).error(R.drawable.ic_play_arrow_black_84dp)
                    .into(trailer_thumbnail_image);

        }

        return convertView;
    }
}
