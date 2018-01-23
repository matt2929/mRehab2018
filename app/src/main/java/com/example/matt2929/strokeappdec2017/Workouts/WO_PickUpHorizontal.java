package com.example.matt2929.strokeappdec2017.Workouts;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.EndRepTrigger;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.Utilities.JerkScoreCalculation;
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
	boolean savingJerk = false;
	JerkScoreCalculation jerkScoreCalculation;

	public WO_PickUpHorizontal(String Name, Integer reps, SpeechTrigger speechTrigger, EndRepTrigger endRepTrigger, SFXPlayer sfxPlayer, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
		super.SensorWorkout(Name, reps, speechTrigger, endRepTrigger, sfxPlayer, outputWorkoutData, outputWorkoutStrings);
		jerkScoreCalculation = new JerkScoreCalculation();
	}

	@Override
	public void SensorDataIn(float[] data) {
		super.SensorDataIn(data);
		int sensorChoice = 1;
		if (Name.contains("Bowl")) {
			sensorChoice = 0;
		}
		if (WorkoutInProgress) {
			if (savingJerk) {
				jerkScoreCalculation.AccelerationIn(data);
			}
			if (AverageDataValue[sensorChoice] > thresehold) {
				if (savingJerk == false) {
					savingJerk = true;
				}
				moving = true;
				belowThresholdCount = 0;
			}
			if (AverageDataValue[sensorChoice] < thresehold) {
				belowThresholdCount++;
				if (belowThresholdCount > belowThresholdMax) {
					if (moving == true) {
						savingJerk = false;
						jerkScoreCalculation.CalculateJerkSingle(5);
						pickUpCount++;
						outputStrings(new String[]{"" + pickUpCount});
						endRepTrigger.endRep();
						speechTrigger.speak("" + pickUpCount);
						if (pickUpCount / reps == .5) {
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
		workoutScore = new WorkoutScore("Jerk", jerkScoreCalculation.CalculateJerkAverage());
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