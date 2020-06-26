package com.fbu.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.fbu.flixster.R;
import com.fbu.flixster.adapters.MovieAdapter;
import com.fbu.flixster.models.Movie;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private List<Movie> movies;
    public static final String TAG = "FavoritesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);

        movies = new ArrayList<Movie>();
        //TODO: Add loadItems(); once the favorite movies are persistent
        Movie newMovie;
        // TODO: Figure out why this is always null???
        newMovie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        if (newMovie != null) {
            Log.d(TAG, String.format("Adding '%s' to Favorites", newMovie.getTitle()));
            movies.add(newMovie);
        }

        // TODO: THIS HURTS SO BAD PLEASE GET THE Parcels.unwrap() to work
        movies.add(new Movie(getIntent().getStringExtra("posterPath"), getIntent().getStringExtra("title"),
                getIntent().getStringExtra("overview"), getIntent().getStringExtra("backdropPath"),
                getIntent().getStringExtra("movieId"), getIntent().getDoubleExtra("voteAverage", 0)));

        // Create the RecyclerView's Adapter and Listener
        //TODO: OnLongClickListener to remove movies
        final MovieAdapter movieAdapter = new MovieAdapter(this, movies, new MovieAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent i = new Intent(FavoritesActivity.this, MovieDetailsActivity.class);
                i.putExtra("movie", Parcels.wrap(movies.get(position)));
                startActivity(i);
            }
        });
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
        return true;
    }
    public void onShowMovies(MenuItem mi) {
        //TODO: Make the list of favorites persistent!!!
        //saveItems();
        finish();
    }
    /*
    private File getDataFile() {
        return new File(getFilesDir(), "data.json");
    }
    */
    /*
    private void loadItems() {

        try {
            ArrayList<String> lines = new ArrayList<String>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            lines = new ArrayList<String>();
        }

        //make jsonArray of jsonObject of movie primitives from the lines
        JSONArray results;
        try {
            Log.i(TAG, "Results: " + results.toString());
            movies.addAll(Movie.fromJsonArray(results));
            movieAdapter.notifyDataSetChanged();
            Log.i(TAG, "Movies: " + movies.size());
        } catch (JSONException e) {
            Log.e(TAG, "results not found in json", e);
        }
    }
*/
    /**
     * Stores Movie fields in JSON format so that it can be loaded using Movie.addAll(Movie.fromJsonArray(text of save file))
     */
    /*
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("Main Activity", "Error writing items", e);
        }
    }
    */
    //TODO: Remove this in favor of actual persistence!!!
    @Override
    public void onBackPressed() {
        Intent i = new Intent(FavoritesActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
    }
}