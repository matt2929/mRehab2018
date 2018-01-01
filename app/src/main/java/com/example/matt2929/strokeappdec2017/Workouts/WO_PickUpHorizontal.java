package com.example.matt2929.strokeappdec2017.Workouts;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.Utilities.SFXPlayer;

/**
 * Created by matt2929 on 12/20/17.
 */

public class WO_PickUpHorizontal extends WorkoutAbstract {
    boolean moving = false;
    double thresehold = .75;
    long timeDelay = 1500;
    int pickUpCount = 0;

    public WO_PickUpHorizontal(String Name, Integer reps, SpeechTrigger speechTrigger, SFXPlayer sfxPlayer, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
        super.Workout(Name, reps, speechTrigger, sfxPlayer, outputWorkoutData, outputWorkoutStrings);
    }

    @Override
    public void SensorDataIn(float[] data) {
        super.SensorDataIn(data);
        if (WorkoutInProgress) {
            if (AverageDataValue[0] >= thresehold) {
                moving = true;
            }

            //if we stopped moving and the time since last pickup greater than a second
            if (moving == true && AverageDataValue[0] < 0 && data[0] > 0 && Math.abs(lastActivity - System.currentTimeMillis()) > timeDelay) {
                moving = false;
                lastActivity = System.currentTimeMillis();
                pickUpCount++;
                speechTrigger.speak("" + pickUpCount);
                if (pickUpCount == reps) {
                    workoutComplete = true;
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

