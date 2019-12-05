package com.tony.moviefan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.tony.moviefan.Movie;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
import java.util.List;


public class SearchFragment extends Fragment {

    private static final String TAG = "SEARCH_FRAGMENT";

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Movie> mMovies;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mMovies=new ArrayList<>();

        mRecyclerView = view.findViewById(R.id.showing_movies_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CurrentMovieListAdapter(mMovies);
        mRecyclerView.setAdapter(mAdapter);


        getMoviesShowingCurrently();




        return view;
    }

    private void getMoviesShowingCurrently() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String urlMovies = "https://api.themoviedb.org/3/movie/now_playing?api_key="+key+"&language=en-US&page=1";
        String urlGenres = "https://api.themoviedb.org/3/genre/movie/list?api_key="+key+"&language=en-US";

        JsonObjectRequest movieRequest = new JsonObjectRequest(Request.Method.GET, urlMovies, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray resultArray = response.getJSONArray("results");
                            for (int i = 0; i < resultArray.length(); i++) {

                                JSONObject obj = resultArray.getJSONObject(i);
                                String title = obj.getString("original_title");
                                String description = obj.getString("overview");
                                String date = obj.getString("release_date");
                                String genre = "horror";
                                mMovies.add(new Movie(title, description, genre, date));
                                mAdapter.notifyItemInserted(mMovies.size() -1);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }



}
