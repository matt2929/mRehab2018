package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

import static com.example.matt2929.strokeappdec2017.Values.WorkoutData.progressCloud;
import static com.example.matt2929.strokeappdec2017.Values.WorkoutData.progressLocal;

public class LoadingScreen extends AppCompatActivity {
	TextView textView;
	ProgressBar progressBarCloud, progressBarLocal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading_screen);
		textView = (TextView) findViewById(R.id.progressText);
		progressBarCloud = (ProgressBar) findViewById(R.id.cloudProgress);
		progressBarLocal = (ProgressBar) findViewById(R.id.localProgress);
		progressBarLocal.setMax(100);
		progressBarCloud.setMax(100);
		final Handler handler = new Handler();
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (progressLocal != 100f && progressCloud != 100f) {
					progressBarCloud.setProgress(Math.round((WorkoutData.progressCloud)));
					progressBarLocal.setProgress(Math.round(WorkoutData.progressLocal));
					textView.setText("Cloud" + Math.round((progressCloud)));
					textView.setText(textView.getText().toString() + "\nLocal" + Math.round((progressLocal)));
					handler.postDelayed(this, 55);
				} else {
					Intent intent = new Intent(getApplicationContext(), PostWorkoutReport.class);
					startActivity(intent);
				}
			}
		});

	}
}
