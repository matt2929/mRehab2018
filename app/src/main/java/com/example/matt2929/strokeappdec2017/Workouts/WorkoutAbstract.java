package com.example.matt2929.strokeappdec2017.Workouts;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.PlaySfXTrigger;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;

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

    public void Workout(SpeechTrigger speechTrigger, PlaySfXTrigger playSfXTrigger, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
        this.speechTrigger = speechTrigger;
        this.outputWorkoutData = outputWorkoutData;
        this.outputWorkoutStrings = outputWorkoutStrings;
        this.playSfXTrigger = playSfXTrigger;
    }

    public void SensorDataIn(float[] data) {

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
