package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveActivitiesDoneToday;
import com.example.matt2929.strokeappdec2017.Utilities.WorkoutSelectData;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;
import com.example.matt2929.strokeappdec2017.Workouts.WorkoutDescription;
import com.example.matt2929.strokeappdec2017.WorkoutsView.WorkoutSelectAdapter;

import java.util.ArrayList;

import static com.example.matt2929.strokeappdec2017.Values.WorkoutData.WORKOUT_DESCRIPTIONS;
import static com.example.matt2929.strokeappdec2017.Values.WorkoutData.Workout_Type_Sensor;

public class WorkoutSelectionActivity extends AppCompatActivity {

	int currentSelection = -1;
	SaveActivitiesDoneToday saveActivitiesDoneToday;
	ImageButton imageButton;
	Boolean isCupWorkout = false;
	ListView listView;
	Button left, right;
	boolean selectedYet = false;
	Intent intent;
	Button continueButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout_selection);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		left = findViewById(R.id.selectLeft);
		right = findViewById(R.id.selectRight);
		intent = new Intent(getApplicationContext(), GoalsAndRepsActivity.class);
		continueButton = findViewById(R.id.continueButton);
		imageButton = findViewById(R.id.homeButton);
		listView = findViewById(R.id.selectActivity);
		nothingSelectedView();

		saveActivitiesDoneToday = new SaveActivitiesDoneToday(getApplicationContext());
		left.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				handSelection(true);
			}
		});

		//Sign into existing newUser
		right.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				handSelection(false);
			}
		});


		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), WorkoutOrHistoryOrCalendarActivity.class);

				startActivity(intent);
			}
		});

		//Select Existing User
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				currentSelection = i;
				isCupWorkout = WorkoutData.WORKOUT_DESCRIPTIONS[i].getPrintType().equals(WorkoutData.Print_Container_Cup);
				somethingSelectedView();
			}

		});
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
				currentSelection = i;
				isCupWorkout = WorkoutData.WORKOUT_DESCRIPTIONS[i].getPrintType().equals(WorkoutData.Print_Container_Cup);
				somethingSelectedView();
				return false;
			}
		});
		listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
				currentSelection = i;
				isCupWorkout = WorkoutData.WORKOUT_DESCRIPTIONS[i].getPrintType().equals(WorkoutData.Print_Container_Cup);
				somethingSelectedView();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		ArrayList<WorkoutSelectData> workouts = new ArrayList<>();
		for (int i = 0; i < WorkoutData.WORKOUT_DESCRIPTIONS.length; i++) {
			WorkoutDescription wd = WorkoutData.WORKOUT_DESCRIPTIONS[i];
			workouts.add(new WorkoutSelectData(wd.getName(), saveActivitiesDoneToday.getWorkoutActivityCount(wd.getName()), wd.getColor(), wd.getDrawableID()));
		}
		WorkoutSelectAdapter workoutSelectAdapter = new WorkoutSelectAdapter(getApplicationContext(), workouts);
		listView.setAdapter(workoutSelectAdapter);

	}

	public void handSelection(boolean leftHand) {
		if (currentSelection != -1) {
			continueButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					isCupWorkout = WorkoutData.WORKOUT_DESCRIPTIONS[currentSelection].getPrintType().equals(WorkoutData.Print_Container_Cup);
					if (WORKOUT_DESCRIPTIONS[currentSelection].getWorkoutType().equals(Workout_Type_Sensor)) {
						intent.putExtra("WorkoutType", "Sensor");
					} else {
						intent.putExtra("WorkoutType", "Touch");
					}
					intent.putExtra("Workout", WORKOUT_DESCRIPTIONS[currentSelection].getName());
					intent.setClass(getApplicationContext(), PutPhoneInContainer.class);
					startActivity(intent);
				}
			});
			continueButton.setAlpha(1f);
			continueButton.setClickable(true);
			if (leftHand) {
				intent.putExtra("Hand", "Left");
				left.setTextColor(Color.WHITE);
				left.setBackground(this.getResources().getDrawable(R.drawable.button_shape_maroon));
				right.setTextColor(Color.BLACK);
				right.setBackground(this.getResources().getDrawable(R.drawable.button_shape_grey));
			} else {
				left.setTextColor(Color.BLACK);
				left.setBackground(this.getResources().getDrawable(R.drawable.button_shape_grey));
				right.setTextColor(Color.WHITE);
				right.setBackground(this.getResources().getDrawable(R.drawable.button_shape_maroon));
				intent.putExtra("Hand", "Right");

			}
		} else {
			Toast.makeText(getApplicationContext(), "Please Select a Workout", Toast.LENGTH_SHORT).show();
		}
	}

	public void nothingSelectedView() {
		Toast.makeText(getApplicationContext(), "Please Select an Activity", Toast.LENGTH_SHORT).show();
		left.setAlpha(.1f);
		right.setAlpha(.1f);
		continueButton.setAlpha(.1f);
		left.setClickable(false);
		right.setClickable(false);
		continueButton.setClickable(false);


	}

	public void somethingSelectedView() {
		if (!selectedYet) {
			Toast.makeText(getApplicationContext(), "Please Select a Hand", Toast.LENGTH_SHORT).show();
		}
		selectedYet = true;
		left.setAlpha(1f);
		right.setAlpha(1f);
		left.setClickable(true);
		right.setClickable(true);
	}
}
