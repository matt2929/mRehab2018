package com.example.matt2929.strokeappdec2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WorkoutOrHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_or_history);
        Button history = (Button) findViewById(R.id.workHist_Hist);
        Button workout = (Button) findViewById(R.id.workHist_Work);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:Hookup history button
            }
        });
        workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), WorkoutSelection.class);
                startActivity(intent);
            }
        });
    }
}
