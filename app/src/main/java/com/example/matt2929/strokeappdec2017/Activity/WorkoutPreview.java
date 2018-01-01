package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.matt2929.strokeappdec2017.R;

public class WorkoutPreview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_preview);
        ImageView imageView = (ImageView) findViewById(R.id.previewImage);
        Button button = (Button) findViewById(R.id.preview_start);
        String workoutName = getIntent().getStringExtra("Workout");
        
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.setClass(getApplicationContext(), SensorWorkoutRunner.class);
                startActivity(intent);
            }
        });
    }
}
