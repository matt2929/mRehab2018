package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveAndWriteUserInfo;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

import java.util.ArrayList;

import static com.example.matt2929.strokeappdec2017.Values.WorkoutData.WORKOUT_DESCRIPTIONS;
import static com.example.matt2929.strokeappdec2017.Values.WorkoutData.WORKOUT_TYPE;
import static com.example.matt2929.strokeappdec2017.Values.WorkoutData.Workout_Type_Sensor;

public class WorkoutSelection extends AppCompatActivity {

    int currentSelection = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_selection);
        Button left = (Button) findViewById(R.id.selectLeft);
        Button right = (Button) findViewById(R.id.selectRight);
        final ListView listView = (ListView) findViewById(R.id.selectActivity);
        SaveAndWriteUserInfo saveAndWriteUserInfo = new SaveAndWriteUserInfo(getApplicationContext());

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentSelection != -1) {
	                Intent intent = new Intent(getApplicationContext(), GoalsAndReps.class);
	                if (WORKOUT_TYPE[currentSelection].equals(Workout_Type_Sensor)) {
                        intent.putExtra("WorkoutType", "Sensor");
                    } else {
                        intent.putExtra("WorkoutType", "Touch");
                    }
                    intent.putExtra("Hand", "Left");
                    intent.putExtra("Workout", WORKOUT_DESCRIPTIONS[currentSelection].getName());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Select a Workout", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Sign into existing newUser
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentSelection != -1) {
	                Intent intent = new Intent(getApplicationContext(), GoalsAndReps.class);
	                if (WORKOUT_TYPE[currentSelection].equals(Workout_Type_Sensor)) {
	                    intent.putExtra("WorkoutType", "Sensor");
	                } else {
	                    intent.putExtra("WorkoutType", "Touch");
	                }
                    intent.putExtra("Hand", "Right");
                    intent.putExtra("Workout", WORKOUT_DESCRIPTIONS[currentSelection].getName());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Select a Workout", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Select Existing User
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentSelection = i;
            }
        });
        ArrayList<String> string = new ArrayList<String>();
        for (int i = 0; i < WorkoutData.WORKOUT_DESCRIPTIONS.length; i++) {
            string.add(WORKOUT_DESCRIPTIONS[i].getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.text_view_list, android.R.id.text1, string);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
    }

}
