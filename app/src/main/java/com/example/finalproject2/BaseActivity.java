package com.example.finalproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.app.Fragment;

import com.google.android.material.navigation.NavigationView;

/**
 * Base activity.
 */
public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the view.
        setContentView(R.layout.activity_main);

        // Get the toolbar object.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the drawer object.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Get the navigation view object.
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_layout);

        // Get the menu object.
        Menu menu = navigationView.getMenu();

        // Get the favourite icon object.
        MenuItem heart = menu.findItem(R.id.favourites);
        heart.setVisible(false);

        // Set a navigation item selected event listener.
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * On create options menu.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    /**
     * On options item selected.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            // If the favourites option is selected.
            case R.id.favourites:
                Toast.makeText(getApplicationContext(), "You clicked on favourites", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    /**
     * On navigation item selected.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Get the selected item.
        int id = item.getItemId();

        // If the home item is selected.
        if (id == R.id.home) {

            Intent nextActivity = new Intent(BaseActivity.this, MainActivity.class);
            startActivity(nextActivity);

        // If the favourite item is selected.
        } else if (id == R.id.favourites) {

            // Load the favourites.
            NavigationFragment navigationFragment = new NavigationFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content,navigationFragment)
                    .commit();

        } else if (id == R.id.exit) {

            // Exit the application.
            finishAffinity();
        }


        // Get the drawer object.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}