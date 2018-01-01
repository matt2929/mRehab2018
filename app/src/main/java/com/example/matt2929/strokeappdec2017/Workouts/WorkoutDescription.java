package com.example.matt2929.strokeappdec2017.Workouts;

/**
 * Created by matt2929 on 12/22/17.
 */

public class WorkoutDescription {
    private String Name = "";
    private String WorkoutType = "";
    private String SensorType = "";

    public WorkoutDescription(String Name, String WorkoutType, String SensorType) {
        this.Name = Name;
        this.WorkoutType = WorkoutType;
        this.SensorType = SensorType;
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
}
