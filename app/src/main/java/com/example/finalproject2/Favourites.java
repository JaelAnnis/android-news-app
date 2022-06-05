package com.example.finalproject2;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Favourites.
 *
 * This class is used to show the saved articles.
 */
public class Favourites extends BaseActivity {

    // Initialize the articles.
    ArrayList<ArticleTitles> listItems = new ArrayList<ArticleTitles>();

    /**
     * On create.
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Set the view.
        setContentView(R.layout.activity_favourites);

        // Get the favourites list view.
        ListView myList = (ListView) findViewById(R.id.favouritesView);

        // Initialize the database connection.
        Opener dbOpener = new Opener(this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();

        // Initialize the favourites list view adaptor.
        Favourites.MyListAdapter adapter = new Favourites.MyListAdapter();

        try {

            // Set the database table columns.
            String [] columns = {Opener.ITEM_ID, Opener.TITLE, Opener.SECTION, Opener.URL};

            // Get the database table results.
            Cursor results = db.query(false, Opener.TABLE_NAME, columns, null, null, null, null, null, null, null);

            // Log the database table for debugging purposes.
            printCursor(results);

            // Set the database table columns.
            int item_id_column_index = results.getColumnIndex(Opener.ITEM_ID);
            int item_title_column_index = results.getColumnIndex(Opener.TITLE);
            int item_section_column_index = results.getColumnIndex(Opener.SECTION);
            int item_url_column_index = results.getColumnIndex(Opener.URL);

            // Iterate through the database table results.
            for (results.moveToFirst(); !results.isAfterLast(); results.moveToNext()) {

                // Set the database table records.
                long id = results.getLong(item_id_column_index);
                String title = results.getString(item_title_column_index);
                String section = results.getString(item_section_column_index);
                String url = results.getString(item_url_column_index);

                // Add the record to the articles.
                listItems.add(new ArticleTitles(id, title, section, url));;
            }

            // Refresh the articles.
            myList.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } catch (Exception e) {

            // Output the errors.
            e.printStackTrace();
        }

        // If an article was clicked.
        myList.setOnItemClickListener((p, b, pos, id) -> {

            // Initialize a bundle.
            Bundle data = new Bundle();

            // Add the article information to the bundle.
            data.putString("title", listItems.get(pos).title);
            data.putString("section", listItems.get(pos).section);
            data.putString("url", listItems.get(pos).url);

            // Start the next activity and pass the bundle data.
            Intent nextActivity = new Intent(Favourites.this, EmptyActivity.class);
            nextActivity.putExtras(data);
            startActivity(nextActivity);
        });

        // If an article is long clicked.
        myList.setOnItemLongClickListener((p, b, pos, id) -> {

            // Output the delete confirmation.
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")

            // If the yes button is clicked.
            .setPositiveButton("Yes", (click, arg) -> {

                // Delete the record.
                db.delete(Opener.TABLE_NAME, "item_id=?", new String[] { Long.toString(listItems.get(pos).id) } );

                // Remove the list item.
                listItems.remove(pos);

                // Refresh the articles.
                adapter.notifyDataSetChanged();
            })

            // If the no button is clicked.
            .setNegativeButton("No", (click, arg) -> {})
            .create().show();

            return true;
        });
    }

    /**
     * Print cursor.
     *
     * This is used to output the database table contents.
     *
     * @param c
     */
    private void printCursor(Cursor c) {

        System.out.println("Database Version Number: " + Opener.VERSION_NUM);
        System.out.println("Column Count: " + c.getColumnCount());
        System.out.println("Column Names: " + Arrays.toString(c.getColumnNames()));
        System.out.println("Result Number: " + c.getCount());
        System.out.println("Result Rows: ");

        int item_id_column_index = c.getColumnIndex(Opener.ITEM_ID);
        int item_title_column_index = c.getColumnIndex(Opener.TITLE);
        int item_section_column_index = c.getColumnIndex(Opener.SECTION);
        int item_url_column_index = c.getColumnIndex(Opener.URL);

        while(c.moveToNext()) {

            int id = c.getInt(item_id_column_index);
            String title = c.getString(item_title_column_index);
            String section = c.getString(item_section_column_index);
            String url = c.getString(item_url_column_index);

            System.out.println(id + " | " + title + " | " + section + " | " + url);
        }
    }

    /**
     *  My list adaptor.
     *
     *  This is used to output the articles to a list view.
     */
    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return listItems.size();
        }

        @Override
        public Object getItem(int position) {

            return listItems.get(position) ;
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View old, ViewGroup parent) {

            View newView= getLayoutInflater().inflate(R.layout.list_item, parent, false);

            ArticleTitles item = (ArticleTitles) getItem(position);

            // Set the list item to the article title.
            ((TextView) newView.findViewById(R.id.textView)).setText(item.title);

            return newView;
        }
    }
}
