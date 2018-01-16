package com.example.matt2929.strokeappdec2017.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveWorkoutData;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.WorkoutJSON;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 */
public class HistoryMain extends AppCompatActivity {

	GraphView graphViewLeft, graphViewRight;
	SaveWorkoutData saveWorkoutData;
	String workoutName = "";
	TextView xAxisLeft, yAxisLeft, workoutText, xAxisRight, yAxisRight;
	RadioGroup groupType;
	RadioButton durationRadio, gradeRadio, repsRadio;
	Button nextWorkout;
	ArrayList<WorkoutJSON> workoutJSONS;
	ArrayList<String> workoutStrings = new ArrayList<>();
	Comparator<WorkoutJSON> workoutJSONComparator;
	int workoutIndex = 0;
	int workoutType = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_main);
		graphViewLeft = (GraphView) findViewById(R.id.historyGraphLeft);
		graphViewRight = (GraphView) findViewById(R.id.historyGraphRight);
		nextWorkout = (Button) findViewById(R.id.nextWorkout);
		xAxisLeft = (TextView) findViewById(R.id.graphXAxisLeft);
		yAxisLeft = (TextView) findViewById(R.id.graphYAxisLeft);
		xAxisRight = (TextView) findViewById(R.id.graphXAxisRight);
		yAxisRight = (TextView) findViewById(R.id.graphYAxisRight);
		durationRadio = (RadioButton) findViewById(R.id.radioDuration);
		gradeRadio = (RadioButton) findViewById(R.id.radioAccuracy);
		repsRadio = (RadioButton) findViewById(R.id.radioReps);

		durationRadio.setOnClickListener(new View.OnClickListener() {
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
		saveWorkoutData = new SaveWorkoutData(getApplicationContext());
		workoutJSONS = saveWorkoutData.getWorkouts();
		for (WorkoutJSON workoutJSON : workoutJSONS) {
			if (!workoutStrings.contains(workoutJSON.getWorkoutName())) {
				workoutStrings.add(workoutJSON.getWorkoutName());
			}
		}

		if (workoutStrings.size() > 0) {
			workoutName = workoutStrings.get(0);
			workoutText.setText(workoutName);
		} else {
			workoutText.setText("No Workouts");
		}

		xAxisLeft.setText("Weeks Ago (Average)");
		xAxisRight.setText("Weeks Ago (Average)");

		workoutJSONComparator = new Comparator<WorkoutJSON>() {
			@Override
			public int compare(WorkoutJSON t0, WorkoutJSON t1) {
				if (t0.getCalendar().get(Calendar.YEAR) < t1.getCalendar().get(Calendar.YEAR)) {
					return -1;
				} else if (t0.getCalendar().get(Calendar.YEAR) > t1.getCalendar().get(Calendar.YEAR)) {
					return 1;
				}
				if (t0.getCalendar().get(Calendar.MONTH) < t1.getCalendar().get(Calendar.MONTH)) {
					return -1;
				} else if (t0.getCalendar().get(Calendar.MONTH) > t1.getCalendar().get(Calendar.MONTH)) {
					return 1;
				}
				if (t0.getCalendar().get(Calendar.DAY_OF_MONTH) < t1.getCalendar().get(Calendar.DAY_OF_MONTH)) {
					return -1;
				} else if (t0.getCalendar().get(Calendar.DAY_OF_MONTH) > t1.getCalendar().get(Calendar.DAY_OF_MONTH)) {
					return 1;
				}
				if (t0.getCalendar().get(Calendar.HOUR) < t1.getCalendar().get(Calendar.HOUR)) {
					return -1;
				} else if (t0.getCalendar().get(Calendar.HOUR) > t1.getCalendar().get(Calendar.HOUR)) {
					return 1;
				}
				if (t0.getCalendar().get(Calendar.MINUTE) < t1.getCalendar().get(Calendar.MINUTE)) {
					return -1;
				} else if (t0.getCalendar().get(Calendar.MINUTE) > t1.getCalendar().get(Calendar.MINUTE)) {
					return 1;
				}
				if (t0.getCalendar().get(Calendar.SECOND) < t1.getCalendar().get(Calendar.SECOND)) {
					return -1;
				} else if (t0.getCalendar().get(Calendar.SECOND) > t1.getCalendar().get(Calendar.SECOND)) {
					return 1;
				}
				return 0;
			}
		};

		nextWorkout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				workoutIndex++;
				if (workoutIndex >= workoutStrings.size()) {
					workoutIndex = 0;
				}
				workoutName = workoutStrings.get(workoutIndex);
				workoutText.setText(workoutStrings.get(workoutIndex));
				setUpGraph(workoutJSONS, workoutType);
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
			case R.id.radioDuration:
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
		ArrayList<WorkoutJSON> leftHandWorkouts = leftHand(workoutJSONS);
		ArrayList<WorkoutJSON> rightHandWorkouts = rightHand(workoutJSONS);
		BarGraphSeries<DataPoint> lineGraphSeriesLeft;
		BarGraphSeries<DataPoint> lineGraphSeriesRight;
		if (workoutType == 0) {
			lineGraphSeriesLeft = repsToGraph(workoutName, leftHandWorkouts);
			lineGraphSeriesRight = repsToGraph(workoutName, rightHandWorkouts);
			graphViewLeft.setTitle("Weekly Repetitions (Left)");
			graphViewRight.setTitle("Weekly Repetitions (Right)");
			yAxisLeft.setText("Reps");
			yAxisRight.setText("Reps");
		} else if (workoutType == 1) {
			lineGraphSeriesLeft = durationToGraph(workoutName, leftHandWorkouts);
			lineGraphSeriesRight = durationToGraph(workoutName, rightHandWorkouts);
			graphViewLeft.setTitle("Weekly Durarion (Left)");
			graphViewRight.setTitle("Weekly Duration (Right)");
			yAxisLeft.setText("Seconds");
			yAxisRight.setText("Seconds");
		} else {
			lineGraphSeriesLeft = accuracyToGraph(workoutName, leftHandWorkouts);
			lineGraphSeriesRight = accuracyToGraph(workoutName, rightHandWorkouts);
			graphViewLeft.setTitle("Weekly Accuracy (Left)");
			graphViewRight.setTitle("Weekly Accuracy (Right)");
			yAxisLeft.setText("Accuracy");
			yAxisRight.setText("Accuracy");
		}
		lineGraphSeriesLeft.setAnimated(true);
		lineGraphSeriesRight.setAnimated(true);
		graphViewLeft.removeAllSeries();
		lineGraphSeriesLeft.setColor(Color.RED);
		graphViewLeft.addSeries(lineGraphSeriesLeft);
		graphViewLeft.invalidate();


		graphViewRight.removeAllSeries();
		lineGraphSeriesRight.setColor(Color.GREEN);
		graphViewRight.addSeries(lineGraphSeriesRight);
		graphViewRight.invalidate();
	}

	/**
	 * @param workoutJSONS
	 * @return
	 */
	public ArrayList<WorkoutJSON> leftHand(ArrayList<WorkoutJSON> workoutJSONS) {
		ArrayList<WorkoutJSON> filteredWorkoutJSONS = new ArrayList<>();
		for (WorkoutJSON workout : workoutJSONS) {
			if (workout.getHand().equals("Left")) {
				filteredWorkoutJSONS.add(workout);
			}
		}
		return filteredWorkoutJSONS;
	}

	/**
	 * @param workoutJSONS
	 * @return
	 */
	public ArrayList<WorkoutJSON> rightHand(ArrayList<WorkoutJSON> workoutJSONS) {
		ArrayList<WorkoutJSON> filteredWorkoutJSONS = new ArrayList<>();
		for (WorkoutJSON workout : workoutJSONS) {
			if (workout.getHand().equals("Right")) {
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
	public BarGraphSeries<DataPoint> repsToGraph(String workoutStr, ArrayList<WorkoutJSON> workoutJSONS) {
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
		BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);
		return series;
	}

	/**
	 * @param workoutStr
	 * @param workoutJSONS
	 * @return
	 */
	public BarGraphSeries<DataPoint> durationToGraph(String workoutStr, ArrayList<WorkoutJSON> workoutJSONS) {
		ArrayList<WorkoutJSON> filteredWorkoutJSONS = new ArrayList<>();
		for (WorkoutJSON workout : workoutJSONS) {
			if (workout.getWorkoutName().equals(workoutStr)) {
				filteredWorkoutJSONS.add(workout);
			}
		}
		DataPoint[] dataPoints = new DataPoint[filteredWorkoutJSONS.size()];
		for (int i = 0; i < dataPoints.length; i++) {
			dataPoints[i] = new DataPoint(i, filteredWorkoutJSONS.get(i).getDuration());
		}
		BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);
		Log.e("DATA", "" + dataPoints.length);
		return series;
	}

	/**
	 * @param workoutStr
	 * @param workoutJSONS
	 * @return
	 */
	public BarGraphSeries<DataPoint> accuracyToGraph(String workoutStr, ArrayList<WorkoutJSON> workoutJSONS) {
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
		BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);
		return series;
	}


}


