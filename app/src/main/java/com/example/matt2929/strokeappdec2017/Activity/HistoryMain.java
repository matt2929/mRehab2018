package com.example.matt2929.strokeappdec2017.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveWorkoutData;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.WorkoutJSON;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class HistoryMain extends AppCompatActivity {
	Button workoutChange, reps, duration, score;
	GraphView graphView;
	ArrayList<Button> buttons = new ArrayList<>();
	SaveWorkoutData saveWorkoutData;
	Comparator<WorkoutJSON> workoutJSONComparator;
	ArrayList<String> workoutStrings = new ArrayList<>();
	String workoutName = "";
	String filter = "Reps";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_main);
		workoutChange = (Button) findViewById(R.id.historyWorkoutShown);
		reps = (Button) findViewById(R.id.historyReps);
		duration = (Button) findViewById(R.id.historyDuration);
		score = (Button) findViewById(R.id.historyScore);
		graphView = (GraphView) findViewById(R.id.historyGraph);
		saveWorkoutData = new SaveWorkoutData(getApplicationContext());
		final ArrayList<WorkoutJSON> workoutJSONS = saveWorkoutData.getWorkouts();
		buttons.add(reps);
		buttons.add(duration);
		buttons.add(score);
		for (WorkoutJSON workoutJSON : workoutJSONS) {
			if (!workoutStrings.contains(workoutJSON.getWorkoutName())) {
				workoutStrings.add(workoutJSON.getWorkoutName());
			}
		}
		if (workoutStrings.size() > 0) {
			workoutChange.setText(workoutStrings.get(0));
			workoutName = workoutStrings.get(0);
		}
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
		reps.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				filter = "Reps";
				graphView.removeAllSeries();
				repsView(workoutName, workoutJSONS);
				for (Button button : buttons) {
					button.setBackground(button.getContext().getResources().getDrawable(android.R.drawable.btn_default_small));
				}
				reps.setBackgroundColor(Color.YELLOW);
			}
		});
		duration.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				filter = "Duration";
				graphView.removeAllSeries();
				durationView(workoutName, workoutJSONS);

				for (Button button : buttons) {
					button.setBackground(button.getContext().getResources().getDrawable(android.R.drawable.btn_default_small));
				}
				duration.setBackgroundColor(Color.YELLOW);
			}
		});
		score.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				filter = "Score";
				graphView.removeAllSeries();
				scoreView(workoutName, workoutJSONS);
				for (Button button : buttons) {
					button.setBackground(button.getContext().getResources().getDrawable(android.R.drawable.btn_default_small));
				}
				score.setBackgroundColor(Color.YELLOW);
			}
		});
		workoutChange.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				for (int i = 0; i < workoutStrings.size(); i++) {

					if (workoutStrings.get(i).equals(workoutName)) {
						if (i == workoutStrings.size() - 1) {
							workoutName = workoutStrings.get(0);
						} else {
							workoutName = workoutStrings.get(i + 1);
						}
						workoutChange.setText(workoutName);
						graphView.removeAllSeries();
						if (filter.equals("Reps")) {
							repsView(workoutName, workoutJSONS);
						} else if (filter.equals("Duration")) {
							durationView(workoutName, workoutJSONS);
						} else {
							scoreView(workoutName, workoutJSONS);
						}
						break;
					}
				}
			}
		});
	}

	public void repsView(String workoutStr, ArrayList<WorkoutJSON> workoutJSONS) {
		ArrayList<WorkoutJSON> filteredWorkoutJSONS = new ArrayList<>();
		for (WorkoutJSON workout : workoutJSONS) {
			if (workout.getWorkoutName().equals(workoutStr)) {
				filteredWorkoutJSONS.add(workout);
			}
		}
		Collections.sort(filteredWorkoutJSONS, workoutJSONComparator);
		DataPoint[] dataPoints = new DataPoint[filteredWorkoutJSONS.size()];
		for (int i = 0; i < filteredWorkoutJSONS.size(); i++) {
			dataPoints[i] = new DataPoint(i, filteredWorkoutJSONS.get(i).getReps());
		}
		LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
		graphView.addSeries(series);
		graphView.invalidate();
	}

	public void durationView(String workoutStr, ArrayList<WorkoutJSON> workoutJSONS) {
		ArrayList<WorkoutJSON> filteredWorkoutJSONS = new ArrayList<>();
		for (WorkoutJSON workout : workoutJSONS) {
			if (workout.getWorkoutName().equals(workoutStr)) {
				filteredWorkoutJSONS.add(workout);
			}
		}
		Collections.sort(filteredWorkoutJSONS, workoutJSONComparator);
		DataPoint[] dataPoints = new DataPoint[filteredWorkoutJSONS.size()];
		for (int i = 0; i < filteredWorkoutJSONS.size(); i++) {
			dataPoints[i] = new DataPoint(i, filteredWorkoutJSONS.get(i).getDuration());
		}
		LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
		graphView.addSeries(series);
	}

	public void scoreView(String workoutStr, ArrayList<WorkoutJSON> workoutJSONS) {
		ArrayList<WorkoutJSON> filteredWorkoutJSONS = new ArrayList<>();
		for (WorkoutJSON workout : workoutJSONS) {
			if (workout.getWorkoutName().equals(workoutStr)) {
				filteredWorkoutJSONS.add(workout);
			}
		}
		Collections.sort(filteredWorkoutJSONS, workoutJSONComparator);
		DataPoint[] dataPoints = new DataPoint[filteredWorkoutJSONS.size()];
		for (int i = 0; i < filteredWorkoutJSONS.size(); i++) {
			dataPoints[i] = new DataPoint(i, filteredWorkoutJSONS.get(i).getAccuracy());
		}
		LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
		graphView.addSeries(series);
	}
}
