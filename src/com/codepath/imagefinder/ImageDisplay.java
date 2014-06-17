package com.codepath.imagefinder;

import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ImageDisplay extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);

		// Get intent parameters.
		ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");
		SmartImageView image_view = (SmartImageView) findViewById(R.id.ivResult);
		image_view.setImageUrl(result.getUrlFull());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
		return true;
	}
}
