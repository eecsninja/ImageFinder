package com.codepath.imagefinder;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class SearchActivity extends Activity {
	// Handles to each view.
	EditText query_field;
	Button search_button;
	GridView results_view;

	// Google image search JSON API.
	private final String IMAGE_SEARCH_URL_BASE =
			"https://ajax.googleapis.com/ajax/services/search/images?rsz=8&start=0&v=1.0&q=";

	// Results from an image search.
	ArrayList<ImageResult> image_results = new ArrayList<ImageResult>();

	// Converts ImageResults to actual views.
	ImageResultArrayAdapter image_adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		// Fill in view handles.
		setupViews();

		// Create image array adapter and attach to result view.
		image_adapter = new ImageResultArrayAdapter(this, image_results);
		results_view.setAdapter(image_adapter);
		results_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Launch new ImageDisplay activity using an intent.
				Intent intent = new Intent(getApplicationContext(), ImageDisplay.class);
				ImageResult image_result = image_results.get(position);
				intent.putExtra("result", image_result);
				startActivity(intent);
			}
		});
	}

	public void onSearch(View v) {
		String query = query_field.getText().toString();
		// TODO: Remove this when the image search is fully functional.
		Toast.makeText(this, query, Toast.LENGTH_LONG).show();

		AsyncHttpClient client = new AsyncHttpClient();
		String url = IMAGE_SEARCH_URL_BASE + Uri.encode(query);
		client.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray results = null;
				try {
					// Convert results to ImageResult.
					results = response.getJSONObject("responseData").getJSONArray("results");
					image_adapter.clear();
					image_adapter.addAll(ImageResult.fromJSONArray(results));
				} catch (JSONException e) {
					System.err.println(e.getMessage());
				}
				// Useful for console debugging.
				Log.d("DEBUG", results.toString());
			}
		});
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

	private void setupViews() {
		query_field = (EditText) findViewById(R.id.etQuery);
		search_button = (Button) findViewById(R.id.btnSearch);
		results_view = (GridView) findViewById(R.id.gvResults);
	}
}
