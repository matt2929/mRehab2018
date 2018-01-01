package com.example.matt2929.strokeappdec2017.SaveAndLoadData;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Created by matt2929 on 12/19/17.
 */

public class SaveHistoricalReps {
    Context context;
    String username;
    HashMap<String, Integer> workouts = new HashMap<>();

    public SaveHistoricalReps(Context context, String username) {
        this.context = context;
        this.username = username;
        fillHashMap();
    }

    private void fillHashMap() {
        List<File> files = getAllFiles(context.getFilesDir());
        ArrayList<String> goals = new ArrayList<>();
        for (File f : files) {
            if (f.getName().contains("UserReps_") && f.getName().contains("_" + username + "_")) {
                User user = new User();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(f));
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        workouts.put(line.split(":")[0], Integer.valueOf((line.split(":")[1])));
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

    public Integer getWorkoutReps(String workoutName) {
        if (workouts.containsKey(workoutName)) {
            return workouts.get(workoutName);
        } else {
            return 10;
        }
    }

    private String loadFile(String filename) {
        List<File> files = getAllFiles(context.getFilesDir());
        String output = "";
        for (File f : files) {
            if (f.getName().equals(filename)) {
                User user = new User();
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

    public void updateWorkout(String workoutName, Integer reps) {
        String filename = "UserReps_" + username + "_" + ".txt";
        FileOutputStream outputStream;
        workouts.put(workoutName, reps);
        String output = "";
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            Iterator it = workouts.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
                output += pair.getKey() + "," + String.valueOf(pair.getValue()) + "\n";
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
            File file = files.remove();
            if (file.isDirectory()) {
                files.addAll(Arrays.asList(file.listFiles()));
            } else if (file.getName().contains("USER")) {
                inFiles.add(file);
            }
        }
        return inFiles;
    }
}


