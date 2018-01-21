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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * Created by matt2929 on 1/18/18.
 *
 */

public class SaveCalendarDateJSON {
	Context context;
	CalendarDateJSON calendarDateJSON;


	public SaveCalendarDateJSON(Context context) {
		this.context = context;
		getData();
	}

	public int getYear() {
		return calendarDateJSON.year;
	}

	public int getDateOfMonth() {
		return calendarDateJSON.dayOfMonth;
	}

	public int getMonth() {
		return calendarDateJSON.month;
	}

	public void getData() {
		List<File> files = getAllFiles(context.getFilesDir());
		String output = "";
		for (File f : files) {
			if (f.getName().equals("Calendar_" + WorkoutData.UserName + ".json")) {
				try {
					BufferedReader br = new BufferedReader(new FileReader(f));
					String line = "";
					while ((line = br.readLine()) != null) {
						Log.e("line", line);
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
		calendarDateJSON = new CalendarDateJSON(output);
		Log.e("dom", "" + calendarDateJSON.dayOfMonth);
	}

	public void addCalendarDate(int dom, int month, int year) {
		String filename = "Calendar_" + WorkoutData.UserName + ".json";
		FileOutputStream outputStream;
		calendarDateJSON = new CalendarDateJSON(month, dom, year);
		try {
			outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write(calendarDateJSON.getJSONString().getBytes());
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
			} else if (file.getName().contains("Calendar")) {
				inFiles.add(file);
			}
		}
		return inFiles;
	}

	public String getString() {
		return calendarDateJSON.getJSONString();
	}
}
