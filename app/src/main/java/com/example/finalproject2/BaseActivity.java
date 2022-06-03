package com.example.finalproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.app.Fragment;

import com.google.android.material.navigation.NavigationView;


public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_layout);
        Menu menu = navigationView.getMenu();
        MenuItem lipstick = menu.findItem(R.id.item1);
        MenuItem coffee = menu.findItem(R.id.item2);
        lipstick.setVisible(false);
        coffee.setVisible(false);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        /*MenuItem searchItem = menu.findItem(R.id.);
        SearchView sView = (SearchView) searchItem.getActionView();
        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(getApplicationContext(), "You clicked on item 1", Toast.LENGTH_SHORT).show();
                break;

            case R.id.item2:
                Toast.makeText(getApplicationContext(), "You clicked on item 2", Toast.LENGTH_SHORT).show();
                break;


        }
        return true; // get rid of this when you complete function, just placed to build properly
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;

        if (id == R.id.home) {

            Intent nextActivity = new Intent(BaseActivity.this, MainActivity.class);
            startActivity(nextActivity);

        } else if (id == R.id.dadJoke) {



            NavigationFragment navigationFragment = new NavigationFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content,navigationFragment)
                    .commit();

        } else if (id == R.id.exit) {
            finishAffinity();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);



        return true;
    }
}