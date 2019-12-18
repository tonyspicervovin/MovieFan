package com.tony.moviefan.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tony.moviefan.BuildConfig;
import com.tony.moviefan.MovieApi.MovieAPICall;
import com.tony.moviefan.R;
import com.tony.moviefan.model.Movie;
import com.tony.moviefan.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SearchMovieFragment extends Fragment implements SaveFavoriteListener{

    private static final String TAG = "SEARCH_MOVIE_FRAGMENT";

    private RecyclerView mRecyclerView;
    private CurrentMovieListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText searchString;
    private Button searchButton;
    private ArrayList<Movie> mMovies;
    private HashMap<Integer, String> genres;

    private MoviesViewModel moviesViewModel;
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
        moviesViewModel = ViewModelProviders.of(getActivity()).get(MoviesViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_movie, container, false);
        mMovies = new ArrayList<Movie>();

        mRecyclerView = view.findViewById(R.id.show_matching_list);
        searchString = view.findViewById(R.id.search_favorite_string);
        searchButton = view.findViewById(R.id.searchMatchingButton);
        //registering widgets

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //setting up recyclerview

        mAdapter = new CurrentMovieListAdapter(mMovies, this, "Save to Favorites");
        mRecyclerView.setAdapter(mAdapter);
        //setting up adapter

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = searchString.getText().toString();
                if (search.isEmpty()) {
                    Toast.makeText(getContext(), "Enter a word", Toast.LENGTH_SHORT).show();
                    return;
                }
                //retrieving users search, showing msg if nothing is entered
                getSearchedForMovies(search);
            }
        });
        return view;
    }

    private void getSearchedForMovies(String search) {

        LiveData<List<Movie>> ld = MovieAPICall.getSearchedForMovies(this.getContext(), search);
        ld.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                // now have list of movies
                // add to adapter
                mMovies = (ArrayList<Movie>) movies;
                mAdapter.setMovies(movies);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
    //calling api and observing livedata for list of movies

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
    @Override
    public void onAddFavorite(int position) {
        Log.d(TAG, "adding movie");
        Movie movie = mMovies.get(position);
        moviesViewModel.insert(movie).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), "Insert " + s, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onDeleteFavorite(int position) {

    }
    @Override
    public void onLongClick(Movie movie) {

    }
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
