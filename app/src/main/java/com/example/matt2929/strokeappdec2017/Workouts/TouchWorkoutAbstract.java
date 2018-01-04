package com.example.matt2929.strokeappdec2017.Workouts;

import android.view.View;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.Utilities.SFXPlayer;

import java.util.ArrayList;

/**
 * Created by matt2929 on 1/4/18.
 */

public class TouchWorkoutAbstract extends WorkoutAbstract {
	ArrayList<View> views = new ArrayList<>();

	public void SensorWorkout(String Name, Integer reps, ArrayList<View> views, SpeechTrigger speechTrigger, SFXPlayer sfxPlayer, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
		super.Workout(Name, reps, speechTrigger, sfxPlayer, outputWorkoutData, outputWorkoutStrings);
		this.views = views;
	}

	public void TouchIn(float x, float y) {

	}
}
