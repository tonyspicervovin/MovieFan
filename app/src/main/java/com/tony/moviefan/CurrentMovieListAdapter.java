package com.tony.moviefan;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CurrentMovieListAdapter extends RecyclerView.Adapter<CurrentMovieListAdapter.CurrentMovieListViewHolder> {

    private List<Movie> data;
    private static String TAG = "CURRENT_MOVIE_LIST_ADAPTER";

    public CurrentMovieListAdapter(List<Movie> data) {this.data = data;}

    static class CurrentMovieListViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout;
        TextView titleTextView;
        TextView genreTextView;
        TextView descriptionTextView;
        TextView dateTextView;

        CurrentMovieListViewHolder(LinearLayout layout) {

            super(layout);
            this.layout = layout;
            titleTextView = layout.findViewById(R.id.titleTextView);
            genreTextView = layout.findViewById(R.id.genreTextView);
            dateTextView = layout.findViewById(R.id.dateTextView);
            descriptionTextView = layout.findViewById(R.id.descriptionTextView);

        }
    }

    @NonNull
    @Override
    public CurrentMovieListAdapter.CurrentMovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.current_movie_list_element, parent, false);
        CurrentMovieListViewHolder viewHolder = new CurrentMovieListViewHolder(layout);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentMovieListAdapter.CurrentMovieListViewHolder holder, int position) {

        Movie movie = data.get(position);
        String title = movie.getTitle();
        Log.d(TAG, "binding movie " + title);
        holder.descriptionTextView.setText(movie.getOverview());
        holder.titleTextView.setText(movie.getTitle());
        holder.genreTextView.setText(movie.getGenres());
        holder.dateTextView.setText(movie.getDate());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }




}
