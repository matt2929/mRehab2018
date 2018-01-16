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
					new WorkoutDescription("Horizontal Bowl", Workout_Type_Sensor, Sensor_Type_Linear, 10,
							"In this workout you will move the bowl from side to side. Be sure to let it sit on the table when you bring it down. When I count pick up the bowl again"),
					new WorkoutDescription("Vertical Bowl", Workout_Type_Sensor, Sensor_Type_Linear, 10,
							"Move the bowl onto the shelf then back onto the table. Wait for me to count before moving\n" +
									"me again."),
					new WorkoutDescription("Horizontal Cup", Workout_Type_Sensor, Sensor_Type_Linear, 10,
							"In this workout you will move the cup from side to side. Be sure to let it sit on the table when you bring it down. When I count, pick up the cup again."),
					new WorkoutDescription("Vertical Cup", Workout_Type_Sensor, Sensor_Type_Linear, 10,
							"Move the cup onto the shelf then back onto the table. Wait for me to count before moving me again."),
					new WorkoutDescription("Walk Cup", Workout_Type_Sensor, Sensor_Type_Linear, 10,
							"Hold the bowl infront of you. Walk fowards at a comfortable pace. Walk for 15 Seconds"),
					new WorkoutDescription("Twist Cup", Workout_Type_Sensor, Sensor_Type_Linear, 10,
							"In this workout you will hold the cup up and pretend to pour out water in front of you. Do not\n" +
									"pour too quick or to slow"),
					new WorkoutDescription("Pour Cup", Workout_Type_Sensor, Sensor_Type_Gravity, 10,
							"In this workout you will hold the cup up and pretend to pour out water in front of you. Do not\n" +
									"pour too quick or to slow."),
					new WorkoutDescription("Unlock Key", Workout_Type_Touch, null, 5,
							"With the key in contact with the phone screen, rotate the key clockwise and counterclockwise.\n" +
									"Try to follow the arc as accurately as possible."),
					new WorkoutDescription("Unlock Door", Workout_Type_Touch, null, 5,
							"With the doorknob in contact with the phone screen, rotate the doorknob clockwise and counterclockwise. Try to follow the arc as accurately as possible."),
					new WorkoutDescription("Phone Number", Workout_Type_Touch, null, 3,
							"Type in the phone number shown above as accurately and quickly as possible. You can hold\n" +
									"the phone in your hand."),
					new WorkoutDescription("Multi Touch", Workout_Type_Touch, null, 5,
							"In this workout you need to click all the blue circles"),
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
	public static Float progressLocal = 0f;
	public static Float progressCloud = 0f;
}
