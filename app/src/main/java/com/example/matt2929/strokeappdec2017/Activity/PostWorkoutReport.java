package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.WorkoutsView.GradeView;

public class PostWorkoutReport extends AppCompatActivity {

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
	repView.SetupView(bitmap1, "Number of Repetitions", 1, 0, false);
	qualityView.SetupView(bitmap2, "Quality of Movement", 1, 0, true);
	durationView.SetupView(bitmap3, "Duration of Workout", 1, 0, false);
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
