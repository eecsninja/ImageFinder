package com.codepath.imagefinder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ResponseHandler extends JsonHttpResponseHandler {
	// Start index of first result.
	int start = 0;

	// Handle to an ImageResult ArrayAdapter.
	ImageResultArrayAdapter adapter;

	public ResponseHandler(int start, ImageResultArrayAdapter adapter) {
		this.start = start;
		this.adapter = adapter;
	}

	@Override
	public void onSuccess(JSONObject response) {
		JSONArray results = null;
		try {
			// Convert results to ImageResult.
			results = response.getJSONObject("responseData").getJSONArray("results");
			// Reset the list of results if this is a new query.
			if (start == 0) {
				adapter.clear();
			}
			adapter.addAll(ImageResult.fromJSONArray(results));
		} catch (JSONException e) {
			System.err.println(e.getMessage());
			System.err.println(response.toString());
		}
	}
}
