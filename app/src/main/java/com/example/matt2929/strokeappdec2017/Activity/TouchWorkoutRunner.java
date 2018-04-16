package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.EndRepTrigger;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechCompleteListener;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechInitListener;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveActivitiesDoneToday;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveHistoricalReps;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveTouchAndSensor;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveWorkoutJSON;
import com.example.matt2929.strokeappdec2017.Utilities.SFXPlayer;
import com.example.matt2929.strokeappdec2017.Utilities.Text2Speech;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;
import com.example.matt2929.strokeappdec2017.Workouts.TouchWorkoutAbstract;
import com.example.matt2929.strokeappdec2017.Workouts.WO_PhoneNumber;
import com.example.matt2929.strokeappdec2017.Workouts.WO_QuickTouch;
import com.example.matt2929.strokeappdec2017.Workouts.WO_Unlock;
import com.example.matt2929.strokeappdec2017.Workouts.WorkoutDescription;
import com.example.matt2929.strokeappdec2017.WorkoutsView.EmptyView;
import com.example.matt2929.strokeappdec2017.WorkoutsView.WV_Unlock;
import com.example.matt2929.strokeappdec2017.WorkoutsView.WorkoutViewAbstract;

import java.util.ArrayList;

public class TouchWorkoutRunner extends AppCompatActivity {

	private final int CHECK_CODE = 0x1;
	//~~~~~~~~~~~~~~~~~~~~~~~
	Long TimeOfWorkout = System.currentTimeMillis();
	Long TimeOfRep = System.currentTimeMillis();
	//Workout Attributes~~~
	String _WorkoutHand; //Which Hand
	String _WorkoutName; //Name of Current Wokrout
	Integer _WorkoutReps;//Number of Repetitions
	//Refrence ID for TTS~~~~
	private Text2Speech _Text2Speech;
	private TouchWorkoutAbstract _CurrentWorkout;
	private WorkoutViewAbstract _CurrentWorkoutView;
	private WorkoutDescription _WorkoutDescription = null;
	private SFXPlayer _SFXPlayer;
	private SaveHistoricalReps _SaveHistoricalReps;
	private SaveTouchAndSensor _SaveTouchAndSensor;
	private SaveWorkoutJSON _SaveWorkoutJSON;
	private Boolean _WorkoutInProgress = false;//Is workout currently running?
	private ArrayList<Float> saveDurations = new ArrayList<>();
	private SaveActivitiesDoneToday _SaveActivitiesDoneToday;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		_WorkoutHand = intent.getStringExtra("Hand");
		_WorkoutName = intent.getStringExtra("Workout");
		_WorkoutReps = intent.getIntExtra("Reps", 10);
		_SaveHistoricalReps = new SaveHistoricalReps(getApplicationContext(), WorkoutData.UserName);
		_SaveTouchAndSensor = new SaveTouchAndSensor(getApplicationContext(), _WorkoutName, "Time,X,Y,Good Touch,Touch Type");
		_SaveWorkoutJSON = new SaveWorkoutJSON(getApplicationContext());
		_SFXPlayer = new SFXPlayer(getApplicationContext());
		_SaveActivitiesDoneToday = new SaveActivitiesDoneToday(getApplicationContext());
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
							TimeOfRep = System.currentTimeMillis();
						} else if (s.equals(WorkoutData.TTS_WORKOUT_COMPLETE)) {
							workoutEndSequence();
						} else if (s.equals(WorkoutData.TTS_WORKOUT_AUDIO_FEEDBACK)) {

						} else if (s.equals(WorkoutData.TEST)) {

						}
					}
				});
				_Text2Speech.addInitListener(new SpeechInitListener() {
					@Override
					public void onInit() {
						String description = "";
						for (int i = 0; i < WorkoutData.WORKOUT_DESCRIPTIONS.length; i++) {
							if (WorkoutData.WORKOUT_DESCRIPTIONS[i].getName().equals(_WorkoutName)) {
								description = WorkoutData.WORKOUT_DESCRIPTIONS[i].getDescription();
							}
						}
						_Text2Speech.speak(description, WorkoutData.TTS_WORKOUT_DESCRIPTION);
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
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_FULLSCREEN;

// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
		//decorView.setSystemUiVisibility(uiOptions);

		SpeechTrigger speechTrigger = new SpeechTrigger() {
			@Override
			public void speak(String s) {
				_Text2Speech.speak(s, WorkoutData.TTS_WORKOUT_AUDIO_FEEDBACK);
			}
		};
		OutputWorkoutData outputWorkoutData = new OutputWorkoutData() {
			@Override
			public void getData(float[] f) {
				_CurrentWorkoutView.dataIn(f);
			}
		};
		OutputWorkoutStrings outputWorkoutStrings = new OutputWorkoutStrings() {
			@Override
			public void getStrings(String[] s) {
				_CurrentWorkoutView.stringIn(s);
			}
		};

		EndRepTrigger endRepTrigger = new EndRepTrigger() {
			@Override
			public void endRep() {
				saveDurations.add(Float.valueOf(System.currentTimeMillis() - TimeOfRep));
				TimeOfRep = System.currentTimeMillis();
			}
		};

		for (int i = 0; i < WorkoutData.WORKOUT_DESCRIPTIONS.length; i++) {
			if (WorkoutName.equals(WorkoutData.WORKOUT_DESCRIPTIONS[i].getName())) {
				_WorkoutDescription = WorkoutData.WORKOUT_DESCRIPTIONS[i];
				break;
			}
		}
		if (_WorkoutDescription.getName().equals("Unlock With Key")) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);


			_CurrentWorkoutView = new WV_Unlock(getApplicationContext());
			setContentView(_CurrentWorkoutView);
			ArrayList<View> views = new ArrayList<>();
			views.add(_CurrentWorkoutView);
			_CurrentWorkoutView.invalidate();
			_CurrentWorkout = new WO_Unlock(WorkoutName, reps, views, endRepTrigger, speechTrigger, _SFXPlayer, outputWorkoutData, outputWorkoutStrings);

		} else if (_WorkoutDescription.getName().equals("Turn Doorknob")) {
			_CurrentWorkoutView = new WV_Unlock(getApplicationContext());
			setContentView(_CurrentWorkoutView);
			ArrayList<View> views = new ArrayList<>();
			views.add(_CurrentWorkoutView);
			_CurrentWorkoutView.invalidate();
			_CurrentWorkout = new WO_Unlock(WorkoutName, reps, views, endRepTrigger, speechTrigger, _SFXPlayer, outputWorkoutData, outputWorkoutStrings);
		} else if (_WorkoutDescription.getName().equals("Phone Number")) {
			EmptyView workoutViewAbstract = new EmptyView(this);
			LayoutInflater factory = LayoutInflater.from(this);
			View myView = factory.inflate(R.layout.activity_phone_number, null);
			workoutViewAbstract.addView(myView);
			myView.setLayoutParams(new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT));
			setContentView(workoutViewAbstract);
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
			_CurrentWorkoutView = workoutViewAbstract;
			_CurrentWorkout = new WO_PhoneNumber(WorkoutName, reps, views, endRepTrigger, speechTrigger, _SFXPlayer, outputWorkoutData, outputWorkoutStrings);
		} else if (_WorkoutDescription.getName().equals("Quick Tap")) {
			EmptyView workoutViewAbstract = new EmptyView(this);
			LayoutInflater factory = LayoutInflater.from(this);
			View myView = factory.inflate(R.layout.activity_quick_touch, null);
			workoutViewAbstract.addView(myView);
			myView.setLayoutParams(new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT));
			setContentView(workoutViewAbstract);
			ArrayList<View> views = new ArrayList<>();
			views.add(findViewById(R.id.circle1));
			views.add(findViewById(R.id.circle2));
			views.add(findViewById(R.id.circle3));
			views.add(findViewById(R.id.circle4));
			views.add(findViewById(R.id.circle5));
			views.add(findViewById(R.id.circle6));
			_CurrentWorkoutView = workoutViewAbstract;
			_CurrentWorkout = new WO_QuickTouch(WorkoutName, reps, views, endRepTrigger, speechTrigger, _SFXPlayer, outputWorkoutData, outputWorkoutStrings);
		}

		final Handler handler = new Handler();
		handler.post(new Runnable() {
			@Override
			public void run() {
				_CurrentWorkout.Update();
				_CurrentWorkoutView.invalidate();
				if (_CurrentWorkout.isWorkoutComplete() && _WorkoutInProgress) {
					_Text2Speech.speak("Activity Complete", WorkoutData.TTS_WORKOUT_COMPLETE);
					_WorkoutInProgress = false;
				}
				handler.postDelayed(this, 35);
			}
		});

// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
	

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		View decorView = getWindow().getDecorView();

		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			decorView.setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_FULLSCREEN
							| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		}
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			Boolean GoodTouch = _CurrentWorkout.TouchIn(event.getX(), event.getY(), event);
			int goodTouchInt;
			if (GoodTouch) {
				goodTouchInt = 1;
			} else {
				goodTouchInt = 0;
			}
			_SaveTouchAndSensor.addData(new float[]{Math.abs(TimeOfWorkout - System.currentTimeMillis()), event.getX(), event.getY(), goodTouchInt, event.getAction()});
			TimeOfWorkout = System.currentTimeMillis();
		}
		return super.dispatchTouchEvent(event);
	}

	public Float averageTime(ArrayList<Float> floats) {
		float sum = 0L;
		for (int i = 0; i < floats.size(); i++) {
			sum += floats.get(i);
		}
		Float value = ((sum / ((float) floats.size())));
		return value;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
		}
		return super.onKeyDown(keyCode, event);
	}
	private void workoutEndSequence() {
		_SFXPlayer.killAll();
		_SaveHistoricalReps.updateWorkout(_CurrentWorkout.getName(), _WorkoutReps);
		float duration = averageTime(saveDurations) / (float) (1000);
		_SaveTouchAndSensor.saveAllData(duration, _CurrentWorkout.getScore().getScore(), _CurrentWorkout.getReps(), _WorkoutHand);
		_SaveWorkoutJSON.addNewWorkout(_CurrentWorkout.getName(), _WorkoutHand, duration, _CurrentWorkout.getScore().getScore(), _CurrentWorkout.getReps());
		_SaveActivitiesDoneToday.updateWorkout(_WorkoutName);
		Intent intent = getIntent();
		intent.setClass(getApplicationContext(), LoadingScreenActivity.class);
		startActivity(intent);
	}
}
