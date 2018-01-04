package com.example.matt2929.strokeappdec2017.Workouts;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.Utilities.AverageValue;
import com.example.matt2929.strokeappdec2017.Utilities.SFXPlayer;

/**
 * Created by matt2929 on 12/19/17.
 */

public abstract class SensorWorkoutAbstract {

    SpeechTrigger speechTrigger;
    OutputWorkoutData outputWorkoutData;
    OutputWorkoutStrings outputWorkoutStrings;
    boolean workoutComplete = false;
    WorkoutScore workoutScore;
    float[] outputData;
    String[] outputStrings;
    SFXPlayer sfxPlayer;
    float[] dataLast = null;
    boolean WorkoutInProgress = false;
    Long lastActivity = System.currentTimeMillis();
    AverageValue[] AverageValues;
    float[] AverageDataValue;
    Integer reps = 10;
    String Name = "";

    public void Workout(String Name, Integer reps, SpeechTrigger speechTrigger, SFXPlayer sfxPlayer, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
        this.Name = Name;
        this.reps = reps;
        this.speechTrigger = speechTrigger;
        this.outputWorkoutData = outputWorkoutData;
        this.outputWorkoutStrings = outputWorkoutStrings;
        this.sfxPlayer = sfxPlayer;
    }

    public void SensorDataIn(float[] data) {
        if (dataLast == null) {
            dataLast = data;
            AverageValues = new AverageValue[data.length];
            AverageDataValue = new float[data.length];
            for (int i = 0; i < data.length; i++) {
                AverageValues[i] = new AverageValue(25);
            }
        }
        for (int i = 0; i < data.length; i++) {
            AverageDataValue[i] = AverageValues[i].addData(data[i]);
        }

    }

    public void StartWorkout() {
        WorkoutInProgress = true;
        lastActivity = System.currentTimeMillis();
    }

    public boolean isWorkoutComplete() {
        return workoutComplete;
    }

    public WorkoutScore getScore() {
        return workoutScore;
    }

    public void outputData(float[] f) {
        outputWorkoutData.getData(f);
    }

    public void outputStrings(String[] s) {
        outputWorkoutStrings.getStrings(s);
    }

    public String getName() {
        return Name;
    }

	public Integer getReps() {
		return reps;
	}
}
