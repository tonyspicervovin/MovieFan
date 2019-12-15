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

import com.tony.moviefan.R;
import com.tony.moviefan.model.Movie;
import com.tony.moviefan.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;


public class CurrentMovieListAdapter extends RecyclerView.Adapter<CurrentMovieListAdapter.CurrentMovieListViewHolder> {

    private List<Movie> data;
    private String buttonText;
    private static String TAG = "CURRENT_MOVIE_LIST_ADAPTER";

    private SaveFavoriteListener listener;

    public CurrentMovieListAdapter(List<Movie> data, SaveFavoriteListener listener, String buttontext) {
        this.data = data;
        this.listener = listener;
        this.buttonText = buttontext;

    }

    public void setMovies(List<Movie> data) {
        this.data = data;
    }

    static class CurrentMovieListViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout;
        TextView titleTextView;
        TextView genreTextView;
        TextView descriptionTextView;
        TextView dateTextView;
        Button favoriteButton;
        SaveFavoriteListener listener;

        CurrentMovieListViewHolder(LinearLayout layout, final SaveFavoriteListener listener) {

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
    public CurrentMovieListAdapter.CurrentMovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.current_movie_list_element, parent, false);
        CurrentMovieListViewHolder viewHolder = new CurrentMovieListViewHolder(layout,listener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CurrentMovieListAdapter.CurrentMovieListViewHolder holder, final int position) {

        final Movie movie = data.get(position);
        String name = movie.getName();
        Log.d(TAG, "binding movie " + name);
        holder.descriptionTextView.setText("Description: " + movie.getOverview());
        holder.titleTextView.setText("Title: " + movie.getName());
        holder.genreTextView.setText("Genres: " + movie.getGenres());
        holder.dateTextView.setText("Date Released: " + movie.getDate());
        holder.favoriteButton.setText(buttonText);

        holder.titleTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(movie);
                return false;
            }
        });
        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonText.equals("Delete from Favorites")){
                    listener.onDeleteFavorite(position);
                }
                listener.onAddFavorite(position);
                Log.d(TAG, "Saving " + movie.getName());
            }
        });

    }
    @Override
    public int getItemCount() {
        return data.size();
    }

}
