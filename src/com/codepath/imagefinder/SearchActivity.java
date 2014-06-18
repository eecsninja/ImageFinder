package com.codepath.imagefinder;

import java.util.ArrayList;

import com.loopj.android.http.AsyncHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
			"https://ajax.googleapis.com/ajax/services/search/images?rsz=8&v=1.0&";

	// ID for launching settings activity.
	private final int SETTINGS_REQUEST_CODE = 76239;

	// Results from an image search.
	ArrayList<ImageResult> image_results = new ArrayList<ImageResult>();

	// Converts ImageResults to actual views.
	ImageResultArrayAdapter image_adapter;

	// Current search options.
	SearchOptions options = new SearchOptions();

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
		results_view.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Triggered only when new data needs to be appended to the list
				// Add whatever code is needed to append new items to your AdapterView
				customLoadMoreDataFromApi(totalItemsCount);
			}
		});
	}

	protected void customLoadMoreDataFromApi(int start_offset) {
		Log.d("DEBUG", "customLoadMoreDataFromApi(" + start_offset + ")");

		AsyncHttpClient client = new AsyncHttpClient();
		String query = query_field.getText().toString();
		String url = getQueryURLString(Uri.encode(query), start_offset);
		client.get(url, new ResponseHandler(start_offset, image_adapter));
	}

	public void onSearch(View v) {
		// Do not attempt search if not connected to Internet.
		if (!hasInternetConnectivity()) {
			Toast.makeText(getApplicationContext(),
					"Could not connect to Internet!",
					Toast.LENGTH_SHORT).show();
			return;
		}


		String query = query_field.getText().toString();
		// Do not search if query is empty.
		if (query.isEmpty()) {
			Toast.makeText(this, "Enter search query", Toast.LENGTH_SHORT).show();
			return;
		}

		AsyncHttpClient client = new AsyncHttpClient();
		String url = getQueryURLString(Uri.encode(query), 0);
		client.get(url, new ResponseHandler(0, image_adapter));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	public void onSettingsClicked(MenuItem mi) {
		// Launch new Settings activity using an intent.
		Intent intent = new Intent(getApplicationContext(), Settings.class);
		intent.putExtra(Settings.INTENT_OPTIONS, options);
		startActivityForResult(intent, SETTINGS_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SETTINGS_REQUEST_CODE && resultCode == RESULT_OK) {
			// Get updated search options.
			options = (SearchOptions) data.getSerializableExtra(Settings.INTENT_OPTIONS);
		}
	}

	private void setupViews() {
		query_field = (EditText) findViewById(R.id.etQuery);
		search_button = (Button) findViewById(R.id.btnSearch);
		results_view = (GridView) findViewById(R.id.gvResults);
	}

	private String getQueryURLString(String query, int start) {
		return IMAGE_SEARCH_URL_BASE + options.toString() + "start=" + start + "&q=" + query;
	}

	// Checks for presence of Internet connection.
	public boolean hasInternetConnectivity() {
		ConnectivityManager connectivity =
				(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
		return (activeNetwork != null) && activeNetwork.isConnectedOrConnecting();
	}
}
