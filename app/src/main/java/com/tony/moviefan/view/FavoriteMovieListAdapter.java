package com.tony.moviefan.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.tony.moviefan.R;
import com.tony.moviefan.model.Movie;
import com.tony.moviefan.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieListAdapter extends Adapter<FavoriteMovieListAdapter.FavoriteMovieListViewHolder> {

    private List<Movie> data;
    private static String TAG = "FAVORITE_MOVIE_LIST_ADAPTER";

    private SaveFavoriteListener listener;

    public FavoriteMovieListAdapter(List<Movie> data) {
        this.data = data;
        this.listener = listener;

    }

    public void setMovies(List<Movie> data) {
        this.data = data;
    }


    static class FavoriteMovieListViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout;
        TextView titleTextView;
        TextView genreTextView;
        TextView descriptionTextView;
        TextView dateTextView;
        Button favoriteButton;
        SaveFavoriteListener listener;

        FavoriteMovieListViewHolder(LinearLayout layout, SaveFavoriteListener listener) {

            super(layout);
            this.layout = layout;
            this.listener = listener;

            titleTextView = layout.findViewById(R.id.titleTextView);
            genreTextView = layout.findViewById(R.id.genreTextView);
            dateTextView = layout.findViewById(R.id.dateTextView);
            descriptionTextView = layout.findViewById(R.id.descriptionTextView);
            favoriteButton = layout.findViewById(R.id.saveFavoriteButton);


        }
    }

    @NonNull
    @Override
    public FavoriteMovieListAdapter.FavoriteMovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_movie_list_element, parent, false);
        FavoriteMovieListViewHolder viewHolder = new FavoriteMovieListViewHolder(layout,listener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteMovieListAdapter.FavoriteMovieListViewHolder holder, final int position) {
        final Movie movie = data.get(position);
        String name = movie.getName();
        Log.d(TAG, "binding movie " + name);
        holder.descriptionTextView.setText(movie.getOverview());
        holder.titleTextView.setText(movie.getName());
        holder.genreTextView.setText(movie.getGenres());
        holder.dateTextView.setText(movie.getDate());


        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onAddFavorite(position);


                Log.d(TAG, "Saving " + movie.getName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
