package com.example.matt2929.strokeappdec2017.SaveAndLoadData;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Created by matt2929 on 1/22/18.
 */

public class SaveActivitiesDoneToday {

	final String LAST_TIME = "lasttime";
	Context context;
	HashMap<String, Long> workouts = new HashMap<>();

	public SaveActivitiesDoneToday(Context context) {
		this.context = context;
		fillHashMap();
		if (workouts.containsKey(LAST_TIME)) {
			Calendar lastTime = Calendar.getInstance();
			lastTime.setTimeInMillis(workouts.get(LAST_TIME));
			Calendar midnight = Calendar.getInstance();
			midnight.setTimeInMillis(lastTime.getTimeInMillis());
			midnight.set(lastTime.get(Calendar.YEAR), lastTime.get(Calendar.MONTH), lastTime.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
			if (midnight.getTimeInMillis() - System.currentTimeMillis() <= 0) {
				workouts.clear();
			}
		}
	}

	private void fillHashMap() {
		List<File> files = getAllFiles(context.getFilesDir());
		for (File f : files) {
			if (f.getName().contains("UserActivities_") && f.getName().contains(WorkoutData.UserName)) {
				try {
					BufferedReader br = new BufferedReader(new FileReader(f));
					String line = "";
					while ((line = br.readLine()) != null) {
						Log.e(f.getName(), line);
						workouts.put(line.split(":")[0], Long.valueOf((line.split(":")[1])));
					}
					br.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
				}
				break;
			}
		}
	}

	public Integer getWorkoutActivityCount(String workoutName) {
		if (workouts.containsKey(workoutName)) {
			return Math.round(workouts.get(workoutName));
		} else {
			return 0;
		}
	}


	public void updateWorkout(String workoutName) {
		String filename = "UserActivities_" + WorkoutData.UserName + ".txt";
		FileOutputStream outputStream;
		File file = new File(filename);
		workouts.put(LAST_TIME, System.currentTimeMillis());
		if (workouts.containsKey(workoutName)) {
			workouts.put(workoutName, workouts.get(workoutName) + 1);
		} else {
			workouts.put(workoutName, Long.valueOf(1));
		}
		String output = "";
		try {
			outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
			Iterator it = workouts.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				output += pair.getKey() + ":" + String.valueOf(pair.getValue()) + "\n";
			}
			outputStream.write(output.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(context, "Failure to save", Toast.LENGTH_SHORT).show();
		}
	}

	private List<File> getAllFiles(File parentDir) {
		List<File> inFiles = new ArrayList<>();
		Queue<File> files = new LinkedList<>();
		files.addAll(Arrays.asList(parentDir.listFiles()));
		while (!files.isEmpty()) {
			Log.e("File", files.peek().getName());
			File file = files.remove();
			if (file.isDirectory()) {
				files.addAll(Arrays.asList(file.listFiles()));
			} else if (file.getName().contains("UserActivities_")) {
				inFiles.add(file);
			}
		}
		return inFiles;
	}
}



