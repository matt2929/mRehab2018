package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.PlaySfXTrigger;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechCompleteListener;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechInitListener;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.Utilities.Text2Speech;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;
import com.example.matt2929.strokeappdec2017.Workouts.WO_PickUpVertical;
import com.example.matt2929.strokeappdec2017.Workouts.WorkoutAbstract;

public class SensorWorkoutRunner extends AppCompatActivity implements SensorEventListener {

    private final int CHECK_CODE = 0x1;
    //~~~~~~~~~~~~~~~~~~~~~~~
    Long TimeOfWorkout = System.currentTimeMillis();
    Long last = System.currentTimeMillis();
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private EditText editText;
    //Refrence ID for TTS~~~~
    private Text2Speech text2Speech;
    private WorkoutAbstract workoutAbstract;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_runner);
        setupSensorsLinear();
        checkTTS();
        SpeechTrigger speechTrigger = new SpeechTrigger() {
            @Override
            public void speak(String s) {
                text2Speech.speak(s, WorkoutData.TTS_WORKOUT_AUDIO_FEEDBACK);
            }
        };
        PlaySfXTrigger playSfXTrigger = new PlaySfXTrigger() {
            @Override
            public void playSfXTrigger(int sfxID) {

            }
        };
        OutputWorkoutData outputWorkoutData = new OutputWorkoutData() {
            @Override
            public float[] getData() {
                return new float[0];
            }
        };
        OutputWorkoutStrings outputWorkoutStrings = new OutputWorkoutStrings() {
            @Override
            public String[] getStrings() {
                return new String[0];
            }
        };
        workoutAbstract = new WO_PickUpVertical(10, speechTrigger, playSfXTrigger, outputWorkoutData, outputWorkoutStrings);
        workoutAbstract.StartWorkout();
        editText = (EditText) findViewById(R.id.editText);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        if (text2Speech != null) {
            text2Speech.destroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    private void checkTTS() {
        Intent check = new Intent();
        check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(check, CHECK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                text2Speech = new Text2Speech(this);
                text2Speech.addSpeechCompleteListener(new SpeechCompleteListener() {
                    @Override
                    public void Spoke(String s) {
                        //do something when tts finishes saying words
                        if (s == WorkoutData.TTS_WORKOUT_DESCRIPTION) {
                            text2Speech.silence(2000);
                            text2Speech.speak("Start When I Say, Begin", WorkoutData.TTS_WORKOUT_READY);
                        } else if (s.equals(WorkoutData.TTS_WORKOUT_READY)) {
                            text2Speech.silence(2000);
                            text2Speech.speak("Begin", WorkoutData.TTS_WORKOUT_BEGIN);
                        } else if (s.equals(WorkoutData.TTS_WORKOUT_BEGIN)) {
                            TimeOfWorkout = System.currentTimeMillis();
                        } else if (s.equals(WorkoutData.TTS_WORKOUT_COMPLETE)) {
                            Long timeToComplete = Math.abs(TimeOfWorkout - System.currentTimeMillis());
                        } else if (s.equals(WorkoutData.TTS_WORKOUT_AUDIO_FEEDBACK)) {

                        } else if (s.equals(WorkoutData.TEST)) {

                        }
                    }
                });
                text2Speech.addInitListener(new SpeechInitListener() {
                    @Override
                    public void onInit() {
                    }
                });


            } else {
                Intent install = new Intent();
                install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(install);
            }
        }
    }

    public void setupSensorsLinear() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        } else {
            Toast.makeText(getApplicationContext(), "No sesor found", Toast.LENGTH_SHORT).show();
        }
    }

    public void setupSensorsGravity() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        } else {
            Toast.makeText(getApplicationContext(), "No sesor found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float AccX = sensorEvent.values[0];
        float AccY = sensorEvent.values[1];
        float AccZ = sensorEvent.values[2];
        if (workoutAbstract != null) {
            workoutAbstract.SensorDataIn(sensorEvent.values);
        }
        //get sensor refresh rate
 /*       editText.setText("X:" + AccX + " Y:" + AccY + " Z:" + AccZ);
        float delay =  (Math.abs(last-System.currentTimeMillis()))/1000f;
        last=System.currentTimeMillis();
        Log.e("Sesnor Rate", "Delay: "+delay+"s "+" fps: "+(1f)/(delay));*/
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
