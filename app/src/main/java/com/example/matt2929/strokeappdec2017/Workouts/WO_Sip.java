package com.example.matt2929.strokeappdec2017.Workouts;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.EndRepTrigger;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.Utilities.SFXPlayer;
import com.example.matt2929.strokeappdec2017.Utilities.ZeroCrossCalculation;

/**
 * Created by matt2929 on 1/16/18.
 */

public class WO_Sip extends SensorWorkoutAbstract {

	private double thresholdPickup = 3.0;
	private boolean pickedUp = false;
	private double threseholdDrink = 2;
	private long timeToDrink = 4000, timeDrank = 0, checkPoint = 0;
	private int repCount = 0;
	private boolean inCoolDown = false;
	private long coolDownLength = 5000, cooldownStart = 0;
	private ZeroCrossCalculation zeroCrossCalculation;

	public WO_Sip(String Name, Integer reps, SpeechTrigger speechTrigger, EndRepTrigger endRepTrigger, SFXPlayer sfxPlayer, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
		super.SensorWorkout(Name, reps, speechTrigger, endRepTrigger, sfxPlayer, outputWorkoutData, outputWorkoutStrings);
		sfxPlayer.loadSFX(R.raw.pour_water);
		sfxPlayer.loopSFX();
		zeroCrossCalculation = new ZeroCrossCalculation();
	}


	@Override
	public void SensorDataIn(float[] data) {
		super.SensorDataIn(data);
		outputData(new float[]{0, ((float) (timeToDrink - timeDrank) / (float) timeToDrink)});

		if (WorkoutInProgress && !inCoolDown) {

			zeroCrossCalculation.dataIn(data);
			if (data[1] > thresholdPickup && !pickedUp) {
				pickedUp = true;
				checkPoint = System.currentTimeMillis();
			}
			if (pickedUp) {
				if (Math.abs(data[2]) > threseholdDrink || Math.abs(data[0]) > threseholdDrink) {
					timeDrank += (Math.abs(checkPoint - System.currentTimeMillis()));
					if (!sfxPlayer.isPlaying()) {
						sfxPlayer.playSFX();
					}
				} else {
					if (sfxPlayer.isPlaying()) {
						sfxPlayer.pauseSFX();
					}
				}
				checkPoint = System.currentTimeMillis();
			}
			if (timeDrank > timeToDrink) {
				repCount++;
				zeroCrossCalculation.endRep();
				endRepTrigger.endRep();
				inCoolDown = true;
				cooldownStart = System.currentTimeMillis();
				if (sfxPlayer.isPlaying()) {
					sfxPlayer.pauseSFX();
				}
				if (repCount == reps) {
					workoutComplete = true;
				} else {
					speechTrigger.speak("Sip complete, Place the mug back down on the table and wait for the next sip.");
				}
			}
		}
		if (inCoolDown) {
			if (Math.abs(cooldownStart - System.currentTimeMillis()) > coolDownLength) {
				inCoolDown = false;
				timeDrank = 0;
				pickedUp = false;
				speechTrigger.speak("Pick up the mug and sip");
				checkPoint = System.currentTimeMillis();
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
		workoutScore = new WorkoutScore("Accuracy", zeroCrossCalculation.calculateZeroCross());
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

	@Override
	public String getName() {
		return super.getName();
	}

	@Override
	public Integer getReps() {
		return super.getReps();
	}
}
