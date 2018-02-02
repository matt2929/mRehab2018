package com.example.matt2929.strokeappdec2017.Workouts;

import android.util.Log;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.EndRepTrigger;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.Utilities.SFXPlayer;
import com.example.matt2929.strokeappdec2017.Utilities.ZeroCrossCalculation;

/**
 * Created by matt2929 on 12/21/17.
 */

public class WO_Twist extends SensorWorkoutAbstract {
	Long Time = System.currentTimeMillis();
	boolean down = false;
	float lastValue = 0;
	int count = 0;
	float threshold = -3.75f;
	ZeroCrossCalculation zeroCrossCalculation;

	public WO_Twist(String Name, Integer reps, SpeechTrigger speechTrigger, EndRepTrigger endRepTrigger, SFXPlayer sfxPlayer, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
		super.SensorWorkout(Name, reps, speechTrigger, endRepTrigger, sfxPlayer, outputWorkoutData, outputWorkoutStrings);
		Time = System.currentTimeMillis();
		zeroCrossCalculation = new ZeroCrossCalculation();
	}

	//TODO: clockwise right hand  CCW LEFT HAND

	@Override
	public void SensorDataIn(float[] data) {
		super.SensorDataIn(data);
		Log.e("Twist", AverageDataValue[1] + "Y, " + down);
		if (WorkoutInProgress) {
			zeroCrossCalculation.dataIn(data);
			if (AverageDataValue[1] < threshold && lastValue >= threshold) {
				count++;
				zeroCrossCalculation.endRep();
				outputWorkoutStrings.getStrings(new String[]{count + ""});
				endRepTrigger.endRep();
				speechTrigger.speak("" + count);
				if (count == reps) {
					workoutComplete = true;
				}
			}
		}
		lastValue = AverageDataValue[1];
	}

	@Override
	public WorkoutScore getScore() {
		workoutScore = new WorkoutScore("Accuracy", zeroCrossCalculation.calculateZeroCross());
		return workoutScore;
	}
}
