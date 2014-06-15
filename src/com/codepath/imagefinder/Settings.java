package com.codepath.imagefinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class Settings extends Activity {
	// View elements.
	Spinner size_select;
	Spinner color_select;
	Spinner type_select;

	// Stored local copy of settings.
	private SearchOptions options;

	// Key string for storing SearchOptions in an intent.
	public final static String INTENT_OPTIONS = "options";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		// This should be launched with some options. If not, create an instance.
		options = (SearchOptions) getIntent().getSerializableExtra(INTENT_OPTIONS);
		if (options == null) {
			options = new SearchOptions();
		}

		setupViews();
	}

	@Override
	public void onBackPressed() {
		Intent data = new Intent();
		data.putExtra(INTENT_OPTIONS, options);
		setResult(RESULT_OK, data);
		finish();
	}

	// Creates local handles to various view elements.
	private void setupViews() {
		size_select = (Spinner) findViewById(R.id.spnSize);
		color_select = (Spinner) findViewById(R.id.spnColor);
		type_select = (Spinner) findViewById(R.id.spnType);

		// Initialize these to the current settings.
		size_select.setSelection(options.getSize());
		color_select.setSelection(options.getColor());
		type_select.setSelection(options.getType());

		// Register a selection change listener.
		OnItemSelectedListener listener = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// Set the correct setting, based on what the selector was.
				switch (parent.getId()) {
				case R.id.spnSize:
					options.setSize(position);
					break;
				case R.id.spnColor:
					options.setColor(position);
					break;
				case R.id.spnType:
					options.setType(position);
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// Does this even get called?
			}
		};
		size_select.setOnItemSelectedListener(listener);
		color_select.setOnItemSelectedListener(listener);
		type_select.setOnItemSelectedListener(listener);
	}
}
