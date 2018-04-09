package com.example.matt2929.strokeappdec2017.WorkoutsView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.Utilities.WorkoutSelectData;

import java.util.ArrayList;

/**
 * Created by matt2929 on 1/18/18.
 */

public class WorkoutSelectAdapter extends ArrayAdapter<WorkoutSelectData> {

	float textSize = 25;
	int minHeight = 200, padding = 15;


	public WorkoutSelectAdapter(Context context, ArrayList<WorkoutSelectData> users) {
		super(context, 0, users);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		WorkoutSelectData workoutSelectData = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item, parent, false);
		}
		convertView.setMinimumHeight(minHeight);
		convertView.setPadding(0, padding, 0, padding);
		TextView activityName = convertView.findViewById(R.id.rowItemActivityName);
		TextView activityCount = convertView.findViewById(R.id.rowItemActivityComplete);
		ImageView imageView = convertView.findViewById(R.id.iconView);
		imageView.setImageResource(workoutSelectData.getResID());
		activityName.setText(workoutSelectData.getWorkoutName());
		activityName.setTextColor(Color.BLACK);
		activityName.setBackgroundColor(workoutSelectData.getColor());
		activityCount.setTextColor(Color.BLACK);
		activityCount.setText("" + workoutSelectData.getActivityCount() + "/3");
		activityCount.setTextSize(textSize);
		activityName.setTextSize(textSize);
		activityName.setShadowLayer(5, 5, 5, Color.LTGRAY);
		return convertView;
	}
}
