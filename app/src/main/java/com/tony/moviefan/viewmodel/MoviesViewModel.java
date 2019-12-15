package com.tony.moviefan.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.tony.moviefan.model.Movie;
import com.tony.moviefan.repository.MovieRepository;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {

    private MovieRepository mMovieRepository;

    private MutableLiveData<List<Movie>> allMovies;

    public MoviesViewModel(@NonNull Application application){

        super(application);
        mMovieRepository = new MovieRepository();
        allMovies = mMovieRepository.getAllMovies();
    }
    public MutableLiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }

    public MutableLiveData<Movie> getMovie(int id) {
        return mMovieRepository.getMovie(id);
    }

    public MutableLiveData<String> insert(Movie movie) {
        return mMovieRepository.insert(movie);
    }

    public MutableLiveData<String> update(Movie movie) {
        return mMovieRepository.update(movie);
    }

    public MutableLiveData<String> delete(Movie movie) {
        return mMovieRepository.delete(movie);
    }
}
