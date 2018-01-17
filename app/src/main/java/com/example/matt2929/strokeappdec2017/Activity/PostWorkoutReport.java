package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveWorkoutData;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.WorkoutJSON;
import com.example.matt2929.strokeappdec2017.WorkoutsView.GradeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PostWorkoutReport extends AppCompatActivity {
	ArrayList<WorkoutJSON> workoutJSONS;
	SaveWorkoutData saveWorkoutData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_workout_report);
		//TODO: post workout report
		GradeView repView = (GradeView) findViewById(R.id.repsView);
		GradeView qualityView = (GradeView) findViewById(R.id.scoreView);
		GradeView durationView = (GradeView) findViewById(R.id.timeView);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Button button = (Button) findViewById(R.id.postWorkoutNext);
		Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.muscle);
		Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.shake);
		Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.stop_watch);

		saveWorkoutData = new SaveWorkoutData(getApplicationContext());
		workoutJSONS = saveWorkoutData.getWorkouts();
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
		if (workoutJSONSFiltered.size() >= 2) {
			WorkoutJSON previousWorkout = workoutJSONSFiltered.get(1);
			repView.SetupView(bitmap1, "Number of Repetitions", previousWorkout.getReps(), thisWorkout.getReps(), (previousWorkout.getReps() <= thisWorkout.getReps()));
			qualityView.SetupView(bitmap2, "Quality of Movement", previousWorkout.getAccuracy(), thisWorkout.getAccuracy(), (previousWorkout.getAccuracy() >= thisWorkout.getAccuracy()));
			durationView.SetupView(bitmap3, "Duration of Workout", previousWorkout.getDuration(), thisWorkout.getDuration(), (previousWorkout.getDuration() >= thisWorkout.getDuration()));
		} else {
			repView.SetupView(bitmap1, "Number of Repetitions", -1, thisWorkout.getReps(), false);
			qualityView.SetupView(bitmap2, "Quality of Movement", -1, thisWorkout.getAccuracy(), false);
			durationView.SetupView(bitmap3, "Duration of Workout", -1, thisWorkout.getDuration(), false);
		}
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//TODO:if within some data, DO YOU WANT TO ADD A NEW GOAL?
				Intent intent = new Intent(getApplicationContext(), WorkoutSelection.class);
				startActivity(intent);
			}
		});

	}
}
