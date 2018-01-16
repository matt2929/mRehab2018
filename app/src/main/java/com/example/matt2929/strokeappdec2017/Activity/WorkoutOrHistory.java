package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.matt2929.strokeappdec2017.R;

public class WorkoutOrHistory extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout_or_history);
		Button history = (Button) findViewById(R.id.workHist_Hist);
		Button workout = (Button) findViewById(R.id.workHist_Work);
		history.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), HistoryMain.class);
				startActivity(intent);
			}
		});


		workout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), WorkoutSelection.class);
				startActivity(intent);
			}
		});
	}
}
