package com.spartatechnology.popularmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.spartatechnology.popularmovies.R;
import com.spartatechnology.popularmovies.constant.PopularMoviesConstants;
import com.spartatechnology.popularmovies.fragment.MovieDetailActivityFragment;

/**
 * Created by kadu on 5/11/16.
 */
public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle bundle = new Bundle();
        bundle.putLong(PopularMoviesConstants.EXTRA_MOVIE_INFO_ID, getIntent().getLongExtra(PopularMoviesConstants.EXTRA_MOVIE_INFO_ID, 0l));
        bundle.putString(PopularMoviesConstants.EXTRA_MOVIE_INFO_IMAGE, getIntent().getStringExtra(PopularMoviesConstants.EXTRA_MOVIE_INFO_IMAGE));

        MovieDetailActivityFragment movieDetailsFragment = new MovieDetailActivityFragment();
        movieDetailsFragment.setArguments(bundle);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, movieDetailsFragment)
                    .commit();
        }

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
}
