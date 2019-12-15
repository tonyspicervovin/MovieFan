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

    @GET("movies/{id}/")
    Call<Movie> get(@Path("id") int id);
    //get movie by ID

    @POST("movies/")
    Call<Void> insert(@Body Movie movie);
    // insert movie

    @PATCH("movies/{id}/")
    Call<Void> update(@Body Movie m, @Path("id") int id);
    // update movie

    @DELETE("movies/{id}/")
    Call<Void> delete(@Path("id") int id);
    // delete movie

}