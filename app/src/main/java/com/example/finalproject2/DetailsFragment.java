package com.example.finalproject2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

/**
 * Details fragment.
 */
public class DetailsFragment extends Fragment implements View.OnClickListener {

    /**
     * On create.
     *
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    /**
     * On create view.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Get the passed bundle.
        Bundle data = getArguments();

        // Get the article information.
        String title = data.getString("title");
        String section = data.getString("section");
        String url = data.getString("url");

        // Initialize the result view.
        View result = inflater.inflate(R.layout.activity_details_fragment, container, false);

        // Get the article information objects.
        TextView textview1 = (TextView)result.findViewById(R.id.TitleFill);
        TextView textview2 = (TextView)result.findViewById(R.id.SectionFill);
        TextView textview3 = (TextView)result.findViewById(R.id.URLFill);

        // Set the article information text.
        textview1.setText(title);
        textview2.setText(section);
        textview3.setText(url);

        // Get the article url object.
        TextView urlTextview = (TextView) result.findViewById(R.id.URLFill);

        // Set a click event listener on article url.
        urlTextview.setOnClickListener(this);

        // Get the add to favourites button object.
        Button addFavouritesButton = (Button) result.findViewById(R.id.addFavouritesButton);

        // If the add to favourties button is clicked.
        addFavouritesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                // Initialize the database connection.
                Opener dbOpener = new Opener(getContext());
                SQLiteDatabase db = dbOpener.getWritableDatabase();

                // Insert the article into the database.
                db.execSQL("INSERT INTO " + dbOpener.TABLE_NAME + " (" + dbOpener.TITLE + ",  " + dbOpener.SECTION + ", " + dbOpener.URL + ") VALUES (\"" + title + "\", \"" + section + "\", \"" + url + "\");");

                // Output a snack bar confirming the article was saved.
                Snackbar snackbar = Snackbar.make(result, "This article was saved to your favourites!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        return result;
    }

    /**
     * On click.
     *
     * This loads the article url when it is clicked.
     *
     * @param view
     */
    @Override
    public void onClick(View view) {

        // Get the passed bundle.
        Bundle data = getArguments();

        // Get the article url.
        String url = data.getString("url");

        // Load the article in the browser.
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}