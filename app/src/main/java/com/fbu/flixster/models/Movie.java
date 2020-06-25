package com.fbu.flixster.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Movie.
 */
@Parcel
public class Movie {

    public static final String TAG = "Movie";

    public String posterPath;
    public String title;
    public String overview;
    public String backdropPath;

    public double voteAverage;

    // for Parceler
    public Movie() {}

    /**
     * Creates a Movie from the jsonObject
     * @param jsonObject The JSONObject to create a Movie from
     */
    public Movie(JSONObject jsonObject) {
        try {
            posterPath = jsonObject.getString("poster_path");
            backdropPath = jsonObject.getString("backdrop_path");
            title = jsonObject.getString("title");
            overview = jsonObject.getString("overview");
            voteAverage = jsonObject.getDouble("vote_average");
        } catch (JSONException e) {
            Log.e(TAG, "results not found in json", e);
        }
    }

    /**
     * Creates a list of movies
     * @param movieJsonArray The JSONArray containing movie elements
     * @return A list of all the movie elements
     * @throws JSONException
     */
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    /**
     * Get the poster's URL.
     * @return The movie's poster's URL.
     */
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    /**
     * Get the backdrop's URL.
     * @return The movie's backdrop's URL.
     */
    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    /**
     * Get the title.
     * @return The movie's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the overview.
     * @return The movie's overview.
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Get the vote average.
     * @return The movie's vote average.
     */
    public Double getVoteAverage() {
        return voteAverage;
    }
}
