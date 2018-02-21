package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveWorkoutJSON;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.WorkoutJSON;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;
import com.example.matt2929.strokeappdec2017.Workouts.WorkoutDescription;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 */
public class HistoryViewActivity extends AppCompatActivity {

	GraphView graphView;
	SaveWorkoutJSON saveWorkoutJson;
	String workoutName = "";
	TextView xAxis, yAxis, workoutText;
	RadioGroup groupType;
	RadioButton timeRadio, gradeRadio, repsRadio;
	Button nextWorkout, backWorkout, graphLeft, graphRight;
	ImageButton imageButton;
	ArrayList<WorkoutJSON> workoutJSONS;
	ArrayList<String> workoutStrings = new ArrayList<>();
	Comparator<WorkoutJSON> workoutJSONComparator;
	int workoutIndex = 0;
	int workoutType = 0;
	Integer color = 0;
	String handToGraph = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_view);
		graphView = (GraphView) findViewById(R.id.historyGraph);
		nextWorkout = (Button) findViewById(R.id.nextWorkout);
		backWorkout = (Button) findViewById(R.id.backWorkout);
		graphLeft = (Button) findViewById(R.id.graphLeftHand);
		graphRight = (Button) findViewById(R.id.graphRightHand);
		imageButton = (ImageButton) findViewById(R.id.homeButton);
		xAxis = (TextView) findViewById(R.id.graphXAxis);
		yAxis = (TextView) findViewById(R.id.graphYAxis);
		timeRadio = (RadioButton) findViewById(R.id.radioTime);
		gradeRadio = (RadioButton) findViewById(R.id.radioAccuracy);
		repsRadio = (RadioButton) findViewById(R.id.radioReps);
		workoutJSONComparator = new Comparator<WorkoutJSON>() {
			@Override
			public int compare(WorkoutJSON t0, WorkoutJSON t1) {
				return (int) (t0.getCalendar().getTimeInMillis() - t1.getCalendar().getTimeInMillis());
			}
		};
		timeRadio.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onRadioButtonClicked(view);
			}
		});
		gradeRadio.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onRadioButtonClicked(view);
			}
		});
		repsRadio.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onRadioButtonClicked(view);
			}
		});
		workoutText = (TextView) findViewById(R.id.CurrentWorkoutText);
		groupType = (RadioGroup) findViewById(R.id.radioTypes);

		if (WorkoutData.UserData.getHand() == 0) {
			handToGraph = "Left";
			leftHandView();
		} else {
			handToGraph = "Right";
			rightHandView();
		}
		saveWorkoutJson = new SaveWorkoutJSON(getApplicationContext());
		workoutJSONS = saveWorkoutJson.getWorkouts();
		for (WorkoutJSON workoutJSON : workoutJSONS) {
			if (!workoutStrings.contains(workoutJSON.getWorkoutName())) {
				workoutStrings.add(workoutJSON.getWorkoutName());
			}
		}

		if (workoutStrings.size() > 0) {
			workoutName = workoutStrings.get(0);
			workoutText.setText(workoutName);
			for (WorkoutDescription workoutDescription : WorkoutData.WORKOUT_DESCRIPTIONS) {
				if (workoutDescription.getName().equals(workoutName)) {
					color = workoutDescription.getColor();
				}
			}
		} else {
			workoutText.setText("No Workouts");
		}
		xAxis.setText("Week From Start");

		graphLeft.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				leftHandView();
				handToGraph = "Left";
				setUpGraph(workoutJSONS, workoutType);
			}
		});

		graphRight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				rightHandView();
				handToGraph = "Right";
				setUpGraph(workoutJSONS, workoutType);
			}
		});
		nextWorkout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				workoutIndex++;
				if (workoutIndex >= workoutStrings.size()) {
					workoutIndex = 0;
				}
				workoutName = workoutStrings.get(workoutIndex);
				for (WorkoutDescription workoutDescription : WorkoutData.WORKOUT_DESCRIPTIONS) {
					if (workoutDescription.getName().equals(workoutName)) {
						color = workoutDescription.getColor();
					}
				}
				workoutText.setText(workoutStrings.get(workoutIndex));
				setUpGraph(workoutJSONS, workoutType);
			}
		});

		backWorkout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				workoutIndex--;
				if (workoutIndex < 0) {
					workoutIndex = workoutStrings.size() - 1;
				}
				workoutName = workoutStrings.get(workoutIndex);
				for (WorkoutDescription workoutDescription : WorkoutData.WORKOUT_DESCRIPTIONS) {
					if (workoutDescription.getName().equals(workoutName)) {
						color = workoutDescription.getColor();
					}
				}
				workoutText.setText(workoutStrings.get(workoutIndex));
				setUpGraph(workoutJSONS, workoutType);
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

	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch (view.getId()) {
			case R.id.radioReps:
				if (checked) {
					workoutType = 0;
					setUpGraph(workoutJSONS, 0);
				}
				break;
			case R.id.radioTime:
				if (checked) {

					workoutType = 1;
					setUpGraph(workoutJSONS, 1);
				}
				break;
			case R.id.radioAccuracy:
				if (checked) {
					workoutType = 2;
					setUpGraph(workoutJSONS, 2);
				}
				break;
		}
	}

	/**
	 * @param workoutJSONS
	 * @param workoutType
	 */
	public void setUpGraph(ArrayList<WorkoutJSON> workoutJSONS, int workoutType) {

		if (workoutJSONS.size() > 1) {
			Collections.sort(workoutJSONS, workoutJSONComparator);
		}
		ArrayList<WorkoutJSON> handWorkouts = leftHand(workoutJSONS);
		LineGraphSeries<DataPoint> lineGraphSeriesLeft;
		if (workoutType == 0) {
			lineGraphSeriesLeft = repsToGraph(workoutName, handWorkouts);
			graphView.setTitle("Weekly Repetitions (" + handToGraph + ")");
			yAxis.setText("Reps (Average)");
		} else if (workoutType == 1) {
			lineGraphSeriesLeft = timeToGraph(workoutName, handWorkouts);
			graphView.setTitle("Weekly Repetition Time (" + handToGraph + ")");
			yAxis.setText("Seconds (Average)");
		} else {
			lineGraphSeriesLeft = accuracyToGraph(workoutName, handWorkouts);
			graphView.setTitle("Weekly Repetition Accuracy (" + handToGraph + ")");
			yAxis.setText("Accuracy (Average)");
		}
		lineGraphSeriesLeft.setAnimated(true);
		graphView.removeAllSeries();
		lineGraphSeriesLeft.setColor(color);
		graphView.addSeries(lineGraphSeriesLeft);
		graphView.invalidate();
	}

	/**
	 * @param workoutJSONS
	 * @return
	 */
	public ArrayList<WorkoutJSON> leftHand(ArrayList<WorkoutJSON> workoutJSONS) {
		ArrayList<WorkoutJSON> filteredWorkoutJSONS = new ArrayList<>();
		String usersHand = "";

		for (WorkoutJSON workout : workoutJSONS) {

			if (workout.getHand().equals(handToGraph)) {
				filteredWorkoutJSONS.add(workout);
			}
		}
		return filteredWorkoutJSONS;
	}

	/**
	 * @param workoutStr
	 * @param workoutJSONS
	 * @return
	 */
	public LineGraphSeries<DataPoint> repsToGraph(String workoutStr, ArrayList<WorkoutJSON> workoutJSONS) {
		ArrayList<WorkoutJSON> filteredWorkoutJSONS = new ArrayList<>();
		for (WorkoutJSON workout : workoutJSONS) {
			if (workout.getWorkoutName().equals(workoutStr)) {
				filteredWorkoutJSONS.add(workout);
			}
		}
		DataPoint[] dataPoints = new DataPoint[filteredWorkoutJSONS.size()];
		for (int i = 0; i < dataPoints.length; i++) {
			dataPoints[i] = new DataPoint(i, filteredWorkoutJSONS.get(i).getReps());
		}
		LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
		return series;
	}

	/**
	 * @param workoutStr
	 * @param workoutJSONS
	 * @return
	 */
	public LineGraphSeries<DataPoint> timeToGraph(String workoutStr, ArrayList<WorkoutJSON> workoutJSONS) {
		ArrayList<WorkoutJSON> filteredWorkoutJSONS = new ArrayList<>();
		for (WorkoutJSON workout : workoutJSONS) {
			if (workout.getWorkoutName().equals(workoutStr)) {
				filteredWorkoutJSONS.add(workout);
			}
		}
		DataPoint[] dataPoints = new DataPoint[filteredWorkoutJSONS.size()];
		for (int i = 0; i < dataPoints.length; i++) {
			dataPoints[i] = new DataPoint(i + 1, filteredWorkoutJSONS.get(i).getDuration());
		}
		LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
		Log.e("DATA", "" + dataPoints.length);
		return series;
	}

	/**
	 * @param workoutStr
	 * @param workoutJSONS
	 * @return
	 */
	public LineGraphSeries<DataPoint> accuracyToGraph(String workoutStr, ArrayList<WorkoutJSON> workoutJSONS) {
		ArrayList<WorkoutJSON> filteredWorkoutJSONS = new ArrayList<>();
		for (WorkoutJSON workout : workoutJSONS) {
			if (workout.getWorkoutName().equals(workoutStr)) {
				filteredWorkoutJSONS.add(workout);
			}
		}
		DataPoint[] dataPoints = new DataPoint[filteredWorkoutJSONS.size()];
		for (int i = 0; i < dataPoints.length; i++) {
			dataPoints[i] = new DataPoint(i, filteredWorkoutJSONS.get(i).getAccuracy());
		}
		LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
		return series;
	}

	public void leftHandView() {
		graphLeft.setBackground(getApplicationContext().getDrawable(R.drawable.button_shape_maroon));
		graphLeft.setTextColor(Color.WHITE);
		graphRight.setBackground(getApplicationContext().getDrawable(R.drawable.button_shape_grey));
		graphRight.setTextColor(Color.BLACK);
	}

	public void rightHandView() {
		graphRight.setBackground(getApplicationContext().getDrawable(R.drawable.button_shape_maroon));
		graphRight.setTextColor(Color.WHITE);
		graphLeft.setBackground(getApplicationContext().getDrawable(R.drawable.button_shape_grey));
		graphLeft.setTextColor(Color.BLACK);
	}
}

