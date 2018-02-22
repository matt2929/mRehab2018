package com.example.matt2929.strokeappdec2017.Workouts;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.EndRepTrigger;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.Utilities.SFXPlayer;
import com.example.matt2929.strokeappdec2017.Utilities.ZeroCrossCalculation;

/**
 * Created by matt2929 on 12/21/17.
 */

public class WO_Pour extends SensorWorkoutAbstract {
	int repCount;
	float filledPercentage = 100f;
	float pouringThresehold = 15f;
	float removalRate = .1f;
	boolean tooQuicklyFullExplainGiven = false;
	boolean inCooldown = false;
	long coolDownDuration = 5000;
	float emptyThreshold = 15f;
	long startOfCooldown = 0l;
	ZeroCrossCalculation zeroCrossCalculation;

	public WO_Pour(String Name, Integer reps, SpeechTrigger speechTrigger, EndRepTrigger endRepTrigger, SFXPlayer SFX, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
		super.Workout(Name, reps, speechTrigger, endRepTrigger, SFX, outputWorkoutData, outputWorkoutStrings);
		sfxPlayer.loadSFX(R.raw.pour_water);
		sfxPlayer.loopSFX();
		zeroCrossCalculation = new ZeroCrossCalculation();
	}

	@Override
	public void SensorDataIn(float[] data) {
		super.SensorDataIn(data);
		if (WorkoutInProgress && !inCooldown) {
			float GravY, GravX;
			GravY = data[1];
			GravX = data[0];
			float Angle = ((GravY + (9.81f)) / (9.81f * 2));
			if (Angle > 1) {
				Angle = 1;
			} else if (Angle < 0) {
				Angle = 0;
			}
			float AngleStandardized = Angle * 100;
			Angle = 1 - Angle;
			if (GravX < 0) {
				Angle = -1 * Angle;
			}
			Angle = (180 * Angle);
			if (Math.abs(AngleStandardized - filledPercentage) <= pouringThresehold) {
				if (!sfxPlayer.isPlaying()) {
					sfxPlayer.playSFX();
				}
				filledPercentage -= removalRate;
				if (filledPercentage < emptyThreshold) {
					repCount++;
					zeroCrossCalculation.endRep();
					endRepTrigger.endRep();
					if (repCount == reps) {
						workoutComplete = true;
					} else {
						speechTrigger.speak("Rep " + repCount + " complete, return the cup to the upright position");
						inCooldown = true;
						startOfCooldown = System.currentTimeMillis();
						sfxPlayer.pauseSFX();
					}
				}

			} else {
				if (sfxPlayer.isPlaying()) {
					if (AngleStandardized - filledPercentage < pouringThresehold) {
						if (!tooQuicklyFullExplainGiven) {
							speechTrigger.speak("Your pouring too quickly, please tilt the mug up until water comes out again.");
							tooQuicklyFullExplainGiven = true;
						} else {
							speechTrigger.speak("Your pouring too quickly");
						}

					}
					sfxPlayer.pauseSFX();
				}
			}
			outputData(new float[]{Angle, ((filledPercentage - 15) / 100f)});
			zeroCrossCalculation.dataIn(new float[]{Angle});
		} else if (inCooldown) {
			if (Math.abs(System.currentTimeMillis() - startOfCooldown) >= coolDownDuration) {
				inCooldown = false;
				filledPercentage = 100f;
				speechTrigger.speak("Begin pouring");
			}
		}
	}

	@Override
	public void StartWorkout() {
		super.StartWorkout();
	}

	@Override
	public boolean isWorkoutComplete() {
		return super.isWorkoutComplete();
	}

	@Override
	public WorkoutScore getScore() {
		workoutScore = new WorkoutScore("Jerk", zeroCrossCalculation.calculateZeroCross());
		return workoutScore;
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
