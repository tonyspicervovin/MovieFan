package com.tony.moviefan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;






public class SearchMovieFragment extends Fragment {

    private static final String TAG = "SEARCH_MOVIE_FRAGMENT";

    private RecyclerView mRecyclerView;
    private CurrentMovieListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText searchString;
    private Button searchButton;

    private String title;
    private String genreCombined = "";

    private ArrayList<Movie> mMovies;
    private HashMap<Integer, String> genres;



    private static String key = BuildConfig.MOVIE_TOKEN;



    private OnFragmentInteractionListener mListener;

    public SearchMovieFragment() {
        // Required empty public constructor
    }


    public static SearchMovieFragment newInstance() {
        SearchMovieFragment fragment = new SearchMovieFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_movie, container, false);
        mMovies = new ArrayList<Movie>();

        mRecyclerView = view.findViewById(R.id.show_searched_movie_list);
        searchString = view.findViewById(R.id.searchString);
        searchButton = view.findViewById(R.id.searchMatchingButton);
        //registering widgets

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //setting up recyclerview

        mAdapter = new CurrentMovieListAdapter(mMovies);
        mRecyclerView.setAdapter(mAdapter);
        //setting up adapter

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = searchString.getText().toString();
                if (search.isEmpty()) {
                    Toast.makeText(getContext(), "Enter a word", Toast.LENGTH_SHORT).show();
                    return;
                }
                //retrieving users search, showing msg if nothing is entered
                getSearchedForMovies(search);
            }
        });


        getGenres();

        return view;
    }


    private void getSearchedForMovies(String search) {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String urlMovies = "https://api.themoviedb.org/3/search/movie?api_key="+key+"&language=en-US&page=1&include_adult=false&query="+search;


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
            if (resultArray.length() == 0){
                Toast.makeText(getContext(), "No results found", Toast.LENGTH_SHORT).show();
            }
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
                    genreCombined = genreCombined + " " + genre;

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


    private void getGenres() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        SearchFragment searchFragment = new SearchFragment();
        HashMap<Integer, String> theGenres = searchFragment.getGenres(queue);
        genres = theGenres;
        Log.d(TAG,theGenres.toString());
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private String convertToGenreString(int code) {

        String genreString  = genres.get(code);

        return genreString;
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


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
