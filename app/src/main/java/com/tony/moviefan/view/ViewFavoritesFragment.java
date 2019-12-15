package com.tony.moviefan.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

import com.tony.moviefan.R;
import com.tony.moviefan.model.Movie;
import com.tony.moviefan.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewFavoritesFragment extends Fragment implements SaveFavoriteListener{

    private static final String TAG = "VIEW_FAVORITES_FRAGMENT";

    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private CurrentMovieListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button deleteButton;
    private Button searchFavorites;
    private EditText searchString;

    private ArrayList<Movie> mMovies;
    private ArrayList<Movie> matchingMovies;
    private Integer matchCount = 0;

    private MoviesViewModel moviesViewModel;

    public ViewFavoritesFragment() {
        // Required empty public constructor
    }

    public static ViewFavoritesFragment newInstance() {
        ViewFavoritesFragment fragment = new ViewFavoritesFragment();
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

        View view = inflater.inflate(R.layout.fragment_view_favorites, container, false);

        Log.d(TAG, "favorite fragment");

        deleteButton = view.findViewById(R.id.search_favorites);
        searchFavorites = view.findViewById(R.id.search_favorites);
        searchString = view.findViewById(R.id.search_favorite_string);
        mMovies = new ArrayList<Movie>();

        mRecyclerView = view.findViewById(R.id.show_favorite_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //setting up recyclerview

        mAdapter = new CurrentMovieListAdapter(mMovies, this, "Delete from Favorites");
        mRecyclerView.setAdapter(mAdapter);

        searchFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = searchString.getText().toString();
                matchingMovies = new ArrayList<Movie>();

                for (int i = 0; i < mMovies.size(); i++) {
                    String title = mMovies.get(i).getName();
                    String description = mMovies.get(i).getOverview();
                    Log.d(TAG, mMovies.get(i).getName() + " " + search);
                    if (title.contains(search) || description.contains(search)) {
                        matchCount += 1;
                        matchingMovies.add(mMovies.get(i));
                        mAdapter.setMovies(matchingMovies);
                        mAdapter.notifyDataSetChanged();
                    }
                    if (matchCount == 0) {
                        Toast.makeText(getActivity(), "No matching movies found", Toast.LENGTH_SHORT).show();
                    }
                }
                matchCount = 0;
            }
        });

        moviesViewModel.getAllMovies().observe(getActivity(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                mMovies = (ArrayList<Movie>) movies;
                mAdapter.setMovies(movies);
                mAdapter.notifyDataSetChanged();
            }
        });
        return view;
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

    @Override
    public void onAddFavorite(int position) {

    }

    @Override
    public void onDeleteFavorite(int position) {
        Log.d(TAG, String.valueOf(position));
        Movie movie = mMovies.get(position);
        moviesViewModel.delete(movie).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), "Delete " + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onLongClick(Movie movie) {

        /*Bundle bundle = new Bundle();
        bundle.putParcelable("movie", (Parcelable) movie);*/
        ViewMovieFragment viewMovieFragment = ViewMovieFragment.newInstance(movie);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, viewMovieFragment);
        ft.addToBackStack(null);
        ft.commit();

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
