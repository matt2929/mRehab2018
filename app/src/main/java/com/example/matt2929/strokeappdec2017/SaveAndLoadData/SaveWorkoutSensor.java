package com.example.matt2929.strokeappdec2017.SaveAndLoadData;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

public class SaveWorkoutSensor extends AsyncTask<Void, Void, Void> {


    public static String _fileName;
    Context _context;
    String _name = "";
    ArrayList<float[]> dataQueue = new ArrayList<float[]>();
    String heading;

    public SaveWorkoutSensor(Context context, String name, String heading) {
        _name = name;
        _context = context;
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        this.heading = heading;
        _fileName = WorkoutData.UserName + "_" + name + "_" + (month + 1) + "-" + day + "-" + year + "_[" + hour + "h~" + minute + "m~" + second + "s].csv";
    }

    public void saveData(float[] data) {
        dataQueue.add(data);
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
                writer.print(_name + heading);
                writer.append('\n');
                for (int i = 0; i < dataQueue.size(); i++) {
                    for (int j = 0; j < dataQueue.get(i).length; j++) {
                        if (j == dataQueue.size() - 1) {
                            writer.append(dataQueue.get(i)[j] + "\n");
                        } else {
                            writer.append(dataQueue.get(i)[j] + ",");
                        }
                        WorkoutData.progress = (((float) i) / ((float) dataQueue.size() - 1)) * 100f;
                    }
                }
                writer.close();
            } catch (IOException e) {
                Toast.makeText(_context, "Error Saving", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        } catch (Exception e) {
            Toast.makeText(_context, "Error Saving", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }
}

