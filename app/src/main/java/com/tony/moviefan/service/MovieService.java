package com.tony.moviefan.service;

import com.tony.moviefan.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MovieService {

    @GET("movies/")
    Call<List<Movie>> getAllMovies();

    // Example - get movie by ID
    @GET("movies/{id}/")
    Call<Movie> get(@Path("id") int id);

    //  POST insert movie
    @POST("movies/")
    Call<Void> insert(@Body Movie movie);

    //  PATCH update movie
    @PATCH("movies/{id}")
    Call<Void> update(@Body Movie m, @Path("id") int id);

    @DELETE("movies/{id}/")
    Call<Void> delete(@Path("id") int id);


}