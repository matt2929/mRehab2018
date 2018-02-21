package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechInitListener;
import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveWorkoutJSON;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.WorkoutJSON;
import com.example.matt2929.strokeappdec2017.Utilities.SFXPlayer;
import com.example.matt2929.strokeappdec2017.Utilities.Text2Speech;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PostWorkoutReportActivity extends AppCompatActivity {
	private final int CHECK_CODE = 0x1;
	ArrayList<WorkoutJSON> workoutJSONS;
	SaveWorkoutJSON saveWorkoutJSON;
	ImageButton imageButton;
	SFXPlayer sfxPlayer;
	boolean betterReps = false;
	boolean betterSmoothness = false;
	boolean betterTime = false;
	WorkoutJSON thisWorkout;
	TextView currentRep, lastRep, currentTime, lastTime, currentSmooth, lastSmooth;
	ImageView smoothView, timeView, repView;
	private Text2Speech _Text2Speech;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_workout_report);
		currentRep = (TextView) findViewById(R.id.currentRep);
		lastRep = (TextView) findViewById(R.id.lastRep);
		currentTime = (TextView) findViewById(R.id.currentTime);
		lastTime = (TextView) findViewById(R.id.lastTime);
		currentSmooth = (TextView) findViewById(R.id.currentSmoothness);
		lastSmooth = (TextView) findViewById(R.id.lastSmoothness);
		smoothView = (ImageView) findViewById(R.id.shakeImage);
		timeView = (ImageView) findViewById(R.id.timeImage);
		repView = (ImageView) findViewById(R.id.repImage);
		sfxPlayer = new SFXPlayer(getApplicationContext());
		sfxPlayer.loadSFX(R.raw.tada);
		Button button = (Button) findViewById(R.id.continueButton);
		ImageButton imageButton = (ImageButton) findViewById(R.id.homeButton);
		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), WorkoutOrHistoryOrCalendarActivity.class);
				startActivity(intent);
			}
		});
		saveWorkoutJSON = new SaveWorkoutJSON(getApplicationContext());
		workoutJSONS = saveWorkoutJSON.getWorkouts();
		ArrayList<WorkoutJSON> workoutJSONSFiltered = new ArrayList<>();
		Comparator<WorkoutJSON> workoutJSONComparator = new Comparator<WorkoutJSON>() {
			@Override
			public int compare(WorkoutJSON t0, WorkoutJSON t1) {
				return (int) (t1.getCalendar().getTimeInMillis() - t0.getCalendar().getTimeInMillis());
			}
		};

		for (WorkoutJSON workoutJSON : workoutJSONS) {
			if (workoutJSON.getWorkoutName().equals(getIntent().getStringExtra("Workout"))) {
				workoutJSONSFiltered.add(workoutJSON);
			}
		}
		Collections.sort(workoutJSONSFiltered, workoutJSONComparator);
		thisWorkout = workoutJSONSFiltered.get(0);
		betterReps = false;
		betterSmoothness = false;
		betterTime = false;
		currentSmooth.setText("" + floatToHundreth(thisWorkout.getAccuracy()));
		currentRep.setText("" + floatToHundreth(thisWorkout.getReps()));
		currentTime.setText("" + floatToHundreth(thisWorkout.getDuration()));
		if (workoutJSONSFiltered.size() >= 2) {
			WorkoutJSON previousWorkout = workoutJSONSFiltered.get(1);
			//-----
			betterReps = (previousWorkout.getReps() <= thisWorkout.getReps());
			betterSmoothness = (previousWorkout.getAccuracy() >= thisWorkout.getAccuracy());
			betterTime = (previousWorkout.getDuration() >= thisWorkout.getDuration());
			//-----
			lastSmooth.setText("" + floatToHundreth(previousWorkout.getAccuracy()));
			lastRep.setText("" + floatToHundreth(previousWorkout.getReps()));
			lastTime.setText("" + floatToHundreth(previousWorkout.getDuration()));
			//-----
			if (betterReps) {
				blinkView(repView);
				repView.setBackgroundColor(Color.GREEN);
			}
			if (betterSmoothness) {
				blinkView(smoothView);
				smoothView.setBackgroundColor(Color.GREEN);

			}
			if (betterTime) {
				blinkView(timeView);
				timeView.setBackgroundColor(Color.GREEN);
			}
		} else {
			lastSmooth.setText("" + -1);
			lastRep.setText("" + -1);
			lastTime.setText("" + -1);

		}
		checkTTS();

		if (betterReps || betterSmoothness || betterTime) {
			sfxPlayer.playSFX();
		}

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//TODO:if within some data, DO YOU WANT TO ADD A NEW GOAL?
				Intent intent = new Intent(getApplicationContext(), WorkoutSelectionActivity.class);
				startActivity(intent);
			}
		});

	}

	private void checkTTS() {
		Intent check = new Intent();
		check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(check, CHECK_CODE);
	}

	//use this to know when tts is done speaking
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				_Text2Speech = new Text2Speech(this);
				_Text2Speech.addInitListener(new SpeechInitListener() {
					@Override
					public void onInit() {
						_Text2Speech.speak("Number of Reps " + floatToHundreth(thisWorkout.getReps()), WorkoutData.TTS_WORKOUT_DESCRIPTION);
						_Text2Speech.silence(1000);
						_Text2Speech.speak("Smoothness " + floatToHundreth(thisWorkout.getAccuracy()), WorkoutData.TTS_WORKOUT_DESCRIPTION);
						_Text2Speech.silence(1000);
						_Text2Speech.speak("Duration " + floatToHundreth(thisWorkout.getDuration()), WorkoutData.TTS_WORKOUT_DESCRIPTION);
					}
				});

			} else {
				Intent install = new Intent();
				install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(install);
			}

		}
	}

	private void blinkView(final View view) {
		Handler handler = new Handler();
		int delay = 750;
		for (int i = 0; i < 8; i++) {
			if (i % 2 != 1) {
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						view.setBackgroundColor(Color.TRANSPARENT);
					}
				}, delay);
			} else {
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						view.setBackgroundColor(Color.GREEN);
					}
				}, delay);
			}
			delay += 100;
		}

	}

	private String floatToHundreth(float value) {
		return new DecimalFormat("#.##").format(value);
	}
}

