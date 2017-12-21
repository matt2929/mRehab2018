package com.example.matt2929.strokeappdec2017.Workouts;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.PlaySfXTrigger;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.Utilities.AverageValue;

/**
 * Created by matt2929 on 12/19/17.
 */

public abstract class WorkoutAbstract {

    SpeechTrigger speechTrigger;
    OutputWorkoutData outputWorkoutData;
    OutputWorkoutStrings outputWorkoutStrings;
    boolean workoutComplete = false;
    WorkoutScore workoutScore;
    float[] outputData;
    String[] outputStrings;
    PlaySfXTrigger playSfXTrigger;
    float[] dataLast = null;
    boolean WorkoutInProgress = false;
    Long lastActivity = System.currentTimeMillis();
    AverageValue[] AverageValues;
    float[] AverageDataValue;
    Integer reps = 10;

    public void Workout(Integer reps, SpeechTrigger speechTrigger, PlaySfXTrigger playSfXTrigger, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
        this.reps = reps;
        this.speechTrigger = speechTrigger;
        this.outputWorkoutData = outputWorkoutData;
        this.outputWorkoutStrings = outputWorkoutStrings;
        this.playSfXTrigger = playSfXTrigger;
    }

    public void SensorDataIn(float[] data) {
        if (dataLast == null) {
            dataLast = data;
            AverageValues = new AverageValue[data.length];
            AverageDataValue = new float[data.length];
            for (int i = 0; i < data.length; i++) {
                AverageValues[i] = new AverageValue(25);
            }
        }
        for (int i = 0; i < data.length; i++) {
            AverageDataValue[i] = AverageValues[i].addData(data[i]);
        }

    }

    public void StartWorkout() {
        WorkoutInProgress = true;
        lastActivity = System.currentTimeMillis();
    }

    public boolean isWorkoutComplete() {
        return workoutComplete;
    }

    public WorkoutScore getScore() {
        return workoutScore;
    }

    public float[] outPutData() {
        return outputData;
    }

    public String[] outputStrings() {
        return outputStrings;
    }

}
