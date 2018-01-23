package com.example.matt2929.strokeappdec2017.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveWorkoutJSON;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.WorkoutJSON;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;
import com.example.matt2929.strokeappdec2017.Workouts.WorkoutDescription;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

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
	RadioButton durationRadio, gradeRadio, repsRadio;
	Button nextWorkout, backWorkout;
	ArrayList<WorkoutJSON> workoutJSONS;
	ArrayList<String> workoutStrings = new ArrayList<>();
	Comparator<WorkoutJSON> workoutJSONComparator;
	int workoutIndex = 0;
	int workoutType = 0;
	Integer color = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_view);
		graphView = (GraphView) findViewById(R.id.historyGraph);
		nextWorkout = (Button) findViewById(R.id.nextWorkout);
		backWorkout = (Button) findViewById(R.id.backWorkout);
		xAxis = (TextView) findViewById(R.id.graphXAxis);
		yAxis = (TextView) findViewById(R.id.graphYAxis);
		durationRadio = (RadioButton) findViewById(R.id.radioDuration);
		gradeRadio = (RadioButton) findViewById(R.id.radioAccuracy);
		repsRadio = (RadioButton) findViewById(R.id.radioReps);
		workoutJSONComparator = new Comparator<WorkoutJSON>() {
			@Override
			public int compare(WorkoutJSON t0, WorkoutJSON t1) {
				return (int) (t1.getCalendar().getTimeInMillis() - t0.getCalendar().getTimeInMillis());
			}
		};
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
		xAxis.setText("Weeks Ago (Average)");


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
		ArrayList<WorkoutJSON> handWorkouts = leftHand(workoutJSONS);
		BarGraphSeries<DataPoint> lineGraphSeriesLeft;
		BarGraphSeries<DataPoint> lineGraphSeriesRight;
		if (workoutType == 0) {
			lineGraphSeriesLeft = repsToGraph(workoutName, handWorkouts);
			graphView.setTitle("Weekly Repetitions (Left)");
			yAxis.setText("Reps");
		} else if (workoutType == 1) {
			lineGraphSeriesLeft = durationToGraph(workoutName, handWorkouts);
			graphView.setTitle("Weekly Durarion (Left)");
			yAxis.setText("Seconds");
		} else {
			lineGraphSeriesLeft = accuracyToGraph(workoutName, handWorkouts);
			graphView.setTitle("Weekly Accuracy (Left)");
			yAxis.setText("Accuracy");
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
		for (WorkoutJSON workout : workoutJSONS) {
			if (workout.getHand().equals("Left")) {
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

