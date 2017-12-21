package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechCompleteListener;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechInitListener;
import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.Utilities.Text2Speech;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

public class TouchWorkoutRunner extends AppCompatActivity implements View.OnTouchListener {

    private final int CHECK_CODE = 0x1;
    Long TimeOfWorkout = System.currentTimeMillis();
    private Text2Speech text2Speech;
    private boolean workoutInProgress = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkTTS();
        setContentView(R.layout.activity_touch_workout_runner);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //invalidate view
                if (workoutInProgress) {
                    handler.postDelayed(this, 55);
                }
            }
        };
        handler.post(runnable);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (text2Speech != null) {
            text2Speech.destroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        text2Speech = new Text2Speech(getApplicationContext());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
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

}
