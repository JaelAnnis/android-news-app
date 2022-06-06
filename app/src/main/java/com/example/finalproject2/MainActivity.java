package com.example.finalproject2;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;

/**
 * Main activity.
 */
public class MainActivity extends BaseActivity {

    // Initialize the articles list.
    ArrayList<ArticleTitles> listItems = new ArrayList<ArticleTitles>();

    // Initialize the articles response.
    JSONArray articles;

    // Initialize the progress bar.
    ProgressBar progressBar;

    // Initialize the SharedPreferences.
    SharedPreferences sharedPreferences;

    // Initialize the article count.
    String articleCount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the shared preferences.
        sharedPreferences = getSharedPreferences("Preferences",MODE_PRIVATE);

        // Get the article count preference.
        articleCount = sharedPreferences.getString("articleCount", "10");

        // Set the view.
        setContentView(R.layout.activity_main);

        // Get the progress bar object.
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Initialize the request.
        SWChar req = new SWChar();

        // Execute the request.
        req.execute("https://content.guardianapis.com/search?api-key=1fb36b70-1588-4259-b703-2570ea1fac6a&q=Tesla");

        // Get the article list object.
        ListView myList = (ListView) findViewById(R.id.ArticleList);

        // If an article is clicked.
        myList.setOnItemClickListener((p, b, pos, id) -> {

            // If the first item was clicked.
            if (pos == 0) {

                // Output the help instructions.
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(getResources().getString(R.string.Instructions))

                        .setMessage(getResources().getString(R.string.instructionDetails))

                        .create().show();

            // If the second item was clicked.
            } else if (pos == 1) {

                // Output the preferences.
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(getResources().getString(R.string.articleCountPreferences))

                        .setMessage(getResources().getString(R.string.preferenceInstructions))

                        // If the 5 button is clicked.
                        .setPositiveButton("5", (click, arg) -> {

                            // Initialize a SharedPreference Editor.
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();

                            // Save the article count preference.
                            myEdit.putString("articleCount", "5");
                            myEdit.commit();
                        })

                        // If the 10 button is clicked.
                        .setNegativeButton("10", (click, arg) -> {

                            // Initialize a SharedPreference Editor.
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();

                            // Save the article count preference.
                            myEdit.putString("articleCount", "10");
                            myEdit.commit();
                        })

                        .create().show();

            // If the third item was clicked.
            } else if (pos == 2) {

                // Load the favourites view.
                Intent nextActivity = new Intent(MainActivity.this, Favourites.class);
                startActivity(nextActivity);

            // If an article was clicked.
            } else {

                // Initialize the bundle.
                Bundle data = new Bundle();
                data.putString("title", listItems.get(pos).title);
                data.putString("section", listItems.get(pos).section);
                data.putString("url", listItems.get(pos).url);

                // Load the article information view.
                Intent nextActivity = new Intent(MainActivity.this, EmptyActivity.class);
                nextActivity.putExtras(data);
                startActivity(nextActivity);
            }
        });

    }

    /**
     * SWChar.
     *
     * This is responsible for making the API request.
     */
    private class SWChar extends AsyncTask<String, Integer, JSONArray> {

        /**
         * Do in background.
         *
         * @param args
         * @return
         */
        @Override
        public JSONArray doInBackground(String... args) {

            // Initialize the url.
            URL url = null;

            try {

                // Set the article url.
                url = new URL(args[0]);

                // Initialize the http connection.
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

                // Get the API response.
                InputStream response = urlConnection.getInputStream();

                // Output the response to console.
                System.out.println(response.toString());

                // Format the response.
                BufferedReader reader = new BufferedReader(new InputStreamReader(response));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                // Convert the response to a JSON object.
                JSONObject results = new JSONObject(result);
                articles = new JSONObject(result).getJSONObject("response").getJSONArray("results");

            } catch (Exception e) {

                // Output the errors.
                e.printStackTrace();
            }

            publishProgress(100);

            return articles;
        }

        /**
         * On progress update.
         *
         * @param args
         */
        public void onProgressUpdate(Integer... args) {

            // Start the progress bar.
            progressBar.setVisibility(View.VISIBLE);
        }

        /**
         * On post execute.
         *
         * READ NOTE FOR WORKAROUND BELOW:
         *
         * This is the section that will be the workaround since the toolbar/menu
         * and navigation drawer do not work. You can access the Help, Preferences, and Favourites
         * using the top list items designated for the views instead.
         *
         * @param fromDoInBackground
         */
        public void onPostExecute(JSONArray fromDoInBackground) {

            // Add the help button.
            listItems.add(new ArticleTitles(-1L,getResources().getString(R.string.Help), "", ""));

            // Add the preferences button.
            listItems.add(new ArticleTitles(-1L,getResources().getString(R.string.Preferences), "", ""));

            // Add the favourite button.
            listItems.add(new ArticleTitles(-1L,getResources().getString(R.string.Favourites), "", ""));

            try {

                // Iterate through the articles.
                for (int i = 0; i < Integer.parseInt(articleCount); i++) {

                    // Get the article information.
                    String title = articles.getJSONObject(i).getString("webTitle");
                    String section = articles.getJSONObject(i).getString("sectionName");
                    String url = articles.getJSONObject(i).getString("webUrl");

                    // Save the article to the list.
                    listItems.add(new ArticleTitles(-1L, title, section, url));
                }

                // Get the article list object.
                ListView myList = (ListView) findViewById(R.id.ArticleList);

                // Initialize the article list adaptor.
                MyListAdapter adapter = new MyListAdapter();

                // Refresh the adaptor.
                myList.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            } catch (Exception e) {

                // Output the errors.
                e.printStackTrace();
            }

            // Stop the progress bar.
            progressBar.setVisibility(View.GONE);;
        }
    }

    /**
     * My list adaptor.
     */
    private class MyListAdapter extends BaseAdapter {

        /**
         * Get count.
         *
         * @return
         */
        @Override
        public int getCount() {

            return listItems.size();
        }

        /**
         * Get item.
         *
         * @param position
         * @return
         */
        @Override
        public Object getItem(int position) {

            return listItems.get(position) ;
        }

        /**
         * Get item id.
         *
         * @param position
         * @return
         */
        @Override
        public long getItemId(int position) {

            return position;
        }

        /**
         * Get view.
         *
         * @param position
         * @param old
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View old, ViewGroup parent) {

            View newView= getLayoutInflater().inflate(R.layout.list_item, parent, false);

            ArticleTitles item = (ArticleTitles) getItem(position);

            // Set the article title.
            ((TextView) newView.findViewById(R.id.textView)).setText(item.title);

            return newView;
        }
    }
}


