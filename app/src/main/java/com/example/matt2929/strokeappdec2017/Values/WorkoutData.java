package com.example.matt2929.strokeappdec2017.Values;

/**
 * Created by matt2929 on 12/18/17.
 */

public final class WorkoutData {

    public final static String[] ALL_WORKOUTS = new String[]{
            "Horizontal Bowl",
            "Vertical Bowl",
            "Horizontal Cup",
            "Vertical Cup",
            "Walk Cup",
            "Pour Cup",
            "Twist Cup",
            "Unlock Key",
            "Unlock Door",
            "Phone Number"};

    public static final String Workout_Type_Sensor = "Workout Sensor";
    public static final String Workout_Type_Touch = "Workout Touch";
    public final static String[] WORKOUT_TYPE = new String[]{
            Workout_Type_Sensor,
            Workout_Type_Sensor,
            Workout_Type_Sensor,
            Workout_Type_Sensor,
            Workout_Type_Sensor,
            Workout_Type_Sensor,
            Workout_Type_Sensor,
            Workout_Type_Touch,
            Workout_Type_Touch,
            Workout_Type_Touch};

    public static final String TTS_WORKOUT_DESCRIPTION = "WORKOUT_DESCRIPTION"; //Say workout description
    public static final String TTS_WORKOUT_READY = "WORKOUT_READY";//Say Ready then have some delay
    public static final String TTS_WORKOUT_BEGIN = "WORKOUT_BEGIN";//Say Begin
    public static final String TTS_WORKOUT_COMPLETE = "WORKOUT_COMPLETE";//Say WorkoutAbstract complete then give post workout feedback
    public static final String TTS_WORKOUT_AUDIO_FEEDBACK = "WORKOUT_FEEDBACK";//Give mid workout feedback
    public static final String TEST = "TEST";
}
