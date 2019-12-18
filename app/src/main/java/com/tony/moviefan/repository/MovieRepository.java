package com.tony.moviefan.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.tony.moviefan.model.Movie;
import com.tony.moviefan.service.AuthorizationHeaderInterceptor;
import com.tony.moviefan.service.MovieService;
import com.tony.moviefan.view.SaveFavoriteListener;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {

    private static final String TAG = "MOVIE_REPOSITORY";

    private MovieService mMovieService;
    private final String baseURL = "https://rest-movie-api.herokuapp.com/api/";
    private MutableLiveData<List<Movie>> mAllMovies;

    public MovieRepository() {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthorizationHeaderInterceptor())
                .retryOnConnectionFailure(true)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mMovieService = retrofit.create(MovieService.class);
        mAllMovies = new MutableLiveData<>();
        //establishing connection to rest api with authorization header
    }

    public MutableLiveData<List<Movie>> getAllMovies() {

        mMovieService.getAllMovies().enqueue(new Callback<List<Movie>>() {

            @Override
            public void onResponse(Call<List<Movie>> call, retrofit2.Response<List<Movie>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "getAllMovies response body: " + response.body());
                    mAllMovies.setValue(response.body());
                }else {
                    Log.e(TAG, "Error getting all movies, message from server: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.e(TAG, "Error fetching all movies", t);
            }
        });
        return mAllMovies;
        // calling movie service for retrieve all movies and return to viewmodel
    }

    public MutableLiveData<Movie> getMovie(final int id) {


        final MutableLiveData<Movie> movie = new MutableLiveData<>();

        mMovieService.get(id).enqueue(new Callback<Movie>() {

            @Override
            public void onResponse(Call<Movie> call, retrofit2.Response<Movie> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "fetched movie " + response.body());
                    movie.setValue(response.body());
                } else {
                    Log.e(TAG, "Error getting movie id " + id + " because " + response.message());
                    movie.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e(TAG, "Error getting movie id " + id , t);
            }
        });
        return movie;
        //calling movie service to get move by id, returning to viewmodel
    }

    public MutableLiveData<String> insert(final Movie movie) {

        final MutableLiveData<String> insertResult = new MutableLiveData<>();

        mMovieService.insert(movie).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "inserted " + movie);
                    insertResult.setValue("success");
                    getAllMovies();
                }else {
                    String error;
                    try {
                        error = response.errorBody().string();
                        if (error.contains("duplicate key value violates unique constraint")) {
                            insertResult.setValue("failed, duplicate movie");
                        }else {
                            insertResult.setValue(error);
                            Log.e(TAG, "Error inserting movie, response from server: " + error + " message " + response.message());
                        }
                    }catch (Exception e) {
                        insertResult.setValue("error");
                        Log.e(TAG, "Error inserting movie, message from server: " + response.message());
                    }
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                insertResult.setValue("error");
                Log.e(TAG, "Error inserting movie " + movie, t);
            }
        });
        return insertResult;
        //calling movie service to insert movie, returning live data with result to display to user
    }

    public MutableLiveData<String> update(final Movie movie) {
        final MutableLiveData<String> updateResult = new MutableLiveData<>();

        mMovieService.update(movie, movie.getId()).enqueue(new Callback<Void>() {


            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    updateResult.setValue("Success updating rating");
                    Log.d(TAG, "updating movie " + movie);
                    getAllMovies();
                }else {
                    updateResult.setValue("Error updating rating");
                    Log.e(TAG, "Error updating movie, message from server: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                updateResult.setValue("Error updating rating");
                Log.e(TAG, "Error updating movie " + movie, t);

            }
        });
        return updateResult;

        //updating movie
    }


    public MutableLiveData<String> delete(final Movie movie) {

        final MutableLiveData<String> deleteResult = new MutableLiveData<>();

        mMovieService.delete(movie.getId()).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "deleted movie " + movie);
                    deleteResult.setValue("success");
                    getAllMovies();
                }else {
                    Log.e(TAG, "Error deleting movie, message from server: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Error deleting movie " + movie, t);
                deleteResult.setValue("Error");

            }
        });
        return deleteResult;
        //deleting movie
    }
}