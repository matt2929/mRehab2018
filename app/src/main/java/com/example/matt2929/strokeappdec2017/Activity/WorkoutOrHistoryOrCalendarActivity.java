package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.Utilities.SaveLastGoalSet;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

public class WorkoutOrHistoryOrCalendarActivity extends AppCompatActivity {

	TextView textView;
	SaveLastGoalSet saveLastGoalSet = new SaveLastGoalSet();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout_or_history);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		textView = findViewById(R.id.userNameSplashWelcome);
		textView.setText("Welcome back, " + WorkoutData.UserName + "!");
		if (saveLastGoalSet.isOneWeek(this)) {
			Intent intent = new Intent(this, SetGoalsActivity.class);
			startActivity(intent);
		}
		Button history = findViewById(R.id.workHist_Hist);
		Button workout = findViewById(R.id.workHist_Work);
		Button calendar = findViewById(R.id.workHist_Next);
		history.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), HistoryViewActivity.class);
				startActivity(intent);
			}
		});

		workout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), WorkoutSelectionActivity.class);
				startActivity(intent);
			}
		});

		calendar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), CalendarSetActivity.class);
				startActivity(intent);
			}
		});
	}
}
