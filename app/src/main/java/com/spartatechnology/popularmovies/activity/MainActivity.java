package com.spartatechnology.popularmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.spartatechnology.moviedbapi.entity.Movie;
import com.spartatechnology.popularmovies.R;
import com.spartatechnology.popularmovies.adapter.MoviesAdapter;
import com.spartatechnology.popularmovies.constant.PopularMoviesConstants;
import com.spartatechnology.popularmovies.fragment.MainActivityFragment;
import com.spartatechnology.popularmovies.fragment.MovieDetailActivityFragment;

/**
 * Created by kadu on 5/11/16.
 */
public class MainActivity extends AppCompatActivity implements MoviesAdapter.OnMovieSelectedListener {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, new MovieDetailActivityFragment())
                        .commit();
            }
        } else {
            mTwoPane = false;
        }

        Bundle bundle = new Bundle();
        bundle.putBoolean(MainActivityFragment.TWO_PANE, mTwoPane);

        final  MainActivityFragment moviesFragment = new MainActivityFragment();
        moviesFragment.setArguments(bundle);

        //if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, moviesFragment).commit();
        //}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieListSelected(Movie movie) {

        if (mTwoPane) {
            MovieDetailActivityFragment movieDetailsFragment = new MovieDetailActivityFragment();

            final Bundle movieBundle = new Bundle();
            movieBundle.putLong(PopularMoviesConstants.EXTRA_MOVIE_INFO_ID, movie.getId());
            movieBundle.putString(PopularMoviesConstants.EXTRA_MOVIE_INFO_IMAGE, movie.getPosterPath());
            movieDetailsFragment.setArguments(movieBundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, movieDetailsFragment)
                    .commit();
        } else {
            final Intent intent = new Intent(this, MovieDetailActivity.class)
                    .putExtra(PopularMoviesConstants.EXTRA_MOVIE_INFO_ID, movie.getId())
                    .putExtra(PopularMoviesConstants.EXTRA_MOVIE_INFO_IMAGE, movie.getPosterPath());
            startActivity(intent);

        }
    }
}
