package com.codepath.imagefinder;

import java.io.Serializable;

// Class that contains search option fields.
public class SearchOptions implements Serializable {
	private static final long serialVersionUID = 2392044444095436721L;

	// These are actual search query value strings.
	private static final String[] SIZE_STRINGS = {
		"", "icon", "small|medium|large|xlarge", "xxlarge", "huge"
	};
	private static final String[] COLOR_STRINGS = {
		"", "black", "blue", "brown", "gray", "green", "orange",
		"pink", "purple", "red", "teal", "white", "yellow"
	};
	private static final String[] TYPE_STRINGS = {
		"", "face", "photo", "clipart", "lineart"
	};
	// Indexes of the currently selected choices in the above arrays.
	int size;
	int color;
	int type;

	// Converts the options into part of the query URL.
	public String toString() {
		String size_string = "";
		String color_string = "";
		String type_string = "";
		try {
			size_string = SIZE_STRINGS[size];
			color_string = COLOR_STRINGS[color];
			type_string = TYPE_STRINGS[type];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
		}
		// Add each field to query if it is set.
		String value = "";
		if (!size_string.isEmpty()) {
			value += "imgsz=" + size_string + "&";
		}
		if (!color_string.isEmpty()) {
			value += "imgcolor=" + color_string + "&";
		}
		if (!type_string.isEmpty()) {
			value += "imgtype=" + type_string + "&";
		}

		return value;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
