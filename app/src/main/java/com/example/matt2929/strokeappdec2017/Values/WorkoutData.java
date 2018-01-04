package com.example.matt2929.strokeappdec2017.Values;

import com.example.matt2929.strokeappdec2017.Workouts.WorkoutDescription;

/**
 * Created by matt2929 on 12/18/17.
 */

public final class WorkoutData {

    public static final String Workout_Type_Sensor = "Workout Sensor";
    public static final String Workout_Type_Touch = "Workout Touch";
    public static final String Sensor_Type_Linear = "Sensor Linear";
    public static final String Sensor_Type_Gravity = "Sensor Gravity";
    public final static WorkoutDescription[] WORKOUT_DESCRIPTIONS = new WorkoutDescription[]
            {
                    new WorkoutDescription("Horizontal Bowl", Workout_Type_Sensor, Sensor_Type_Linear),
                    new WorkoutDescription("Vertical Bowl", Workout_Type_Sensor, Sensor_Type_Linear),
                    new WorkoutDescription("Horizontal Cup", Workout_Type_Sensor, Sensor_Type_Linear),
                    new WorkoutDescription("Vertical Cup", Workout_Type_Sensor, Sensor_Type_Linear),
                    new WorkoutDescription("Walk Cup", Workout_Type_Sensor, Sensor_Type_Linear),
                    new WorkoutDescription("Twist Cup", Workout_Type_Sensor, Sensor_Type_Linear),
                    new WorkoutDescription("Pour Cup", Workout_Type_Sensor, Sensor_Type_Gravity),
                    new WorkoutDescription("Unlock Key", Workout_Type_Touch, null),
                    new WorkoutDescription("Unlock Door", Workout_Type_Touch, null),
                    new WorkoutDescription("Phone Number", Workout_Type_Touch, null),
                    new WorkoutDescription("Multi Touch", Workout_Type_Touch, null),
            };
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
            Workout_Type_Touch,
            Workout_Type_Touch};
    public static final String TTS_WORKOUT_DESCRIPTION = "WORKOUT_DESCRIPTION"; //Say workout description
    public static final String TTS_WORKOUT_READY = "WORKOUT_READY";//Say Ready then have some delay
    public static final String TTS_WORKOUT_BEGIN = "WORKOUT_BEGIN";//Say Begin
	public static final String TTS_WORKOUT_COMPLETE = "WORKOUT_COMPLETE";//Say SensorWorkoutAbstract complete then give post workout feedback
	public static final String TTS_WORKOUT_AUDIO_FEEDBACK = "WORKOUT_FEEDBACK";//Give mid workout feedback
    public static final String TEST = "TEST";
    public static String UserName = "";
    public static Float progress = 0f;
}
