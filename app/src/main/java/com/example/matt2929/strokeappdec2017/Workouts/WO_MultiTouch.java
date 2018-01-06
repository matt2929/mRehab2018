package com.example.matt2929.strokeappdec2017.Workouts;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.Utilities.SFXPlayer;

import java.util.ArrayList;

/**
 * Created by matt2929 on 1/6/18.
 */

public class WO_MultiTouch extends TouchWorkoutAbstract {
	float[] angle = new float[0];


	public WO_MultiTouch(String Name, Integer reps, ArrayList<View> views, SpeechTrigger speechTrigger, SFXPlayer sfxPlayer, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
		super.TouchWorkout(Name, reps, views, speechTrigger, sfxPlayer, outputWorkoutData, outputWorkoutStrings);
		angle = new float[views.size()];
		float angleSeperation = 360f / views.size();
		for (int i = 0; i < views.size(); i++) {
			angle[i] = angleSeperation * (float) i;

		}
	}

	@Override
	public void TouchIn(float x, float y) {
		super.TouchIn(x, y);
		for (View v : views) {
			if (v.getX() <= x && v.getX() + v.getWidth() > x) {
				if (v.getY() <= y && v.getY() + v.getHeight() > y) {
					v.setBackgroundColor(Color.BLACK);
				}
			}
		}
	}

	@Override
	public void Update() {
		super.Update();
		if (angle.length == views.size()) {
			for (int i = 0; i < angle.length; i++) {
				angle[i] = angle[i] + 1f;
				if (angle[i] >= 360f) {
					angle[i] = 0;
				}
			}
			for (int i = 0; i < views.size(); i++) {

				views.get(i).setX(angleToX(angle[i], 400) - views.get(i).getWidth() / 2 + 540);
				views.get(i).setY(angleToY(angle[i], 400) - views.get(i).getHeight() / 2 + 810);
				Log.e("Coor " + i, "" + views.get(i).getRootView().getWidth());

			}
		}
	}

	public float angleToX(float f, int radius) {
		return (float) Math.cos((f / 180 * Math.PI)) * radius;
	}

	public float angleToY(float f, int radius) {
		return (float) Math.sin((f / 180 * Math.PI)) * radius;
	}
}
