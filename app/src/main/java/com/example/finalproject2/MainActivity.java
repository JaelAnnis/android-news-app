package com.example.finalproject2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends BaseActivity {
    ArrayList<ArticleTitles> listItems = new ArrayList<ArticleTitles>();
    JSONArray articles;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        SWChar req = new SWChar();

        req.execute("https://content.guardianapis.com/search?api-key=1fb36b70-1588-4259-b703-2570ea1fac6a&q=Tesla");

        ListView myList = (ListView) findViewById(R.id.ArticleList);

        myList.setOnItemLongClickListener((p, b, pos, id) -> {

            Bundle data = new Bundle();
            data.putString("title", listItems.get(pos).title);
            data.putString("section", listItems.get(pos).section);
            data.putString("url", listItems.get(pos).url);

            Intent nextActivity = new Intent(MainActivity.this, EmptyActivity.class);
            nextActivity.putExtras(data);
            startActivity(nextActivity);

            return true;
        });
    }

    private class SWChar extends AsyncTask<String, Integer, JSONArray> {

        @Override
        public JSONArray doInBackground(String... args) {

            URL url = null;

            try {

                url = new URL(args[0]);

                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

                InputStream response = urlConnection.getInputStream();
                System.out.println(response.toString());

                BufferedReader reader = new BufferedReader(new InputStreamReader(response));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                JSONObject results = new JSONObject(result);

                articles = new JSONObject(result).getJSONObject("response").getJSONArray("results");

            } catch (Exception e) {

                e.printStackTrace();
                System.out.println("ERROR");
            }

            //publishProgress(25);
            //publishProgress(50);
            publishProgress(100);

            return articles;
        }

        public void onProgressUpdate(Integer... args) {

            //imageView.setImageBitmap(bmp);
        }

        public void onPostExecute(JSONArray fromDoInBackground) {

            System.out.println(articles.toString());

            try {

                for (int i = 0; i < articles.length(); i++) {

                    String title = articles.getJSONObject(i).getString("webTitle");
                    String section = articles.getJSONObject(i).getString("sectionName");
                    String url = articles.getJSONObject(i).getString("webUrl");

                    listItems.add(new ArticleTitles(title, section, url));
                }

                ListView myList = (ListView) findViewById(R.id.ArticleList);
                MyListAdapter adapter = new MyListAdapter();

                myList.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            } catch (Exception e) {

                e.printStackTrace();
                System.out.println("ERROR");
            }
        }
    }

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

            ((TextView) newView.findViewById(R.id.textView)).setText(item.title);

            return newView;
        }
    }
}


