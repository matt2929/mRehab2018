package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveHistoricalGoals;
import com.example.matt2929.strokeappdec2017.Utilities.SaveLastGoalSet;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

import java.util.ArrayList;
import java.util.Calendar;

public class SetGoalsActivity extends AppCompatActivity {
	SaveLastGoalSet saveLastGoalSet;
	TextView workoutText;
	ImageButton nextWorkout, backWorkout;
	RadioButton radioTime, radioRep, radioSmooth;
	Button save;
	int workoutIndex = 0;
	ArrayList<String> workoutStrings = new ArrayList<>();
	String choice = "Increase Reps";

	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_goals);
		saveLastGoalSet = new SaveLastGoalSet();
		workoutText = (TextView) findViewById(R.id.goalWorktoutText);
		nextWorkout = (ImageButton) findViewById(R.id.goalNextWorkout);
		backWorkout = (ImageButton) findViewById(R.id.goalBackWorkout);
		radioTime = (RadioButton) findViewById(R.id.goalDecreaseTime);
		radioRep = (RadioButton) findViewById(R.id.goalIncreaseRep);
		radioSmooth = (RadioButton) findViewById(R.id.goalIncreaseSmoothness);
		save = (Button) findViewById(R.id.goalSaveGoal);
		workoutText.setText(WorkoutData.WORKOUT_DESCRIPTIONS[0].getName());
		radioRep.performClick();
		for (int i = 0; i < WorkoutData.WORKOUT_DESCRIPTIONS.length; i++) {
			workoutStrings.add(WorkoutData.WORKOUT_DESCRIPTIONS[i].getName());
		}
		nextWorkout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				workoutIndex++;
				if (workoutIndex >= workoutStrings.size()) {
					workoutIndex = 0;
				}
				workoutText.setText(workoutStrings.get(workoutIndex));
			}
		});

		backWorkout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				workoutIndex--;
				if (workoutIndex < 0) {
					workoutIndex = workoutStrings.size() - 1;
				}
				workoutText.setText(workoutStrings.get(workoutIndex));
			}
		});

		radioRep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				if (b) {
					choice = "Increase Reps";
				}
			}
		});

		radioSmooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				if (b) {
					choice = "Increase Smoothness";
				}
			}
		});
		radioTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				if (b) {
					choice = "Decrease Time";
				}
			}
		});
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (choice != null) {
					saveClickAction();
				} else {
					Toast.makeText(getApplicationContext(), "Please Select a goal", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public void saveClickAction() {
		SaveHistoricalGoals saveHistoricalGoals = new SaveHistoricalGoals(getApplicationContext());
		Calendar today = Calendar.getInstance();
		saveHistoricalGoals.saveGoals("On the " + workoutText.getText().toString() + " Activity you want to " + choice
				+ ": " + today.get(Calendar.MONTH) + "/" + today.get(Calendar.DAY_OF_MONTH) + "/" + today.get(Calendar.YEAR));
		saveLastGoalSet.saveGoalDateNow(this);
		Intent intent = new Intent(getApplicationContext(), WorkoutOrHistoryOrCalendarActivity.class);
		startActivity(intent);
	}

}
