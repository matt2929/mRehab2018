package com.example.matt2929.strokeappdec2017.Workouts;

import com.example.matt2929.strokeappdec2017.Listeners_Triggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.Listeners_Triggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.Listeners_Triggers.SpeechTrigger;

/**
 * Created by matt2929 on 12/19/17.
 */

public abstract class WorkoutAbstract {

    SpeechTrigger speechTrigger;
    OutputWorkoutData outputWorkoutData;
    OutputWorkoutStrings outputWorkoutStrings;
    boolean workoutComplete;
    WorkoutScore workoutScore;
    float[] outputData;
    String[] outputStrings;

    public void Workout(SpeechTrigger speechTrigger, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
        this.speechTrigger = speechTrigger;
        this.outputWorkoutData = outputWorkoutData;
        this.outputWorkoutStrings = outputWorkoutStrings;
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
