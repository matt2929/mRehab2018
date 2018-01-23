package com.example.matt2929.strokeappdec2017.Workouts;

import android.graphics.Color;
import android.view.View;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.EndRepTrigger;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.Utilities.SFXPlayer;
import com.example.matt2929.strokeappdec2017.WorkoutsView.CircleView;

import java.util.ArrayList;

/**
 * Created by matt2929 on 1/6/18.
 */

public class WO_QuickTouch extends TouchWorkoutAbstract {
	float[] angle = new float[0];
	boolean newLevelBreak = false;
	ArrayList<Integer> touched = new ArrayList<>();
	boolean canTouch = false;
	Long coolDown = System.currentTimeMillis();
	boolean inCooldown = false;
	int completed = 0;
	int level = 0;
	int incorrect = 0;
	public WO_QuickTouch(String Name, Integer reps, ArrayList<View> views, EndRepTrigger endRepTrigger, SpeechTrigger speechTrigger, SFXPlayer sfxPlayer, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
		super.TouchWorkout(Name, reps, views, endRepTrigger, speechTrigger, sfxPlayer, outputWorkoutData, outputWorkoutStrings);
		angle = new float[views.size()];
		float angleSeperation = 360f / views.size();
		for (int i = 0; i < views.size(); i++) {
			angle[i] = angleSeperation * (float) i;

		}
	}

	@Override
	public boolean TouchIn(float x, float y) {
		if (WorkoutInProgress && !inCooldown) {
			for (View v : views) {
				int[] coor = new int[2];
				v.getLocationInWindow(coor);
				if (coor[0] <= x && coor[0] + v.getWidth() > x) {
					if (coor[1] <= y && coor[1] + v.getHeight() > y) {
						CircleView circleView = (CircleView) v;
						circleView.setColor(Color.GREEN);
						circleView.invalidate();
						if (!touched.contains(v.getId())) {
							touched.add(v.getId());
						}
						if (checkDone()) {
							completed++;
							endRepTrigger.endRep();
							if (completed == reps) {
								workoutComplete = true;
							} else {
								canTouch = false;
								inCooldown = true;
								speechTrigger.speak("Rep " + completed + " completed. Next rep starting in 5 Seconds.");
								coolDown = System.currentTimeMillis();
							}

						}
						return true;
					}
				}
			}
		}
		incorrect++;
		return false;
	}

	@Override
	public void Update() {
		super.Update();
		if (WorkoutInProgress) {
			if (!inCooldown) {
				if (angle.length == views.size()) {
					for (int i = 0; i < angle.length; i++) {
						angle[i] = angle[i] + 1f;
						if (angle[i] >= 360f) {
							angle[i] = 0;
						}
					}

				}
				if (level == 0) {
					levelOne();
				} else if (level == 1) {
					levelTwo();
				} else {
					levelThree();
				}
			} else {
				if (Math.abs(coolDown - System.currentTimeMillis()) > 5000) {
					speechTrigger.speak("Start");
					canTouch = true;
					inCooldown = false;
					newLevel();

				}
			}
		}
	}

	public void levelOne() {
		for (int i = 0; i < views.size(); i++) {
			views.get(i).setX(angleToX(angle[i], 400) - views.get(i).getWidth() / 2 + 540);
			views.get(i).setY(angleToY(angle[i], 400) - views.get(i).getHeight() / 2 + 810);
		}
	}

	public void levelTwo() {
		float seperation = views.get(0).getRootView().getHeight() / (views.size() + 1);
		for (int i = 0; i < views.size(); i++) {
			views.get(i).setX(angleToX(angle[i], 400) - views.get(i).getWidth() / 2 + 540);
			views.get(i).setY((seperation * (i + 1)) - views.get(i).getHeight() / 2);
		}
	}

	public void levelThree() {
		float seperation = views.get(0).getRootView().getWidth() / (views.size() + 1);
		for (int i = 0; i < views.size(); i++) {
			views.get(i).setX((seperation * (i + 1)) - views.get(i).getWidth() / 2);
			views.get(i).setY(angleToY(angle[i], 400) - views.get(i).getHeight() / 2 + 810);
		}
	}

	public void newLevel() {
		touched.clear();
		for (View v : views) {
			CircleView circleView = (CircleView) v;
			circleView.setColor(Color.BLUE);
			circleView.invalidate();
		}
		level++;
		if (level == 3) {
			level = 0;
		}
	}

	@Override
	public WorkoutScore getScore() {
		return new WorkoutScore("Accuracy", ((((float) reps * 6f) - (float) incorrect)) / ((float) reps * 6f) * 100f);

	}

	@Override
	public void StartWorkout() {
		super.StartWorkout();
		canTouch = true;
	}

	public boolean checkDone() {
		return (touched.size() == views.size());

	}

	public float angleToX(float f, int radius) {
		return (float) Math.cos((f / 180 * Math.PI)) * radius;
	}

	public float angleToY(float f, int radius) {
		return (float) Math.sin((f / 180 * Math.PI)) * radius;
	}
}
