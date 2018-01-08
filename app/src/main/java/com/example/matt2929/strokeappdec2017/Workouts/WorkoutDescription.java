package com.example.matt2929.strokeappdec2017.Workouts;

/**
 * Created by matt2929 on 12/22/17.
 */

public class WorkoutDescription {
	private String Name = "";
	private String WorkoutType = "";
	private String SensorType = "";
	private Integer NormalReps = 10;

	public WorkoutDescription(String Name, String WorkoutType, String SensorType, Integer NormalReps) {
		this.Name = Name;
		this.WorkoutType = WorkoutType;
		this.SensorType = SensorType;
		this.NormalReps = NormalReps;
	}

	public String getName() {
		return Name;
	}

	public String getSensorType() {
		return SensorType;
	}

	public String getWorkoutType() {
		return WorkoutType;
	}

	public Integer getNormalReps() {
		return NormalReps;
	}
}
