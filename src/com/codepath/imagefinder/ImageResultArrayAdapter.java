package com.codepath.imagefinder;

import java.util.List;

import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ImageResultArrayAdapter extends ArrayAdapter<ImageResult> {

	public ImageResultArrayAdapter(Context context, List<ImageResult> images) {
		super(context, R.layout.item_image_result, images);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SmartImageView image_view;
		if (convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			image_view = (SmartImageView) inflator.inflate(R.layout.item_image_result, parent, false);
		} else {
			// Clear existing image if reusing an existing view.
			image_view = (SmartImageView) convertView;
			image_view.setImageResource(android.R.color.transparent);
		}
		ImageResult image_info = getItem(position);
		image_view.setImageUrl(image_info.getUrlThumb());

		return image_view;
	}
}
