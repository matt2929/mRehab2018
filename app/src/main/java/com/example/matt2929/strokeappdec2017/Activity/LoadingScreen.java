package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.example.matt2929.strokeappdec2017.R;

import static com.example.matt2929.strokeappdec2017.Values.WorkoutData.progress;

public class LoadingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (progress != 100f) {
                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                    progressBar.setProgress(Float.floatToIntBits(Math.round(progress)));
                    handler.postDelayed(this, 100);
                } else {
                    Intent intent = new Intent(getApplicationContext(), PostWorkoutReport.class);
                    startActivity(intent);
                }
            }
        });

    }
}
