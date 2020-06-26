package com.fbu.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.fbu.flixster.adapters.MovieAdapter;
import com.fbu.flixster.databinding.ActivityMovieDetailsBinding;
import com.fbu.flixster.models.Movie;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

//TODO: Add landscape view!
//TODO: Use dynamically sized images!
/**
 * This Activity shows a Movie's details
 */
public class MovieDetailsActivity extends AppCompatActivity {

    public static final String TAG = "MovieDetailsActivity";
    private static final String VIDEO_ID_URL = "https://api.themoviedb.org/3/movie/%s/videos?api_key=" + BuildConfig.API_KEY;
    Movie movie;

    /**
     * Shows the Movie Details View.
     * @param savedInstanceState The Activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d(TAG, String.format("Showing details for '%s'", movie.getTitle()));

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));


        binding.tvTitle.setText(movie.getTitle());
        binding.tvOverview.setText(movie.getOverview());
        float voteAverage = movie.getVoteAverage().floatValue();
        binding.rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : 0);
        Glide.with(getApplicationContext()).load(movie.getBackdropPath()).placeholder(R.drawable.flicks_movie_placeholder).transform(new RoundedCornersTransformation(30, 0)).into(binding.ivBackdrop);

        // TODO: This may cause a bug that makes the first backdrop clicked on open a MovieTrailerActivity but the trailer doesn't play
        // now acquire the videoID
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEO_ID_URL, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject videoJsonObject = json.jsonObject;
                try {
                    final String videoId = videoJsonObject.getJSONArray("results").getJSONObject(0).getString("key");
                    Log.d(TAG, String.format("Got key for %s", movie.getTitle()));
                    binding.ivBackdrop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                            i.putExtra("videoId", videoId);
                            startActivity(i);
                        }
                    });
                    binding.ivPlay.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    Log.e(TAG, "results not found in json", e);
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }
}
