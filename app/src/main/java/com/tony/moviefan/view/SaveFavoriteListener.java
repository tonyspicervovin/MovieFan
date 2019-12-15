package com.tony.moviefan.view;

import com.tony.moviefan.model.Movie;

public interface SaveFavoriteListener {

    void onAddFavorite(int position);
    void onDeleteFavorite(int position);
    void onLongClick(Movie movie);
    //listener for various click events
}
