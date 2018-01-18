package com.example.matt2929.strokeappdec2017.Utilities;

/**
 * Created by matt2929 on 1/18/18.
 */

public class WorkoutSelectData {

	private String workoutName = "";
	private int activityCount = 0;
	private Integer color;

	public WorkoutSelectData(String workoutName, int activityCount, Integer color) {
		this.workoutName = workoutName;
		this.activityCount = activityCount;
		this.color = color;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public String getWorkoutName() {
		return workoutName;
	}

	public void setWorkoutName(String workoutName) {
		this.workoutName = workoutName;
	}

	public int getActivityCount() {
		return activityCount;
	}

	public void setActivityCount(int activityCount) {
		this.activityCount = activityCount;
	}

}
