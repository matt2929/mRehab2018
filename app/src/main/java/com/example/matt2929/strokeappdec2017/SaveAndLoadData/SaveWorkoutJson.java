package com.example.matt2929.strokeappdec2017.SaveAndLoadData;

import android.content.Context;
import android.os.Environment;

import com.example.matt2929.strokeappdec2017.AmazonCloud.UploadToAmazonBucket;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by matt2929 on 1/3/18.
 */

public class SaveWorkoutJson {
	ArrayList<WorkoutJSON> workoutJSONS = new ArrayList<>();
	Context context;
	UploadToAmazonBucket uploadToAmazonBucket;

	public SaveWorkoutJson(Context context) {
		this.context = context;
		uploadToAmazonBucket = new UploadToAmazonBucket(context);
	}


	public ArrayList<WorkoutJSON> getWorkouts() {
		File fileParent = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "RehabApplicationAllWorkouts");
		ArrayList<File> Files = new ArrayList<>(getAllFiles(fileParent));
		workoutJSONS.clear();
		for (File f : Files) {
			BufferedReader br = null;
			String fileText = "";
			try {
				br = new BufferedReader(new FileReader(f));
				String line = "";
				while ((line = br.readLine()) != null) {
					fileText += line;
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			workoutJSONS.add(new WorkoutJSON(fileText));
		}
		return workoutJSONS;
	}


	public void addNewWorkout(String WorkoutName, String Hand, Long Duration, Long Accuracy, int Reps) {
		Calendar currentCalendar = Calendar.getInstance();
		String fileName = WorkoutData.UserName + "_" + WorkoutName + "_" + currentCalendar.get(Calendar.YEAR) + "~" + currentCalendar.get(Calendar.MONTH) + "~" + currentCalendar.get(Calendar.DAY_OF_MONTH) + "_[" + currentCalendar.get(Calendar.HOUR_OF_DAY) + "h~" + currentCalendar.get(Calendar.MINUTE) + "m" + currentCalendar.get(Calendar.SECOND) + "].json";
		String output = "";
		WorkoutJSON newWorkoutJSON = new WorkoutJSON(WorkoutData.UserName, WorkoutName, Reps, Duration, Accuracy, Hand);
		output = newWorkoutJSON.getJSONString();
		File fileParent = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "RehabApplicationAllWorkouts");
		if (!fileParent.exists()) {
			fileParent.mkdir();
		}
		File file = new File(fileParent, fileName);
		PrintWriter writer;
		if (file.exists()) {
			file.delete();

		}
		try {
			file.createNewFile();
			writer = new PrintWriter(new FileWriter(file, true));
			writer.append(output);
			writer.close();
			uploadToAmazonBucket.saveData(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<File> getAllFiles(File parentDir) {

		List<File> inFiles = new ArrayList<>();
		Queue<File> files = new LinkedList<>();
		files.addAll(Arrays.asList(parentDir.listFiles()));
		while (!files.isEmpty()) {
			File file = files.remove();
			if (file.isDirectory()) {
				files.addAll(Arrays.asList(file.listFiles()));
			} else if (file.getName().contains(WorkoutData.UserName)) {
				inFiles.add(file);
			}
		}

		return inFiles;
	}
}


