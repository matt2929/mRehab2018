package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechCompleteListener;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechInitListener;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveHistoricalReps;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveWorkoutData;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveWorkoutSensor;
import com.example.matt2929.strokeappdec2017.Utilities.SFXPlayer;
import com.example.matt2929.strokeappdec2017.Utilities.Text2Speech;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;
import com.example.matt2929.strokeappdec2017.Workouts.TouchWorkoutAbstract;
import com.example.matt2929.strokeappdec2017.Workouts.WO_MultiTouch;
import com.example.matt2929.strokeappdec2017.Workouts.WO_PhoneNumber;
import com.example.matt2929.strokeappdec2017.Workouts.WorkoutDescription;

import java.util.ArrayList;

public class TouchWorkoutRunner extends AppCompatActivity {

	private final int CHECK_CODE = 0x1;
	//~~~~~~~~~~~~~~~~~~~~~~~
	Long TimeOfWorkout = System.currentTimeMillis();
	//Workout Attributes~~~
	String _WorkoutHand; //Which Hand
	String _WorkoutName; //Name of Current Wokrout
	Integer _WorkoutReps;//Number of Repetitions
	//Refrence ID for TTS~~~~
	private Text2Speech _Text2Speech;
	private TouchWorkoutAbstract _CurrentWorkout;
	private View _CurrentWorkoutView;
	private WorkoutDescription _WorkoutDescription = null;
	private SFXPlayer _SFXPlayer;
	private SaveHistoricalReps _SaveHistoricalReps;
	private SaveWorkoutSensor _SaveWorkoutSensor;
	private SaveWorkoutData _SaveWorkoutData;
	private Boolean _WorkoutInProgress = false;//Is workout currently running?
//

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		_WorkoutHand = intent.getStringExtra("Hand");
		_WorkoutName = intent.getStringExtra("Workout");
		_WorkoutReps = intent.getIntExtra("Reps", 10);
		_SaveHistoricalReps = new SaveHistoricalReps(getApplicationContext(), WorkoutData.UserName);
		_SaveWorkoutSensor = new SaveWorkoutSensor(getApplicationContext(), WorkoutData.UserName, "Time,X,Y");
		_SaveWorkoutData = new SaveWorkoutData(getApplicationContext());
		_SFXPlayer = new SFXPlayer(getApplicationContext());
		SetupWorkout(_WorkoutName, _WorkoutReps);
		checkTTS();

	}

	private void checkTTS() {
		Intent check = new Intent();
		check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(check, CHECK_CODE);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (_Text2Speech != null) {
			_Text2Speech.destroy();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (_Text2Speech != null) {
			_Text2Speech.destroy();
		}
	}


	//use this to know when tts is done speaking
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				Log.e("got here", resultCode + "");
				_Text2Speech = new Text2Speech(this);
				_Text2Speech.addSpeechCompleteListener(new SpeechCompleteListener() {
					@Override
					public void Spoke(String s) {
						Log.e("TTS: ", s);
						if (s.equals(WorkoutData.TTS_WORKOUT_DESCRIPTION)) {
							_Text2Speech.silence(2000);
							_Text2Speech.speak("Start When I Say, Begin", WorkoutData.TTS_WORKOUT_READY);
						} else if (s.equals(WorkoutData.TTS_WORKOUT_READY)) {
							_Text2Speech.silence(2000);
							_Text2Speech.speak("Begin", WorkoutData.TTS_WORKOUT_BEGIN);
						} else if (s.equals(WorkoutData.TTS_WORKOUT_BEGIN)) {
							TimeOfWorkout = System.currentTimeMillis();
							_CurrentWorkout.StartWorkout();
							_WorkoutInProgress = true;
						} else if (s.equals(WorkoutData.TTS_WORKOUT_COMPLETE)) {
							Long timeToComplete = Math.abs(TimeOfWorkout - System.currentTimeMillis());
							_SFXPlayer.killAll();
							_SaveHistoricalReps.updateWorkout(_CurrentWorkout.getName(), _WorkoutReps);
							_SaveWorkoutSensor.execute();
							_SaveWorkoutData.addNewWorkout(_CurrentWorkout.getName(), timeToComplete, 100l, _CurrentWorkout.getReps());
							Intent intent = new Intent(getApplicationContext(), LoadingScreen.class);
							startActivity(intent);
						} else if (s.equals(WorkoutData.TTS_WORKOUT_AUDIO_FEEDBACK)) {

						} else if (s.equals(WorkoutData.TEST)) {

						}
					}
				});
				_Text2Speech.addInitListener(new SpeechInitListener() {
					@Override
					public void onInit() {
						_Text2Speech.speak(_WorkoutName + ",on," + _WorkoutHand + ",Hand," + ". Doing " + _WorkoutReps + ",Reps", WorkoutData.TTS_WORKOUT_DESCRIPTION);
					}
				});


			} else {
				Intent install = new Intent();
				install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(install);
			}
		}
	}

	public void SetupWorkout(String WorkoutName, int reps) {
		SpeechTrigger speechTrigger = new SpeechTrigger() {
			@Override
			public void speak(String s) {
				_Text2Speech.speak(s, WorkoutData.TTS_WORKOUT_AUDIO_FEEDBACK);
			}
		};
		OutputWorkoutData outputWorkoutData = new OutputWorkoutData() {
			@Override
			public void getData(float[] f) {
				//	_CurrentWorkoutView.dataIn(f);
			}
		};
		OutputWorkoutStrings outputWorkoutStrings = new OutputWorkoutStrings() {
			@Override
			public void getStrings(String[] s) {
				//	_CurrentWorkoutView.stringIn(s);
			}
		};
		for (int i = 0; i < WorkoutData.WORKOUT_DESCRIPTIONS.length; i++) {
			if (WorkoutName.equals(WorkoutData.WORKOUT_DESCRIPTIONS[i].getName())) {
				_WorkoutDescription = WorkoutData.WORKOUT_DESCRIPTIONS[i];
				break;
			}
		}
		if (_WorkoutDescription.getName().equals("Unlock Key")) {
		/*	setContentView(R.layout.activity_m);
			_CurrentWorkout = new WO_MultiTouch(WorkoutName, reps, speechTrigger, _SFXPlayer, outputWorkoutData, outputWorkoutStrings);

		*/
		} else if (_WorkoutDescription.getName().equals("Unlock Door")) {
		/*	_CurrentWorkout = new WO_PickUpVertical(WorkoutName, reps, speechTrigger, _SFXPlayer, outputWorkoutData, outputWorkoutStrings);
			_CurrentWorkoutView = new WV_JustText(getApplicationContext());
		*/
		} else if (_WorkoutDescription.getName().equals("Phone Number")) {
			setContentView(R.layout.activity_phone_number);
			ArrayList<View> views = new ArrayList<>();
			views.add(findViewById(R.id.whatToType));
			views.add(findViewById(R.id.whatYouTyped));
			views.add(findViewById(R.id.phone0));
			views.add(findViewById(R.id.phone1));
			views.add(findViewById(R.id.phone2));
			views.add(findViewById(R.id.phone3));
			views.add(findViewById(R.id.phone4));
			views.add(findViewById(R.id.phone5));
			views.add(findViewById(R.id.phone6));
			views.add(findViewById(R.id.phone7));
			views.add(findViewById(R.id.phone8));
			views.add(findViewById(R.id.phone9));
			_CurrentWorkoutView = findViewById(android.R.id.content).getRootView();
			_CurrentWorkout = new WO_PhoneNumber(WorkoutName, reps, views, speechTrigger, _SFXPlayer, outputWorkoutData, outputWorkoutStrings);

		} else if (_WorkoutDescription.getName().equals("Multi Touch")) {
			setContentView(R.layout.activity_multi_touch);
			ArrayList<View> views = new ArrayList<>();
			views.add(findViewById(R.id.circle1));
			views.add(findViewById(R.id.circle2));
			views.add(findViewById(R.id.circle3));
			views.add(findViewById(R.id.circle4));
			views.add(findViewById(R.id.circle5));
			views.add(findViewById(R.id.circle6));
			_CurrentWorkoutView = findViewById(android.R.id.content).getRootView();
			_CurrentWorkout = new WO_MultiTouch(WorkoutName, reps, views, speechTrigger, _SFXPlayer, outputWorkoutData, outputWorkoutStrings);

		}
		final Handler handler = new Handler();
		handler.post(new Runnable() {
			@Override
			public void run() {
				_CurrentWorkout.Update();
				_CurrentWorkoutView.invalidate();
				if (_CurrentWorkout.isWorkoutComplete() && _WorkoutInProgress) {
					_Text2Speech.speak("Workout Complete", WorkoutData.TTS_WORKOUT_COMPLETE);
					_WorkoutInProgress = false;
				}
				handler.postDelayed(this, 35);
			}
		});

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		_CurrentWorkout.TouchIn(event.getX(), event.getY());
		Log.e("Coordinates", "(" + event.getX() + "," + event.getY() + ")");
		_SaveWorkoutSensor.saveData(new float[]{event.getX(), event.getY()});
		return super.onTouchEvent(event);
	}

}
