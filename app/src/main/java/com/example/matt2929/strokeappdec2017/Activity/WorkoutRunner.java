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

import com.example.matt2929.strokeappdec2017.Listeners_Triggers.SpeechCompleteListener;
import com.example.matt2929.strokeappdec2017.Listeners_Triggers.SpeechInitListener;
import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.Utilities.Text2Speech;

public class WorkoutRunner extends AppCompatActivity implements SensorEventListener {

    private final int CHECK_CODE = 0x1;
    private final String TTS_WORKOUT_DESCRIPTION = "WORKOUT_DESCRIPTION"; //Say workout description
    private final String TTS_WORKOUT_READY = "WORKOUT_READY";//Say Ready then have some delay
    private final String TTS_WORKOUT_BEGIN = "WORKOUT_BEGIN";//Say Begin
    private final String TTS_WORKOUT_COMPLETE = "WORKOUT_COMPLETE";//Say WorkoutAbstract complete then give post workout feedback
    private final String TTS_WORKOUT_AUDIO_FEEDBACK = "WORKOUT_FEEDBACK";//Give mid workout feedback
    private final String TEST = "TEST";//just a test
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private EditText editText;
    //Refrence ID for TTS~~~~
    private Text2Speech text2Speech;
    //~~~~~~~~~~~~~~~~~~~~~~~

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_runner);
        setupSensors();
        checkTTS();
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
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
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
                        if (s == TTS_WORKOUT_DESCRIPTION) {

                        } else if (s.equals(TTS_WORKOUT_READY)) {

                        } else if (s.equals(TTS_WORKOUT_BEGIN)) {

                        } else if (s.equals(TTS_WORKOUT_COMPLETE)) {

                        } else if (s.equals(TTS_WORKOUT_AUDIO_FEEDBACK)) {

                        } else if (s.equals(TEST)) {

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

    public void setupSensors() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        } else {
            Toast.makeText(getApplicationContext(), "No sesor found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float AccX = sensorEvent.values[0];
        float AccY = sensorEvent.values[1];
        float AccZ = sensorEvent.values[2];
        editText.setText("X:" + AccX + " Y:" + AccY + " Z:" + AccZ);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
