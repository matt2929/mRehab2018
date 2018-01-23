package com.example.matt2929.strokeappdec2017.Workouts;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.EndRepTrigger;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.Utilities.AverageValue;
import com.example.matt2929.strokeappdec2017.Utilities.SFXPlayer;

/**
 * Created by matt2929 on 1/4/18.
 */

public abstract class WorkoutAbstract {
	boolean workoutComplete = false;
	Long lastActivity = System.currentTimeMillis();
	WorkoutScore workoutScore;
	float[] outputData;
	String[] outputStrings;
	boolean WorkoutInProgress = false;
	SpeechTrigger speechTrigger;
	OutputWorkoutData outputWorkoutData;
	OutputWorkoutStrings outputWorkoutStrings;
	SFXPlayer sfxPlayer;
	float[] dataLast = null;
	AverageValue[] AverageValues;
	float[] AverageDataValue;
	Integer reps = 10;
	String Name = "";
	EndRepTrigger endRepTrigger;

	public void Workout(String Name, Integer reps, SpeechTrigger speechTrigger, EndRepTrigger endRepTrigger, SFXPlayer sfxPlayer, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
		this.Name = Name;
		this.reps = reps;
		this.speechTrigger = speechTrigger;
		this.outputWorkoutData = outputWorkoutData;
		this.outputWorkoutStrings = outputWorkoutStrings;
		this.sfxPlayer = sfxPlayer;
		this.endRepTrigger = endRepTrigger;
	}

	public void StartWorkout() {
		WorkoutInProgress = true;
		lastActivity = System.currentTimeMillis();
	}

	public boolean isWorkoutComplete() {
		return workoutComplete;
	}

	public WorkoutScore getScore() {
		return new WorkoutScore("Null", -1);
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