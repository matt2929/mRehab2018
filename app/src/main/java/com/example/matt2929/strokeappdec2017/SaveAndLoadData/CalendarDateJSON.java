package com.example.matt2929.strokeappdec2017.SaveAndLoadData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by matt2929 on 1/17/18.
 */

public class CalendarDateJSON {
	int month = -1, dayOfMonth = -1, year = -1;
	JSONObject jsonObject = new JSONObject();

	public CalendarDateJSON(int month, int dayOfMonth, int year) {
		this.month = month;
		this.dayOfMonth = dayOfMonth;
		this.year = year;
		try {
			jsonObject.put("year", year);
			jsonObject.put("dom", dayOfMonth);
			jsonObject.put("month", month);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public CalendarDateJSON(String string) {
		try {
			JSONObject jsonObject = new JSONObject(string);
			year = jsonObject.getInt("year");
			dayOfMonth = jsonObject.getInt("dom");
			month = jsonObject.getInt("month");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getJSONString() {
		return jsonObject.toString();
	}

}
