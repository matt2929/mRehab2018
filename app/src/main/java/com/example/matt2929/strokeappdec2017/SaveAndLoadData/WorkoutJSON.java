package com.example.matt2929.strokeappdec2017.SaveAndLoadData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by matt2929 on 1/3/18.
 */

public class WorkoutJSON {
	JSONObject object = new JSONObject();
	String WorkoutName = "";
	Integer Reps = -1;
	float Duration = -1l;
	float Accuracy = -1l;
	String Hand = "Not Set", UserName = "Not Set";
	Calendar calendar;

	public WorkoutJSON(String UserName, String WorkoutName, Integer Reps, float Duration, float Accuracy, String Hand) {
		this.UserName = UserName;
		this.WorkoutName = WorkoutName;
		this.Reps = Reps;
		this.Duration = Duration;
		this.Accuracy = Accuracy;
		this.calendar = Calendar.getInstance();
		this.Hand = Hand;
		try {
			object.put("Name", WorkoutName);
			object.put("UserName", UserName);
			object.put("Reps", Reps);
			object.put("Duration", Duration);
			object.put("Accuracy", Accuracy);
			object.put("Hand", Hand);
			object.put("Year", calendar.get(Calendar.YEAR));
			object.put("Month", calendar.get(Calendar.MONTH));
			object.put("DayOfMonth", calendar.get(Calendar.DAY_OF_MONTH));
			object.put("HourOfDay", calendar.get(Calendar.HOUR));
			object.put("Minute", calendar.get(Calendar.MINUTE));
			object.put("Second", calendar.get(Calendar.SECOND));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public WorkoutJSON(String JSON) {
		try {
			object = new JSONObject(JSON);
			Hand = object.getString("Hand");
			UserName = object.getString("UserName");
			WorkoutName = object.getString("Name");
			Reps = object.getInt("Reps");
			Duration = object.getLong("Duration");
			Accuracy = (float) object.getDouble("Accuracy");
			int Year = object.getInt("Year");
			int Month = object.getInt("Month");
			int DOM = object.getInt("DayOfMonth");
			int HOD = object.getInt("HourOfDay");
			int Minute = object.getInt("Minute");
			int Second = object.getInt("Second");
			calendar = Calendar.getInstance();
			calendar.set(Year, Month, DOM, HOD, Minute, Second);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getJSONString() {
		return object.toString();
	}

	public Integer getReps() {
		return Reps;
	}

	public float getAccuracy() {
		return Accuracy;
	}

	public float getDuration() {
		return Duration;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public String getWorkoutName() {
		return WorkoutName;
	}

	public String getHand() {
		return Hand;
	}
}

