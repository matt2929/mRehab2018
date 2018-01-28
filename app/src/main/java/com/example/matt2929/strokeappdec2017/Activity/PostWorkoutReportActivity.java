package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveWorkoutJSON;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.WorkoutJSON;
import com.example.matt2929.strokeappdec2017.Utilities.SFXPlayer;
import com.example.matt2929.strokeappdec2017.WorkoutsView.GradeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PostWorkoutReportActivity extends AppCompatActivity {
	ArrayList<WorkoutJSON> workoutJSONS;
	SaveWorkoutJSON saveWorkoutJSON;
	ImageButton imageButton;
	SFXPlayer sfxPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_workout_report);
		//TODO: post workout report
		sfxPlayer = new SFXPlayer(getApplicationContext());
		sfxPlayer.loadSFX(R.raw.tada);
		GradeView repView = (GradeView) findViewById(R.id.repsView);
		GradeView qualityView = (GradeView) findViewById(R.id.scoreView);
		GradeView timeView = (GradeView) findViewById(R.id.timeView);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Button button = (Button) findViewById(R.id.postWorkoutNext);
		Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.muscle);
		Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.shake);
		Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.stop_watch);
		ImageButton imageButton = (ImageButton) findViewById(R.id.postWorkoutHome);
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


		for (WorkoutJSON workoutJSON : workoutJSONS) {
			if (workoutJSON.getWorkoutName().equals(getIntent().getStringExtra("Workout"))) {
				workoutJSONSFiltered.add(workoutJSON);
			}
		}
		Collections.sort(workoutJSONSFiltered, workoutJSONComparator);
		WorkoutJSON thisWorkout = workoutJSONSFiltered.get(0);
		boolean betterReps = false;
		boolean betterSmoothness = false;
		boolean betterTime = false;
		if (workoutJSONSFiltered.size() >= 2) {
			WorkoutJSON previousWorkout = workoutJSONSFiltered.get(1);
			betterReps = (previousWorkout.getReps() <= thisWorkout.getReps());
			betterSmoothness = (previousWorkout.getAccuracy() >= thisWorkout.getAccuracy());
			betterTime = (previousWorkout.getDuration() >= thisWorkout.getDuration());
			repView.SetupView(bitmap1, "Number of Repetitions", previousWorkout.getReps(), thisWorkout.getReps(), betterReps);
			qualityView.SetupView(bitmap2, "Repetition Smoothness (Average)", previousWorkout.getAccuracy(), thisWorkout.getAccuracy(), betterSmoothness);
			timeView.SetupView(bitmap3, "Repetition Time (Average)", previousWorkout.getDuration(), thisWorkout.getDuration(), betterTime);

		} else {
			repView.SetupView(bitmap1, "Number of Repetitions", -1, thisWorkout.getReps(), false);
			qualityView.SetupView(bitmap2, "Repetition Smoothness (Average)", -1, thisWorkout.getAccuracy(), false);
			timeView.SetupView(bitmap3, "Repetition Time (Average)", -1, thisWorkout.getDuration(), false);
		}

		if (betterReps || betterSmoothness || betterTime) {
			sfxPlayer.playSFX();
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
}
