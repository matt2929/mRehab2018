package com.example.matt2929.strokeappdec2017.Workouts;

import android.util.Log;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.Utilities.SFXPlayer;

/**
 * Created by matt2929 on 12/21/17.
 */

public class WO_Pour extends WorkoutAbstract {
    float filledPercentage = 100f;
    float thresehold = 15f;
    float removalRate = .1f;

    public WO_Pour(String Name, Integer reps, SpeechTrigger speechTrigger, SFXPlayer SFX, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
        super.Workout(Name, reps, speechTrigger, SFX, outputWorkoutData, outputWorkoutStrings);
        sfxPlayer.loadSFX(R.raw.pour_water);
        sfxPlayer.loopSFX();
    }

    @Override
    public void SensorDataIn(float[] data) {
        super.SensorDataIn(data);
        if (WorkoutInProgress) {
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
            if (Math.abs(AngleStandardized - filledPercentage) <= thresehold) {
                filledPercentage -= removalRate;
                if (filledPercentage < 0) {
                    workoutComplete = true;
                }
                if (!sfxPlayer.isPlaying()) {
                    sfxPlayer.playSFX();
                }
            } else {
                if (sfxPlayer.isPlaying()) {
                    sfxPlayer.pauseSFX();
                }
            }
            if (filledPercentage < 0) {
                workoutComplete = true;
            }
            Log.e("Angle: ", Angle + "º" + "AngleS: " + AngleStandardized + "º Filled%" + filledPercentage);
            outputData(new float[]{Angle, ((filledPercentage) / 100f)});
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
