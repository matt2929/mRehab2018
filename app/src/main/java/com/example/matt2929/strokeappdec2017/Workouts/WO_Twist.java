package com.example.matt2929.strokeappdec2017.Workouts;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.Utilities.SFXPlayer;

/**
 * Created by matt2929 on 12/21/17.
 */

public class WO_Twist extends SensorWorkoutAbstract {
    public WO_Twist(String Name, Integer reps, SpeechTrigger speechTrigger, SFXPlayer sfxPlayer, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
        super.Workout(Name, reps, speechTrigger, sfxPlayer, outputWorkoutData, outputWorkoutStrings);
    }
}
