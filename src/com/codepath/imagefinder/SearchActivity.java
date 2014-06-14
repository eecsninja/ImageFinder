package com.codepath.imagefinder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

public class SearchActivity extends Activity {
	// Handles to each view.
	EditText query_field;
	Button search_button;
	GridView results_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		// Fill in view handles.
		setupViews();
	}

	private void setupViews() {
		query_field = (EditText) findViewById(R.id.etQuery);
		search_button = (Button) findViewById(R.id.btnSearch);
		results_view = (GridView) findViewById(R.id.gvResults);
	}
}
