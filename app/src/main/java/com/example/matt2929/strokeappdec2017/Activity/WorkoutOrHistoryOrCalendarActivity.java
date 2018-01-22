package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.matt2929.strokeappdec2017.R;

public class WorkoutOrHistoryOrCalendarActivity extends AppCompatActivity {

	TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout_or_history);
		Button history = (Button) findViewById(R.id.workHist_Hist);
		Button workout = (Button) findViewById(R.id.workHist_Work);
		Button calendar = (Button) findViewById(R.id.workHist_Next);
		textView = (TextView) findViewById(R.id.setDateText);
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
