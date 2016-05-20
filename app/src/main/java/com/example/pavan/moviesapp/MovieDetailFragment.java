package com.example.pavan.moviesapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class MovieDetailFragment extends Fragment {



    private MovieDetail_PagerAdapter movieDetail_pagerAdapter;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    public static MovieDetailFragment newInstance(Bundle bundle) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = bundle;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        movieDetail_pagerAdapter = new MovieDetail_PagerAdapter(getChildFragmentManager());

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.movie_details_view_pager);
        viewPager.setAdapter(movieDetail_pagerAdapter);


        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);

        tabLayout.setupWithViewPager(viewPager);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });

        return view;
    }

}
