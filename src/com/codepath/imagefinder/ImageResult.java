package com.codepath.imagefinder;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult implements Serializable {
	private static final long serialVersionUID = -2835516952368765958L;
	private String url_full;	// URL of image.
	private String url_thumb;	// URL of thumbnail.

	public ImageResult(JSONObject json) {
		try {
			url_full = json.getString("url");
			url_thumb = json.getString("tbUrl");
		} catch (JSONException e) {
			System.err.println(e.getMessage());
			url_full = null;
			url_thumb = null;
		}
	}

	public String getUrlFull() {
		return url_full;
	}
	public String getUrlThumb() {
		return url_thumb;
	}

	public String toString() {
		return "{ " + url_full + ", " + url_thumb + " }";
	}

	public static ArrayList<? extends ImageResult> fromJSONArray(
			JSONArray array) {
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		for (int i = 0; i < array.length(); ++i) {
			try {
				results.add(new ImageResult(array.getJSONObject(i)));
			} catch (JSONException e) {
				System.err.println(e.getMessage());
			}
		}
		return results;
	}
}
