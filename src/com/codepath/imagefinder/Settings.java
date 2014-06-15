package com.codepath.imagefinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Settings extends Activity {

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
	}

	@Override
	public void onBackPressed() {
		Intent data = new Intent();
		data.putExtra(INTENT_OPTIONS, options);
		setResult(RESULT_OK, data);
		finish();
	}
}
