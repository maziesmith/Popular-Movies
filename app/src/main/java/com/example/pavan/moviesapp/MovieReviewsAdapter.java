package com.example.pavan.moviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pavan on 4/15/2016.
 */
public class MovieReviewsAdapter extends BaseAdapter {

    private final String LOG_TAG = getClass().getSimpleName();

    int noOfReviews;
    TextView review_author_name, review_content;


    List<String> author_name = new ArrayList<>();
    List<String> author_review = new ArrayList<>();
    Context context;

    public MovieReviewsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return noOfReviews;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.reviews, parent, false);

            review_author_name = (TextView) convertView.findViewById(R.id.review_author_name);
            review_content = (TextView) convertView.findViewById(R.id.review_content);


            review_author_name.setText(author_name.get(position));
            review_content.setText(author_review.get(position));

        }

        return convertView;
    }
}
