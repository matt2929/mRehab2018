package com.example.matt2929.strokeappdec2017.SaveAndLoadData;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.matt2929.strokeappdec2017.AmazonCloud.UploadToAmazonBucket;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SaveTouchAndSensor extends AsyncTask<Void, Void, Void> {


	public static String _fileName;
	Context _context;
	String _workoutName = "";
	ArrayList<float[]> dataQueue = new ArrayList<float[]>();
	String heading;
	UploadToAmazonBucket uploadToAmazonBucket;
	Calendar cal;

	public SaveTouchAndSensor(Context context, String workoutName, String heading) {
		_workoutName = workoutName;
		_context = context;
		cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int year = cal.get(Calendar.YEAR);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		this.heading = heading;
		_fileName = WorkoutData.UserName + "_" + workoutName + "_" + (month + 1) + "-" + day + "-" + year + "_[" + hour + "h~" + minute + "m~" + second + "s].csv";
	}

	public void saveData(float[] data) {
		dataQueue.add(data);
		uploadToAmazonBucket = new UploadToAmazonBucket(_context);
	}

	@Override
	protected Void doInBackground(Void... voids) {
		try {
			File fileParent = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "RehabApplication");
			if (!fileParent.exists()) {
				fileParent.mkdir();
			}
			File file = new File(fileParent, _fileName);
			PrintWriter writer;
			if (file.exists()) {
				file.delete();

			}
			try {
				file.createNewFile();
				writer = new PrintWriter(new FileWriter(file, true));
				writer.append(WorkoutData.UserName + _workoutName + " " + humanReadableTime(cal.getTimeInMillis()) + "\n" + heading + "\n");
				for (int i = 0; i < dataQueue.size(); i++) {
					for (int j = 0; j < dataQueue.get(i).length; j++) {
						if (j == dataQueue.get(i).length - 1) {
							writer.append(dataQueue.get(i)[j] + "\n");
						} else {
							writer.append(dataQueue.get(i)[j] + ",");
						}
						Log.e("Progress Asynch", "" + WorkoutData.progressLocal);
						WorkoutData.progressLocal = (((float) i) / ((float) dataQueue.size() - 1)) * 100f;
					}
				}
				writer.close();
				uploadToAmazonBucket.saveData(file);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String humanReadableTime(long time) {
		long yourmilliseconds = time;
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
		Date resultdate = new Date(yourmilliseconds);
		return sdf.format(resultdate);
	}
}

