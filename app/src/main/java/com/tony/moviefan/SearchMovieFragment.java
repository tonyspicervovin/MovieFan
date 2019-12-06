package com.tony.moviefan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;






public class SearchMovieFragment extends Fragment {

    private static final String TAG = "SEARCH_MOVIE_FRAGMENT";

    private RecyclerView mRecyclerView;
    private CurrentMovieListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Movie> mMovies;
    private HashMap<Integer, String> genres;


    private static String key = BuildConfig.MOVIE_TOKEN;



    private OnFragmentInteractionListener mListener;

    public SearchMovieFragment() {
        // Required empty public constructor
    }


    public static SearchMovieFragment newInstance() {
        SearchMovieFragment fragment = new SearchMovieFragment();

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
        View view = inflater.inflate(R.layout.fragment_search_movie, container, false);
        mMovies = new ArrayList<Movie>();

        mRecyclerView = view.findViewById(R.id.show_searched_movie_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());

        mAdapter = new CurrentMovieListAdapter(mMovies);
        mRecyclerView.setAdapter(mAdapter);

        getGenres();


        return view;
    }

    private void getGenres() {
        SearchFragment getGenres = new SearchFragment();
        HashMap<Integer, String> theGenres = getGenres.getGenres();
        Log.d(TAG,theGenres.toString());
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
