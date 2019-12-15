package com.tony.moviefan.MovieApi;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tony.moviefan.BuildConfig;
import com.tony.moviefan.R;
import com.tony.moviefan.model.Movie;
import com.tony.moviefan.view.MainFragment;
import com.tony.moviefan.view.SearchFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieAPICall {

    private static String TAG = "MOVIE_API_CALL";
    private static String key = BuildConfig.MOVIE_TOKEN;
    private ArrayList<Movie> mMovies;

    private JSONObject returnResponse;


    public void getCurrentMovies(FragmentActivity activity) {

        Log.d(TAG, "Calling api");

        RequestQueue queue = Volley.newRequestQueue(activity);
        String urlMovies = "https://api.themoviedb.org/3/movie/now_playing?api_key="+key+"&language=en-US&page=1";
        final SearchFragment searchFragment = new SearchFragment();

        JsonObjectRequest movieRequest = new JsonObjectRequest(Request.Method.GET, urlMovies, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "got response");


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error", error);
                    }
                }
        );
        queue.add(movieRequest);

    }


}
