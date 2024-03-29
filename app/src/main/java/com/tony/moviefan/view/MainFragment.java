package com.tony.moviefan.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.tony.moviefan.R;


public class MainFragment extends Fragment {

    private ImageButton searchButton;
    private ImageButton newButton;
    private ImageButton viewFavoriteButton;

    private OnFragmentInteractionListener mListener;

    private static String TAG = "MAIN_FRAGMENT";

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        searchButton = view.findViewById(R.id.searchButton);
        newButton = view.findViewById(R.id.new_button);
        viewFavoriteButton = view.findViewById(R.id.view_favorites_button);
        //registering widgets

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NewMoviesFragment newMoviesFragment = NewMoviesFragment.newInstance();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, newMoviesFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        //showing new movies playing in theatre fragment

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SearchMovieFragment searchMovieFragment = SearchMovieFragment.newInstance();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, searchMovieFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        //showing fragment for searching for movies

        viewFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewFavoritesFragment viewFavoritesFragment = ViewFavoritesFragment.newInstance();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, viewFavoritesFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return view;
        //showing view favorites fragment
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
