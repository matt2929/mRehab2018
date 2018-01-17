package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveHistoricalGoals;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveHistoricalReps;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

import java.util.ArrayList;

public class GoalsAndReps extends AppCompatActivity {
    SaveHistoricalGoals saveHistoricalGoals;
    SaveHistoricalReps saveHistoricalReps;
    Integer reps = 10;
    Intent newIntent;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_and_reps);

        Intent oldIntent = getIntent();
        String WorkoutHand = oldIntent.getStringExtra("Hand");
        String WorkoutName = oldIntent.getStringExtra("Workout");
        String WorkoutType = oldIntent.getStringExtra("WorkoutType");
        newIntent = new Intent(getApplicationContext(), WorkoutPreview.class);
        newIntent.putExtra("Hand", oldIntent.getStringExtra("Hand"));
        newIntent.putExtra("Workout", oldIntent.getStringExtra("Workout"));
        newIntent.putExtra("WorkoutType", WorkoutType);
        saveHistoricalGoals = new SaveHistoricalGoals(getApplicationContext());
        saveHistoricalReps = new SaveHistoricalReps(getApplicationContext(), WorkoutData.UserName);
        ArrayList<String> goals = saveHistoricalGoals.getGoals(WorkoutData.UserName);
        reps = saveHistoricalReps.getWorkoutReps(WorkoutName);
        textView = (TextView) findViewById(R.id.GoalAndRepNumberText);
        Button startWorkout = (Button) findViewById(R.id.GoalAndRepStartWorkout);
        Button repPlus = (Button) findViewById(R.id.GoalAndRepPlus);
        Button repMinus = (Button) findViewById(R.id.GoalAndRepMinus);
        ListView goalListView = (ListView) findViewById(R.id.GoalAndRepGoalsList);
        textView.setText("" + reps);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.text_view_list, android.R.id.text1, goals);
        // Assign adapter to ListView
        goalListView.setAdapter(adapter);

        repMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateReps((reps - 1));
            }
        });
        repPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateReps((reps + 1));
            }
        });

        startWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newIntent.putExtra("Reps", reps);
                startActivity(newIntent);
            }
        });
    }

    public void updateReps(int i) {
        if (i <= 30 && i >= 1) {
            reps = i;
            textView.setText("" + reps);
        } else {
            Toast.makeText(getApplicationContext(), "Reps Must be between 1 and 30", Toast.LENGTH_SHORT).show();
        }
    }
}
