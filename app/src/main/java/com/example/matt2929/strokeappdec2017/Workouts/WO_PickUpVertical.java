package com.example.matt2929.strokeappdec2017.Workouts;

import android.util.Log;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.PlaySfXTrigger;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;

/**
 * Created by matt2929 on 12/20/17.
 */

public class WO_PickUpVertical extends WorkoutAbstract {
    boolean moving = false;
    double thresehold = .75;
    long timeDelay = 1500;
    int pickUpCount = 0;

    public WO_PickUpVertical(Integer reps, SpeechTrigger speechTrigger, PlaySfXTrigger playSfXTrigger, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
        super.Workout(reps, speechTrigger, playSfXTrigger, outputWorkoutData, outputWorkoutStrings);
    }

    @Override
    public void SensorDataIn(float[] data) {
        super.SensorDataIn(data);
        if (WorkoutInProgress) {

            if (AverageDataValue[1] > thresehold) {
                if (moving != true) {
                }
                moving = true;

            }
            Log.e("Compare", "Av:" + AverageDataValue[1] + "Data: " + data[1]);
            //if we stopped moving and the time since last pickup greater than a second
            if (moving == true && AverageDataValue[1] < 0 && data[1] > 0 && Math.abs(lastActivity - System.currentTimeMillis()) > timeDelay) {
                moving = false;
                lastActivity = System.currentTimeMillis();
                pickUpCount++;
                speechTrigger.speak("" + pickUpCount);
                Log.e("PickUp", String.valueOf(pickUpCount));
            }
        }

    }

    @Override
    public boolean isWorkoutComplete() {
        return super.isWorkoutComplete();
    }

    @Override
    public WorkoutScore getScore() {
        return super.getScore();
    }

    @Override
    public float[] outPutData() {
        return super.outPutData();
    }

    @Override
    public String[] outputStrings() {
        return super.outputStrings();
    }


}
