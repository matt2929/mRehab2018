package com.example.matt2929.strokeappdec2017.Workouts;

import android.util.Log;
import android.view.View;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.EndRepTrigger;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.Utilities.SFXPlayer;
import com.example.matt2929.strokeappdec2017.Utilities.ZeroCrossCalculation;

import java.util.ArrayList;

/**
 * Created by matt2929 on 12/21/17.
 */

public class WO_Unlock extends TouchWorkoutAbstract {

	boolean left = false;
	ZeroCrossCalculation zeroCrossCalculation;
	int countReps = 0;
	float width = 1080;
	float height = 0;

	public WO_Unlock(String Name, Integer reps, ArrayList<View> views, EndRepTrigger endRepTrigger, SpeechTrigger speechTrigger, SFXPlayer sfxPlayer, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
		super.TouchWorkout(Name, reps, views, endRepTrigger, speechTrigger, sfxPlayer, outputWorkoutData, outputWorkoutStrings);
		zeroCrossCalculation = new ZeroCrossCalculation();
		sfxPlayer.loadSFX(R.raw.unlockclick);
	}

	@Override
	public boolean TouchIn(float x, float y) {
		if (WorkoutInProgress) {
			outputData(new float[]{x, y});
			double angle = Math.atan((y / x));
			zeroCrossCalculation.dataIn(new float[]{(float) angle});
			float margin = (200f);
			if (left) {
				if (x < margin) {
					completeTurn();
				}
			} else {
				if (x > (width - margin)) {
					completeTurn();
				}
			}
			Log.e("make it", left + " " + width);
		}
		return true;
	}

	public void completeTurn() {
		if (sfxPlayer.isPlaying()) {
			sfxPlayer.killAll();
			sfxPlayer.loadSFX(R.raw.unlockclick);
		}
		countReps++;
		speechTrigger.speak("" + countReps);
		zeroCrossCalculation.endRep();
		endRepTrigger.endRep();
		if (countReps == reps) {
			workoutComplete = true;
		} else {
			sfxPlayer.playSFX();

		}
		left = !left;
	}

	@Override
	public void Update() {
		super.Update();
	}

	@Override
	public void outputData(float[] f) {
		super.outputData(f);
	}

	@Override
	public WorkoutScore getScore() {
		return new WorkoutScore("Smoothness", zeroCrossCalculation.calculateZeroCross());
	}


}
