package com.fbu.flixster.models;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.fbu.flixster.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

/**
 * Represents a Movie.
 */
@Parcel
public class Movie {

    static final String TAG = "Movie";
    private static final String VIDEO_ID_URL = "https://api.themoviedb.org/3/movie/%s/videos?api_key=" + BuildConfig.API_KEY;

    String posterPath;
    String title;
    String overview;
    String backdropPath;
    String movieId;
    double voteAverage;

    // for Parceler
    Movie() {}

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
            movieId = jsonObject.getString("id");
        } catch (JSONException e) {
            Log.e(TAG, "results not found in json", e);
        }

    }

    public Movie(String posterPath, String title, String overview, String backdropPath, String movieId, double voteAverage) {
        this.posterPath = posterPath;
        this.title = title;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.movieId = movieId;
        this.voteAverage = voteAverage;
    }
    /**
     * Creates a list of movies
     * @param movieJsonArray The JSONArray containing movie elements
     * @return A list of all the movie elements
     * @throws JSONException An element(s) are missing.
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

    /**
     * Get the video ID.
     */
    public String getMovieId() { return movieId; }
}
