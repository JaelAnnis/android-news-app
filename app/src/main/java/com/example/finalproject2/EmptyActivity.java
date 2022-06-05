package com.example.finalproject2;

import android.os.Bundle;

/**
 * Empty activity.
 *
 * This loads the article information into fields.
 */
public class EmptyActivity extends BaseActivity {

    /**
     * On create.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the view.
        setContentView(R.layout.activity_empty);

        // Get the passed bundle.
        Bundle data = getIntent().getExtras();

        // Initialize the fragment.
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(data);

        // Load the fragment view.
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, detailsFragment).commit();
    }
}