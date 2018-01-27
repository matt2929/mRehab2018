package com.example.matt2929.strokeappdec2017.SaveAndLoadData;

import android.content.Context;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by matt2929 on 12/19/17.
 */

public class SaveHistoricalGoals {
	Context context;

	public SaveHistoricalGoals(Context context) {
		this.context = context;
	}

	public ArrayList<String> getGoals() {
		List<File> files = getAllFiles(context.getFilesDir());
		ArrayList<String> goals = new ArrayList<>();
		for (File f : files) {
			if (f.getName().contains("UserGoals_") && f.getName().contains("_" + WorkoutData.UserName + "_")) {
				try {
					BufferedReader br = new BufferedReader(new FileReader(f));
					String line = "";
					while ((line = br.readLine()) != null) {
						if (line.contains("Goal")) {
							goals.add(line.split(":")[1]);
						}
					}
					br.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
				}
				break;
			}

		}
		return goals;
	}

	private String loadFile(String filename) {
		List<File> files = getAllFiles(context.getFilesDir());
		String output = "";
		for (File f : files) {
			if (f.getName().equals(filename)) {
				try {
					BufferedReader br = new BufferedReader(new FileReader(f));
					String line = "";
					while ((line = br.readLine()) != null) {
						output += line;
					}
					br.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
				}
				break;
			}

		}
		return output;
	}

	public void saveGoals(String goal) {
		String filename = "UserGoals_" + WorkoutData.UserName + "_" + ".txt";
		FileOutputStream outputStream;

		try {
			outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write((loadFile(filename) + "\nGoal:" + goal).getBytes());
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
			File file = files.remove();
			if (file.isDirectory()) {
				files.addAll(Arrays.asList(file.listFiles()));
			} else if (file.getName().contains("UserGoals")) {
				inFiles.add(file);
			}
		}
		return inFiles;
	}
}
