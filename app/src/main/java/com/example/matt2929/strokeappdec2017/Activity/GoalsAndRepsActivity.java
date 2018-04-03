package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveHistoricalGoals;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveHistoricalReps;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

import java.util.ArrayList;

public class GoalsAndRepsActivity extends
		AppCompatActivity {
	SaveHistoricalGoals saveHistoricalGoals;
	SaveHistoricalReps saveHistoricalReps;
	Integer reps = 10;
	Intent newIntent;
	TextView repCountText, selectedWorkoutText, selectedHandText;
	ImageButton imageButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goals_and_reps);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		Intent oldIntent = getIntent();
		String WorkoutHand = oldIntent.getStringExtra("Hand");
		String WorkoutName = oldIntent.getStringExtra("Workout");
		String WorkoutType = oldIntent.getStringExtra("WorkoutType");
		newIntent = oldIntent.setClass(getApplicationContext(), WorkoutPreviewActivity.class);
		saveHistoricalGoals = new SaveHistoricalGoals(getApplicationContext());
		saveHistoricalReps = new SaveHistoricalReps(getApplicationContext(), WorkoutData.UserName);
		ArrayList<String> goals = saveHistoricalGoals.getGoals();
		ArrayList<String> temp = new ArrayList<>();
		for (int i = 0; i < goals.size(); i++) {
			if (goals.get(i).contains(WorkoutName)) {
				temp.add(goals.get(i));
			}
		}
		goals = temp;
		reps = saveHistoricalReps.getWorkoutReps(WorkoutName);
		repCountText = findViewById(R.id.GoalAndRepNumberText);
		selectedHandText = findViewById(R.id.handSelectionTextView);
		selectedWorkoutText = findViewById(R.id.workoutSelectionView);
		selectedWorkoutText.setText(WorkoutName);
		selectedHandText.setText(WorkoutHand);
		Button startWorkout = findViewById(R.id.continueButton);
		Button repPlus = findViewById(R.id.GoalAndRepPlus);
		Button repMinus = findViewById(R.id.GoalAndRepMinus);
		imageButton = findViewById(R.id.homeButton);
		repCountText.setText("" + reps);
		/*ListView goalListView = (ListView) findViewById(R.id.GoalAndRepGoalsList);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.text_view_list, android.R.id.text1, goals);
		// Assign adapter to ListView
		goalListView.setAdapter(adapter);*/
		repMinus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				updateReps((reps - 1));
			}
		});
		repPlus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				updateReps((reps + 1));
			}
		});

		startWorkout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				updateReps((3));
				newIntent.putExtra("Reps", 3);
				startActivity(newIntent);
			}
		});
		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), WorkoutOrHistoryOrCalendarActivity.class);
				startActivity(intent);
			}
		});
	}

	public void updateReps(int i) {
		if (i <= 30 && i >= 1) {
			reps = i;
			repCountText.setText("" + reps);
		} else {
			Toast.makeText(getApplicationContext(), "Reps Must be between 1 and 30", Toast.LENGTH_SHORT).show();
		}
	}
}
