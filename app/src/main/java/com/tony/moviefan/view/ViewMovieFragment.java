package com.tony.moviefan.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tony.moviefan.R;
import com.tony.moviefan.model.Movie;
import com.tony.moviefan.viewmodel.MoviesViewModel;

import java.io.Serializable;


public class ViewMovieFragment extends Fragment {


    private static final String TAG = "VIEW_MOVIE_FRAGMENT";

    private Movie movie;

    private TextView showTitle;
    private TextView showGenres;
    private TextView showDate;
    private TextView showDescription;
    private RatingBar ratingBar;
    private Button saveRating;
    private Float rating;
    private ImageButton rottenTomatoeButton;

    private MoviesViewModel moviesViewModel;




    private OnFragmentInteractionListener mListener;

    public ViewMovieFragment(Movie movie) {
        // Required empty public constructor
    }


    public static ViewMovieFragment newInstance(Movie movie) {
        ViewMovieFragment fragment = new ViewMovieFragment(movie);
        Bundle args = new Bundle();
        args.putParcelable("movie", movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moviesViewModel = ViewModelProviders.of(getActivity()).get(MoviesViewModel.class);
        if (getArguments() != null) {
            movie = getArguments().getParcelable("movie");

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_movie, container, false);

        showTitle = view.findViewById(R.id.show_title_movie);
        showDate = view.findViewById(R.id.show_date);
        showGenres = view.findViewById(R.id.show_genres);
        showDescription = view.findViewById(R.id.show_description);
        ratingBar = view.findViewById(R.id.ratingBar);
        saveRating = view.findViewById(R.id.save_rating_button);
        rottenTomatoeButton = view.findViewById(R.id.rotten_tomatoe_button);

        showTitle.setText(movie.getName());
        showDate.setText("Release Date: " + movie.getDate());
        showGenres.setText("Genres: " + movie.getGenres());
        showDescription.setText("Description: " + movie.getOverview());


        ratingBar.setRating(movie.getRating());

        rottenTomatoeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.rottentomatoes.com/search/?search=" + movie.getName()));
                startActivity(browserIntent);
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = ratingBar.getRating();

            }
        });

        saveRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               movie.setRating(rating);
                Log.d(TAG, String.valueOf(movie.getRating()));
                moviesViewModel.update(movie);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
