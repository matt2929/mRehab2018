package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveWorkoutJSON;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.WorkoutJSON;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;
import com.example.matt2929.strokeappdec2017.Workouts.WorkoutDescription;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PostWorkoutReportActivity extends AppCompatActivity {
	private final int CHECK_CODE = 0x1;
	ArrayList<WorkoutJSON> workoutJSONS;
	SaveWorkoutJSON saveWorkoutJSON;
	ImageButton imageButton;
	boolean betterReps = false;
	boolean betterSmoothness = false;
	boolean betterTime = false;
	WorkoutJSON thisWorkout;
	ImageView smoothView, timeView, repView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_workout_report);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		smoothView = findViewById(R.id.shakeImage);
		timeView = findViewById(R.id.timeImage);
		repView = findViewById(R.id.repImage);
		Button button = findViewById(R.id.continueButton);
		ImageButton imageButton = findViewById(R.id.homeButton);
		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), WorkoutOrHistoryOrCalendarActivity.class);
				startActivity(intent);
			}
		});
		saveWorkoutJSON = new SaveWorkoutJSON(getApplicationContext());
		workoutJSONS = saveWorkoutJSON.getWorkouts();
		ArrayList<WorkoutJSON> workoutJSONSFiltered = new ArrayList<>();
		Comparator<WorkoutJSON> workoutJSONComparator = new Comparator<WorkoutJSON>() {
			@Override
			public int compare(WorkoutJSON t0, WorkoutJSON t1) {
				return (int) (t1.getCalendar().getTimeInMillis() - t0.getCalendar().getTimeInMillis());
			}
		};
		String accuracyWording = "";
		for (WorkoutJSON workoutJSON : workoutJSONS) {
			if (workoutJSON.getWorkoutName().equals(getIntent().getStringExtra("Workout"))) {
				workoutJSONSFiltered.add(workoutJSON);
			}
		}
		for (WorkoutDescription workoutDescription : WorkoutData.WORKOUT_DESCRIPTIONS) {
			if (workoutDescription.getName().equals(getIntent().getStringExtra("Workout"))) {
				if (workoutDescription.getWorkoutType().equals(WorkoutData.Workout_Type_Touch)) {
					accuracyWording = "Accuracy";
				} else {
					accuracyWording = "Smoothness";
				}
				TextView textView = findViewById(R.id.smoothnessTitle);
				textView.setText("Average " + accuracyWording);
				break;
			}
		}
		Collections.sort(workoutJSONSFiltered, workoutJSONComparator);
		thisWorkout = workoutJSONSFiltered.get(0);
		betterReps = false;
		betterSmoothness = false;
		betterTime = false;
		if (workoutJSONSFiltered.size() >= 2) {
			WorkoutJSON previousWorkout = workoutJSONSFiltered.get(1);
			//-----
			betterReps = (previousWorkout.getReps() <= thisWorkout.getReps());
			betterSmoothness = (previousWorkout.getAccuracy() >= thisWorkout.getAccuracy());
			betterTime = (previousWorkout.getDuration() >= thisWorkout.getDuration());
			//-----
			//-----
			if (betterReps) {
				blinkView(repView);
				repView.setBackgroundColor(Color.GREEN);
			}
			if (betterSmoothness) {
				blinkView(smoothView);
				smoothView.setBackgroundColor(Color.GREEN);

			}
			if (betterTime) {
				blinkView(timeView);
				timeView.setBackgroundColor(Color.GREEN);
			}
		} else {

		}


		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//TODO:if within some data, DO YOU WANT TO ADD A NEW GOAL?
				Intent intent = new Intent(getApplicationContext(), WorkoutSelectionActivity.class);
				startActivity(intent);
			}
		});

	}

	private void checkTTS() {
		Intent check = new Intent();
		check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(check, CHECK_CODE);
	}


	private void blinkView(final View view) {
		Handler handler = new Handler();
		int delay = 750;
		for (int i = 0; i < 8; i++) {
			if (i % 2 != 1) {
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						view.setBackgroundColor(Color.TRANSPARENT);
					}
				}, delay);
			} else {
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						view.setBackgroundColor(Color.GREEN);
					}
				}, delay);
			}
			delay += 100;
		}

	}

	private String floatToHundreth(float value) {
		return new DecimalFormat("#.##").format(value);
	}
}

