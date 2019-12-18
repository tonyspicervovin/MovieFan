package com.tony.moviefan.MovieApi;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieAPICall {

    private static String TAG = "MOVIE_API_CALL";
    private static String key = BuildConfig.MOVIE_TOKEN;
    private ArrayList<Movie> mMovies;

    private JSONObject returnResponse;


    public static MutableLiveData<List<Movie>> getCurrentMovies(Context context) {

        Log.d(TAG, "Calling api");

        final MutableLiveData<List<Movie>> liveDataMovie = new MutableLiveData<>();

        RequestQueue queue = Volley.newRequestQueue(context);
        String urlMovies = "https://api.themoviedb.org/3/movie/now_playing?api_key="+key+"&language=en-US&page=1";
      //  final SearchFragment searchFragment = new SearchFragment();

        JsonObjectRequest movieRequest = new JsonObjectRequest(Request.Method.GET, urlMovies, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "got response");

                        List<Movie> movies = processResponse(response);
                        liveDataMovie.setValue(movies);
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

        return liveDataMovie;

    }


    private static List<Movie> processResponse(JSONObject response) {

        String title, genreCombined = "";
        ArrayList <Movie> Movies  = new ArrayList<>();
        try {
            JSONArray resultArray = response.getJSONArray("results");
            for (int i = 0; i < resultArray.length(); i++) {

                JSONObject obj = resultArray.getJSONObject(i);
                String lang = obj.getString("original_language");
                if (lang.equals("en")){
                    title = obj.getString("original_title");
                }else {
                    title = obj.getString("title");
                }

                String description = obj.getString("overview");
                String date = obj.getString("release_date");
                JSONArray genre_ids = obj.getJSONArray("genre_ids");

                for (int j = 0; j < genre_ids.length(); j++) {
                    int code = genre_ids.getInt(j);
                    String genre = convertToGenreString(code);
                    genreCombined = genreCombined +" " + genre;
                    Log.d(TAG, "genre " + genre_ids.getInt(j));
                }

                Log.d(TAG, "Adding movie "+ title);
                Movies.add(new Movie(title, description, genreCombined, date));
                genreCombined = "";


            }
            return Movies;

        } catch (JSONException e) {
            Log.e(TAG, "Error processing JSON resposne", e);
        }
        return null;

    }


    private static String  convertToGenreString(int code) {

        String genreString  = genres.get(code);

        return genreString;
    }


}
