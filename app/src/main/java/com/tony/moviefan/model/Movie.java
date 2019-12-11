package com.tony.moviefan.model;

public class Movie {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    private int id;
    private String title;
    private String overview;
    private String genres;
    private String date;
    private float rating;


    public Movie(String title, String overview, String genres, String date) {
        this.title = title;
        this.overview = overview;
        this.genres = genres;
        this.date = date;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", genres='" + genres + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public float getRating() {
        return rating;
    }
}
