package com.tony.moviefan;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tony.moviefan.model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

public class TestMovieFan {

    private static final String TAG = "TEST_MOVIE";

    @Test
    public void TestProcessResponse() throws Exception {

        JSONObject jsonObject = new JSONObject("{\n" +
                "page: 1,\n" +
                "total_results: 3,\n" +
                "total_pages: 1,\n" +
                "results: [\n" +
                "{\n" +
                "popularity: 13.034,\n" +
                "id: 87516,\n" +
                "video: false,\n" +
                "vote_count: 1117,\n" +
                "vote_average: 5.9,\n" +
                "title: \"Oldboy\",\n" +
                "release_date: \"2013-11-14\",\n" +
                "original_language: \"en\",\n" +
                "original_title: \"Oldboy\",\n" +
                "genre_ids: [\n" +
                "18,\n" +
                "53,\n" +
                "9648,\n" +
                "28\n" +
                "],\n" +
                "backdrop_path: \"/bk7oQqahFMarYBFri6xzjvXDzwm.jpg\",\n" +
                "adult: false,\n" +
                "overview: \"An everyday man has only three and a half days and limited resources to discover why he was imprisoned in a nondescript room for 20 years without any explanation.\",\n" +
                "poster_path: \"/cmspwpe1usgG5hYijfEcTrueKRC.jpg\"\n" +
                "},\n" +
                "{\n" +
                "popularity: 20.305,\n" +
                "vote_count: 4125,\n" +
                "video: false,\n" +
                "poster_path: \"/rIZX6X0MIHYEebk6W4LABT9VP2c.jpg\",\n" +
                "id: 670,\n" +
                "adult: false,\n" +
                "backdrop_path: \"/cne1AaNoVPHOEUiwcdS7sSo1uJ5.jpg\",\n" +
                "original_language: \"ko\",\n" +
                "original_title: \"올드보이\",\n" +
                "genre_ids: [\n" +
                "28,\n" +
                "18,\n" +
                "9648,\n" +
                "53\n" +
                "],\n" +
                "title: \"Oldboy\",\n" +
                "vote_average: 8.2,\n" +
                "overview: \"With no clue how he came to be imprisoned, drugged and tortured for 15 years, a desperate businessman seeks revenge on his captors.\",\n" +
                "release_date: \"2003-09-28\"\n" +
                "},\n" +
                "{\n" +
                "popularity: 2.335,\n" +
                "id: 41454,\n" +
                "video: false,\n" +
                "vote_count: 10,\n" +
                "vote_average: 5.9,\n" +
                "title: \"Oldboys\",\n" +
                "release_date: \"2009-12-25\",\n" +
                "original_language: \"da\",\n" +
                "original_title: \"Oldboys\",\n" +
                "genre_ids: [\n" +
                "35,\n" +
                "18\n" +
                "],\n" +
                "backdrop_path: \"/rxc0IDkfOpW5KmGcVdJD6HdL2Rb.jpg\",\n" +
                "adult: false,\n" +
                "overview: \"Now in his fifties, Vagn leads a solitary life and plays football with a group of similarly aged men, some even older. After being left behind at a petrol station by his teammates on their way to a match in Sweden, he encounters a young habitual offender and together they set off in hot pursuit of Vagn's buddies. A Nordic road movie taking in a whole series of comic, serious and, above all, well-written scenarios.\",\n" +
                "poster_path: \"/jaChFl4VzBe3QsKSoxB5BbNTX94.jpg\"\n" +
                "}\n" +
                "]\n" +
                "}");

        /*JSONObject jsonObject = new JSONObject();
        jsonObject.put("page",1);
        jsonObject.put("total_results", 1);
        jsonObject.put("total_pages", 1);
        JSONArray jsonArray = new JSONArray();
        JSONObject movie1 = new JSONObject();
        JSONObject movie2 = new JSONObject();
        movie1.put("title", "harry potter");
        movie1.put("overview", "magic movie");
        movie1.put("genre_ids", 18);
        movie1.put("release_date", "2013-11-14");
        movie2.put("title", "scarface");
        movie2.put("overview", "mob movie");
        movie2.put("genre_ids", 53);
        movie2.put("release_date", "2013-11-14");
        jsonArray.put(movie1);
        jsonArray.put(movie2);
        jsonObject.put("-results", jsonArray);
        Log.d(TAG, jsonObject.toString());*/



    }

}
