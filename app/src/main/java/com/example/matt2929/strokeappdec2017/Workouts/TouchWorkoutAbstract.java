package com.example.matt2929.strokeappdec2017.Workouts;

import android.view.View;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.EndRepTrigger;
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

	public void TouchWorkout(String Name, Integer reps, ArrayList<View> views, EndRepTrigger endRepTrigger, SpeechTrigger speechTrigger, SFXPlayer sfxPlayer, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
		super.Workout(Name, reps, speechTrigger, endRepTrigger, sfxPlayer, outputWorkoutData, outputWorkoutStrings);
		this.views = views;

	}

	public boolean TouchIn(float x, float y) {
		return false;
	}

	public void Update() {

	}
}
