package com.example.matt2929.strokeappdec2017.Values;

import android.graphics.Color;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.User;
import com.example.matt2929.strokeappdec2017.Workouts.WorkoutDescription;

/**
 * Created by matt2929 on 12/18/17.
 */

public final class WorkoutData {

	public static final String Workout_Type_Sensor = "Workout Sensor";
	public static final String Workout_Type_Touch = "Workout Touch";
	public static final String Sensor_Type_Linear = "Sensor Linear";
	public static final String Sensor_Type_Gravity = "Sensor Gravity";

	public static final String Print_Container_Cup = "Print Cup";
	public static final String Print_Container_Bowl = "Print Bowl";
	public static final String Print_Container_Key = "Print Key";
	public static final String Print_Container_Door = "Print Door";
	public static final String Print_Container_No_Container = "No Container";

	public static final String Key_Last_Goal_Set = "Last Goal Set";

	public final static WorkoutDescription[] WORKOUT_DESCRIPTIONS = new WorkoutDescription[]
			{
					new WorkoutDescription("Horizontal Mug", Workout_Type_Sensor, Sensor_Type_Linear, Print_Container_Cup, 10,
							"You will move the mug from one side of the panel to the other. Set the mug on the table and wait for me to count. Then, move the mug again.",
							Color.rgb(125, 190, 214), R.drawable.ic_horizontalmugxhdpi),
					new WorkoutDescription("Quick Twist Mug", Workout_Type_Sensor, Sensor_Type_Linear, Print_Container_Cup, 10,
							"Hold the mug in front of you in an upright position. As quickly as you can turn your hand outward and then return to the upright position.",
							Color.rgb(125, 190, 214), R.drawable.ic_quicktwistwithmugxhdpi),
					new WorkoutDescription("Unlock With Key", Workout_Type_Touch, null, Print_Container_Key, 5,
							"With the key in contact with the phone screen, rotate the key clockwise and wait for me to count. Then rotate it counterclockwise, and wait for me to count again.",
							Color.rgb(146, 157, 24), R.drawable.ic_unlockkeyhdpi),
			};

	public static final String TTS_WORKOUT_DESCRIPTION = "WORKOUT_DESCRIPTION"; //Say workout description
	public static final String TTS_WORKOUT_READY = "WORKOUT_READY";//Say Ready then have some delay
	public static final String TTS_WORKOUT_BEGIN = "WORKOUT_BEGIN";//Say Begin
	public static final String TTS_WORKOUT_COMPLETE = "WORKOUT_COMPLETE";//Say SensorWorkoutAbstract complete then give post workout feedback
	public static final String TTS_WORKOUT_AUDIO_FEEDBACK = "WORKOUT_FEEDBACK";//Give mid workout feedback
	public static final String TEST = "TEST";
	public static String UserName = "";
	public static User UserData = null;
	public static Float progressLocal = 0f;
	public static Float progressCloud = 0f;
}
