package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.WorkoutsView.PlayGifView;


public class WorkoutPreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_preview);

	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	    PlayGifView videoView = findViewById(R.id.videoView);
	    Button start = findViewById(R.id.preview_start);
	    ImageButton imageButton = findViewById(R.id.homeButton);
	    String workoutName = getIntent().getStringExtra("Workout");
	    imageButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    Intent intent = new Intent(getApplicationContext(), WorkoutOrHistoryOrCalendarActivity.class);
			    startActivity(intent);
		    }
	    });
	    videoView.setImageResource(R.drawable.skeletondance);
	    videoView.setScaleX(3);
	    videoView.setScaleY(3);
	    switch (workoutName) {
		    case "Horizontal Bowl":
			    break;
		    case "Vertical Bowl":
                break;
            case "Horizontal Cup":
                break;
            case "Vertical Cup":
                break;
            case "Walk Cup":
                break;
            case "Twist Cup":
                break;
            case "Pour Cup":
                break;
            case "Unlock Key":
                break;
            case "Unlock Door":
                break;
            case "Phone Number":
                break;
            case "Multi Touch":
                break;
        }
	    start.setOnClickListener(new View.OnClickListener() {
		    @Override
            public void onClick(View view) {
                Intent intent = getIntent();
	            String WorkoutType = intent.getStringExtra("WorkoutType");
	            if (WorkoutType.equals("Sensor")) {
		            intent.setClass(getApplicationContext(), SensorWorkoutRunner.class);
	            } else {
		            intent.setClass(getApplicationContext(), TouchWorkoutRunner.class);
	            }
                startActivity(intent);
            }
        });
    }
}
