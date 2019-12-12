package com.tony.moviefan.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.tony.moviefan.BuildConfig;
import com.tony.moviefan.R;
import com.tony.moviefan.model.Movie;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tony.moviefan.viewmodel.MoviesViewModel;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SearchFragment extends Fragment implements SaveFavoriteListener{

    private static final String TAG = "SEARCH_FRAGMENT";

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private CurrentMovieListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MoviesViewModel moviesViewModel;


    private HashMap<Integer, String> genres;

    private List<Movie> mMovies;
    private String title;
    private String genreCombined = "";

    private static String key = BuildConfig.MOVIE_TOKEN;


    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        moviesViewModel = ViewModelProviders.of(getActivity()).get(MoviesViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mMovies= new ArrayList<>();

        mRecyclerView = view.findViewById(R.id.showing_movies_list);
        //registering widgets

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //setting up recyclerview

        mAdapter = new CurrentMovieListAdapter(mMovies,this, "Save to Favorites");
        mRecyclerView.setAdapter(mAdapter);
        //setting up adapter

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        getGenres(queue);


        getMoviesShowingCurrently();



        return view;
    }

    public HashMap<Integer, String> getGenres(RequestQueue queue) {

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

    private void getMoviesShowingCurrently() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String urlMovies = "https://api.themoviedb.org/3/movie/now_playing?api_key="+key+"&language=en-US&page=1";


        JsonObjectRequest movieRequest = new JsonObjectRequest(Request.Method.GET, urlMovies, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList <Movie> Movies = processResponse(response);
                        mMovies = Movies;
                        Log.d(TAG, mMovies.toString());
                        mAdapter.setMovies(mMovies);
                        mAdapter.notifyDataSetChanged();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        queue.add(movieRequest);
    }

    private ArrayList<Movie> processResponse(JSONObject response) {

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

    private String convertToGenreString(int code) {

        String genreString  = genres.get(code);

        return genreString;
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAddFavorite(int position) {
        Movie movie = mMovies.get(position);
        moviesViewModel.insert(movie).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), "Insert " + s, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onDeleteFavorite(int position) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }



}
