package com.tony.moviefan.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    protected Movie(Parcel in) {
        id = in.readInt();
        name = in.readString();
        overview = in.readString();
        genres = in.readString();
        date = in.readString();
        rating = in.readFloat();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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
    private String name;
    private String overview;
    private String genres;
    private String date;
    private float rating;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Movie(String name, String overview, String genres, String date) {
        this.name = name;
        this.overview = overview;
        this.genres = genres;
        this.date = date;
    }


    public void setRating(Float rating){this.rating = rating;}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
                "name='" + name + '\'' +
                ", overview='" + overview + '\'' +
                ", genres='" + genres + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public float getRating() {
        return rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(overview);
        parcel.writeString(genres);
        parcel.writeString(date);
        parcel.writeFloat(rating);
    }
}
