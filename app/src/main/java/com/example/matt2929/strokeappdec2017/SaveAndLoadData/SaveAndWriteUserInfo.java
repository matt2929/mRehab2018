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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by matt2929 on 12/18/17.
 */

public class SaveAndWriteUserInfo {
    Context context;

    public SaveAndWriteUserInfo(Context context) {
        this.context=context;
    }

	public boolean isUserCreated() {
		List<File> files = getAllFiles(context.getFilesDir());
		return (files.size() > 0);
	}

	public User getUser() {
		List<File> files = getAllFiles(context.getFilesDir());
		for (File f : files) {
			User user = new User();
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line="";
                while ((line = br.readLine()) != null){
                    if(line.contains("Name")){
                        user.setName(line.split(":")[1]);
                    }else if(line.contains("Age")){
                        user.setAge(Integer.valueOf(line.split(":")[1]));
                    }else if(line.contains("Affected")){
                        user.setHand(Integer.valueOf(line.split(":")[1]));
                    }else if(line.contains("Goals")){
                        user.setGoals(line.split(":")[1]);
                    }else{

                    }
	                return user;
                }
                br.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e){

            }
        }
		return null;
	}
    public void saveUser(User newUser){
        String filename = "USER_Full_" + newUser.getName() + "_" + newUser.getHand() + ".txt";
        String string = "Name:"+newUser.getName();
        string += ("\nAge:"+newUser.getAge());
        string += ("\nAffected:"+newUser.getHand());
        string += ("\nGoals:"+newUser.getGoals());
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"Failure to save",Toast.LENGTH_SHORT).show();
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
            } else if (file.getName().contains("USER") && file.getName().contains("Full")) {
                inFiles.add(file);
            }
        }
        return inFiles;
    }
}
