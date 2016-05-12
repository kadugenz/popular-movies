package com.spartatechnology.popularmovies.activity;

import android.app.Activity;
import android.os.Bundle;

import com.spartatechnology.popularmovies.fragment.SettingsFragment;

/**
 * Created by kadu on 5/10/16.
 */
public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
