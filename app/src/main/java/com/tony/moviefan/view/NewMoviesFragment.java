package com.tony.moviefan.view;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import com.tony.moviefan.BuildConfig;
import com.tony.moviefan.MovieApi.MovieAPICall;
import com.tony.moviefan.R;
import com.tony.moviefan.model.Movie;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tony.moviefan.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NewMoviesFragment extends Fragment implements SaveFavoriteListener{

    private static final String TAG = "SEARCH_FRAGMENT";

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private CurrentMovieListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MoviesViewModel moviesViewModel;


    private HashMap<Integer, String> genres;

    private List<Movie> mMovies;
    private String title;
    private String genreCombined = "";

    private static String key = BuildConfig.MOVIE_TOKEN;


    public NewMoviesFragment() {
        // Required empty public constructor
    }

    public static NewMoviesFragment newInstance() {
        NewMoviesFragment fragment = new NewMoviesFragment();

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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mMovies= new ArrayList<>();

        mRecyclerView = view.findViewById(R.id.showing_movies_list);
        //registering widgets

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //setting up recyclerview

        mAdapter = new CurrentMovieListAdapter(mMovies,this, "Save to Favorites");
        mRecyclerView.setAdapter(mAdapter);
        //setting up adapter

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        getMoviesShowingCurrently();
        return view;
    }



    private void getMoviesShowingCurrently() {

        LiveData<List<Movie>> ld = MovieAPICall.getCurrentMovies(this.getContext());
        ld.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {

                // now have list of movies
                // add to adapter
                mMovies = movies;
                mAdapter.setMovies(movies);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
        //calling api for movies and observing livedata

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