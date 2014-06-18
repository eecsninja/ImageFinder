package com.codepath.imagefinder;

import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ImageDisplay extends Activity {
	String url;		// URL of the image being displayed.

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);

		// Get intent parameters.
		ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");
		SmartImageView image_view = (SmartImageView) findViewById(R.id.ivResult);
		image_view.setImageUrl(result.getUrlFull());

		// Store image URL locally.
		url = result.getUrlFull();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
		return true;
	}

	// Share the URL.
	public void shareImage(MenuItem menu) {
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, url);
		startActivity(Intent.createChooser(sharingIntent,"Share using"));
	}
}
