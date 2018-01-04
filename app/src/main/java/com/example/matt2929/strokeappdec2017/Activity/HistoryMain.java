package com.example.matt2929.strokeappdec2017.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveWorkoutData;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.WorkoutJSON;

import java.util.ArrayList;

public class HistoryMain extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_main);
		TextView textView = (TextView) findViewById(R.id.jsonData);
		SaveWorkoutData saveWorkoutData = new SaveWorkoutData(getApplicationContext());
		ArrayList<WorkoutJSON> workoutJSONS = saveWorkoutData.getWorkouts();
		String out = "";
		for (WorkoutJSON workoutJSON : workoutJSONS) {
			out += workoutJSON.getJSONString() + "\n";
		}
		textView.setText(out);
	}
}
