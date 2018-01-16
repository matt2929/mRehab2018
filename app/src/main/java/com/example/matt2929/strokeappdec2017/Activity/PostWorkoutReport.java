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
		long smallest = Long.MAX_VALUE;
		long secondSmallest = Long.MAX_VALUE;
		int index = 0, secondIndex = 0;
		for (int i = 0; i < workoutJSONS.size(); i++) {
			long timeDiff = Math.abs(workoutJSONS.get(i).getCalendar().getTimeInMillis() - System.currentTimeMillis());
			if (timeDiff < smallest) {
				secondSmallest = smallest;
				secondIndex = index;
				smallest = timeDiff;
				index = i;
			} else if (timeDiff < secondSmallest) {
				secondSmallest = timeDiff;
				secondIndex = i;
			}
		}
		WorkoutJSON closest = workoutJSONS.get(index);
		WorkoutJSON secondClosest = workoutJSONS.get(secondIndex);

		repView.SetupView(bitmap1, "Number of Repetitions", secondClosest.getReps(), closest.getReps(), closest.getReps() <= secondClosest.getReps());
		qualityView.SetupView(bitmap2, "Quality of Movement", secondClosest.getAccuracy(), closest.getAccuracy(), closest.getAccuracy() <= secondClosest.getAccuracy());
		durationView.SetupView(bitmap3, "Duration of Workout", secondClosest.getDuration(), closest.getDuration(), closest.getDuration() <= secondClosest.getDuration());
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
