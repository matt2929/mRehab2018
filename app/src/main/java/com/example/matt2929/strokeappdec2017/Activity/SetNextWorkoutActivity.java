package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.matt2929.strokeappdec2017.R;

public class SetNextWorkoutActivity extends AppCompatActivity {
	DatePicker datePicker;
	TextView textView;
	Button button;
	Boolean dateSelected = false;
	int yearPick = -1, monthOfYearPick = -1, dayOfMonthPick = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_next_workout);
		datePicker = (DatePicker) findViewById(R.id.setNextCal);
		textView = (TextView) findViewById(R.id.setNextText);
		button = (Button) findViewById(R.id.setNextButt);

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (dateSelected) {
					Intent intent = new Intent(getApplicationContext(), WorkoutOrHistoryActivity.class);
					startActivity(intent);
				}
			}
		});
	}
}
