package com.example.matt2929.strokeappdec2017.Workouts;

import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.EndRepTrigger;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.Utilities.SFXPlayer;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by matt2929 on 12/21/17.
 */

public class WO_PhoneNumber extends TouchWorkoutAbstract {

	boolean canTouch = false;
	Long coolDown = System.currentTimeMillis();
	boolean inCooldown = false;
	int completed = 0;
	ArrayList<Button> buttons = new ArrayList<>();
	TextView whatToType, whatTyped;
	int[] phoneNumber = new int[10];
	boolean[] phoneNumberProg = new boolean[10];
	Long delayTime = 4000l;
	int incorrect = 0;

	public WO_PhoneNumber(String Name, Integer reps, ArrayList<View> views, EndRepTrigger endRepTrigger, SpeechTrigger speechTrigger, SFXPlayer sfxPlayer, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
		super.TouchWorkout(Name, reps, views, endRepTrigger, speechTrigger, sfxPlayer, outputWorkoutData, outputWorkoutStrings);
		for (int i = 2; i < views.size(); i++) {
			buttons.add((Button) views.get(i));
		}
		whatToType = (TextView) views.get(0);
		whatTyped = (TextView) views.get(1);
		newLevel();
		for (Button b : buttons) {
			b.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					phoneNumberButton((Button) view);
				}
			});
		}
	}

	@Override
	public boolean TouchIn(float x, float y) {
		for (Button button : buttons) {
			if (x >= button.getLeft() && x < button.getRight()) {
				if (y > button.getTop() && y < button.getBottom()) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public void Update() {
		super.Update();
		if (inCooldown) {
			if (Math.abs(coolDown - System.currentTimeMillis()) > delayTime) {
				inCooldown = false;
				newLevel();
			} else {
				whatToType.setText("Cool Down");
				whatTyped.setText("" + (((delayTime - (Math.abs(coolDown - System.currentTimeMillis()))) / 1000) + 1));
			}
		}
	}

	public void phoneNumberButton(Button b) {
		if (!inCooldown && canTouch) {
			String numS = b.getText().toString();
			int number = Integer.valueOf((numS));
			for (int i = 0; i < phoneNumberProg.length; i++) {
				if (phoneNumberProg[i] == false) {
					if (phoneNumber[i] == number) {
						incorrect++;
						phoneNumberProg[i] = true;
						quickChange(b, Color.GREEN);
						whatTyped.setText(whatTyped.getText().toString() + numS);
						if (i == 9) {
							completed++;
							endRepTrigger.endRep();
							if (completed == reps) {
								workoutComplete = true;
							} else {
								coolDown();
							}
						}
						break;
					} else {
						quickChange(b, Color.RED);
						break;
					}
				}
			}
		}
	}

	public void quickChange(final Button button, final Integer color) {
		Handler handler = new Handler();
		button.setBackgroundColor(color);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				button.setBackground(button.getContext().getResources().getDrawable(android.R.drawable.btn_default));
			}
		}, 100);
	}

	private void coolDown() {
		inCooldown = true;
		coolDown = System.currentTimeMillis();
		speechTrigger.speak("phone number complete, starting rep " + (completed + 1) + ". ");
	}

	public void newLevel() {
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			phoneNumber[i] = random.nextInt(10);
			phoneNumberProg[i] = false;
		}
		String str = "";
		for (int i = 0; i < 10; i++) {
			str += phoneNumber[i];
		}
		whatToType.setText(str);
		whatTyped.setText("");
	}

	@Override
	public void StartWorkout() {
		super.StartWorkout();
		canTouch = true;
	}

	@Override
	public WorkoutScore getScore() {
		return new WorkoutScore("Accuracy", ((((float) reps * 10f) - (float) incorrect)) / ((float) reps * 10f) * 100f);
	}

	public boolean checkDone() {
		return workoutComplete;
	}

	public float angleToX(float f, int radius) {
		return (float) Math.cos((f / 180 * Math.PI)) * radius;
	}

	public float angleToY(float f, int radius) {
		return (float) Math.sin((f / 180 * Math.PI)) * radius;
	}
}
