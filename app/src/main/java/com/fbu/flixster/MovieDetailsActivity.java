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
import com.fbu.flixster.adapters.MovieAdapter;
import com.fbu.flixster.models.Movie;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * This Activity shows a Movie's details
 */
public class MovieDetailsActivity extends AppCompatActivity {

    public static final String TAG = "MovieDetailsActivity";

    Movie movie;

    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivBackdrop;
    View.OnClickListener playTrailerListener;

    /**
     * Shows the Movie Details View.
     * @param savedInstanceState The Activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d(TAG, String.format("Showing details for '%s'", movie.getTitle()));

        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        rbVoteAverage = findViewById(R.id.rbVoteAverage);
        ivBackdrop = findViewById(R.id.ivBackdrop);

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));


        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : 0);
        Glide.with(getApplicationContext()).load(movie.getBackdropPath()).placeholder(R.drawable.flicks_movie_placeholder).transform(new RoundedCornersTransformation(30, 0)).into(ivBackdrop);

        playTrailerListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                i.putExtra("videoId", movie.getVideoId());
                startActivity(i);
            }
        };
        ivBackdrop.setOnClickListener(playTrailerListener);
    }
}
