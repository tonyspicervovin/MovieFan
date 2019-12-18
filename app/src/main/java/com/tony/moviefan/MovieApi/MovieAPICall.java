package com.tony.moviefan.MovieApi;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tony.moviefan.BuildConfig;
import com.tony.moviefan.model.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


    public class MovieAPICall {

        private static String TAG = "MOVIE_API_CALL";
        private static String key = BuildConfig.MOVIE_TOKEN;
        private static HashMap<Integer, String> genres;

        public static MutableLiveData<List<Movie>> getSearchedForMovies(Context context, String search) {

            final MutableLiveData<List<Movie>> liveDataMovie = new MutableLiveData<>();
            RequestQueue queue = Volley.newRequestQueue(context);
            getGenres(queue);
            String urlMovies = "https://api.themoviedb.org/3/search/movie?api_key="+key+"&language=en-US&page=1&include_adult=false&query="+search;
            JsonObjectRequest movieRequest = new JsonObjectRequest(Request.Method.GET, urlMovies, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            List<Movie> movies = processResponse(response);
                            liveDataMovie.setValue(movies);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }
            );
            queue.add(movieRequest);
            return liveDataMovie;
        // making call to api for searched movie, processing response and assigning live data to it which is observed in SearchMovieFragment
        }

        public static MutableLiveData<List<Movie>> getCurrentMovies(Context context) {

            Log.d(TAG, "Calling api");

            final MutableLiveData<List<Movie>> liveDataMovie = new MutableLiveData<>();
            RequestQueue queue = Volley.newRequestQueue(context);
            getGenres(queue);
            String urlMovies = "https://api.themoviedb.org/3/movie/now_playing?api_key="+key+"&language=en-US&page=1";
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
        // Making call for new movies in theatres, processing response to list of movies and assigning live data to that which is observed
        // in the NewMoviesFragment

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
                //processing making a list of movies from the json response

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
        //converting genre code to a string

        public static HashMap<Integer, String> getGenres(RequestQueue queue) {

            Log.d(TAG, "fetching genres");
            genres = new HashMap<>();
            String urlGenres = "https://api.themoviedb.org/3/genre/movie/list?api_key="+key+"&language=en-US";
            JsonObjectRequest movieRequest = new JsonObjectRequest(Request.Method.GET, urlGenres, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray resultArray = response.getJSONArray("genres");
                                for (int i = 0; i < resultArray.length(); i++) {
                                    JSONObject obj = resultArray.getJSONObject(i);
                                    int id = obj.getInt("id");
                                    String name = obj.getString("name");
                                    Log.d(TAG, "Adding " + name);
                                    genres.put(id, name);
                                }
                            } catch (JSONException e) {
                                Log.e(TAG, "Error processing JSON resposne", e);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }
            );
            queue.add(movieRequest);

            return genres;
        }
        //retrieving genre codes and corresponding strings from api
        //putting them in a hash map to be able to convert our code to a string
    }
