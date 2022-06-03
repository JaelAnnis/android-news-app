package com.example.finalproject2;


import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.os.Bundle;

public class EmptyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Bundle data = getIntent().getExtras();

        if (data != null) {

            String title = data.getString("title");
            String section = data.getString("section");
            String url = data.getString("url");
        }

        DetailsFragment detailsFragment = new DetailsFragment();

        detailsFragment.setArguments(data);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, detailsFragment).commit();
    }
}