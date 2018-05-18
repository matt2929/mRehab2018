package com.example.matt2929.strokeappdec2017.Workouts;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.EndRepTrigger;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.Utilities.SFXPlayer;
import com.example.matt2929.strokeappdec2017.Utilities.ZeroCrossCalculation;

/**
 * Created by matt2929 on 12/22/17.
 */

public class WO_Walk extends SensorWorkoutAbstract {
	ZeroCrossCalculation zeroCrossCalculation;
	float threshold = 3;
	Boolean inCooldown = false;
	Boolean startData = false;
	Long startRep = 0L;
	int repCount = 0;
	Long walkLength = 10000L;
	Long cooldownLength = 10000L;
	Long cooldownStart = 0L;

	public WO_Walk(String Name, Integer reps, SpeechTrigger speechTrigger, EndRepTrigger endRepTrigger, SFXPlayer sfxPlayer, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
		super.SensorWorkout(Name, reps, speechTrigger, endRepTrigger, sfxPlayer, outputWorkoutData, outputWorkoutStrings);
		zeroCrossCalculation = new ZeroCrossCalculation();
		sfxPlayer.loadSFX(R.raw.pour_water);
		sfxPlayer.loopSFX();
	}


	@Override
	public void SensorDataIn(float[] data) {
		super.SensorDataIn(data);
		if (WorkoutInProgress) {
			if (!inCooldown) {
				if (!startData) {
					startRep = System.currentTimeMillis();
					startData = true;
				}
				zeroCrossCalculation.dataIn(data);
				if (data[1] > threshold && data[2] > threshold) {
					if (!sfxPlayer.isPlaying()) {
						sfxPlayer.playSFX();
					}
				} else {
					if (sfxPlayer.isPlaying()) {
						sfxPlayer.pauseSFX();
					}
				}
				if (System.currentTimeMillis() - startRep > walkLength) {
					repCount++;
					endRepTrigger.endRep();
					zeroCrossCalculation.endRep();
					if (repCount == reps) {
						workoutComplete = true;
					} else {
						speechTrigger.speak("Walk complete wait to start next one in 5 seconds");

					}
					cooldownStart = System.currentTimeMillis();
					inCooldown = true;
				}
			} else {
				if (System.currentTimeMillis() - cooldownStart > cooldownLength) {
					inCooldown = false;
					startData = false;
					speechTrigger.speak("Start walking");

				}
			}
		}
	}

	@Override
	public WorkoutScore getScore() {
		workoutScore = new WorkoutScore("Accuracy", zeroCrossCalculation.calculateZeroCross());
		return workoutScore;
	}
}
