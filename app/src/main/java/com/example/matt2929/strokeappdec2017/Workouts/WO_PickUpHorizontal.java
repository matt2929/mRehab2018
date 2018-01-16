package com.example.matt2929.strokeappdec2017.Workouts;

import android.util.Log;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.Utilities.SFXPlayer;

/**
 * Created by matt2929 on 12/20/17.
 */

public class WO_PickUpHorizontal extends SensorWorkoutAbstract {
	boolean moving = false;
	double thresehold = .25;
	int pickUpCount = 0;
	int belowThresholdCount = 0;
	int belowThresholdMax = 100;

	public WO_PickUpHorizontal(String Name, Integer reps, SpeechTrigger speechTrigger, SFXPlayer sfxPlayer, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
		super.SensorWorkout(Name, reps, speechTrigger, sfxPlayer, outputWorkoutData, outputWorkoutStrings);
	}

	@Override
	public void SensorDataIn(float[] data) {
		super.SensorDataIn(data);
		int sensorChoice = 1;
		if (Name.contains("Bowl")) {
			sensorChoice = 0;
		}
		Log.e("Accy", AverageDataValue[1] + " m/s^2 Moving:" + moving + " threshholdCount: " + belowThresholdCount);
		if (WorkoutInProgress) {
			if (AverageDataValue[sensorChoice] > thresehold) {
				moving = true;
				belowThresholdCount = 0;
			}
			if (AverageDataValue[sensorChoice] < thresehold) {
				belowThresholdCount++;
				if (belowThresholdCount > belowThresholdMax) {
					if (moving == true) {
						pickUpCount++;
						speechTrigger.speak("" + pickUpCount);
						if (pickUpCount == reps / 2) {
							speechTrigger.speak(".Half Way");

						}
						if (pickUpCount == reps) {
							workoutComplete = true;
						}
					}
					moving = false;
				}
			}
		}

	}

	@Override
	public boolean isWorkoutComplete() {
		return super.isWorkoutComplete();
	}

	@Override
	public WorkoutScore getScore() {
		workoutScore = new WorkoutScore("Jerk", 10);
		return super.getScore();
	}

	@Override
	public void outputData(float[] f) {
		super.outputData(f);
	}

	@Override
	public void outputStrings(String[] s) {
		super.outputStrings(s);
	}
}

