package com.example.finalproject2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFragment extends Fragment implements View.OnClickListener {

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle data = getArguments();

        String title = data.getString("title");
        String section = data.getString("section");
        String url = data.getString("url");

        View result = inflater.inflate(R.layout.activity_details_fragment, container, false);

        TextView textview1 = (TextView)result.findViewById(R.id.TitleFill);
        TextView textview2 = (TextView)result.findViewById(R.id.SectionFill);
        TextView textview3 = (TextView)result.findViewById(R.id.URLFill);

        textview1.setText(title);
        textview2.setText(section);
        textview3.setText(url);

        TextView urlTextview = (TextView) result.findViewById(R.id.URLFill);
        urlTextview.setOnClickListener(this);

        return result;
    }

    @Override
    public void onClick(View view) {

        Bundle data = getArguments();

        String url = data.getString("url");

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}