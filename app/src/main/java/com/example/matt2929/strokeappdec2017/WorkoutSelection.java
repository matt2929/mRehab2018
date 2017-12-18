package com.example.matt2929.strokeappdec2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.matt2929.strokeappdec2017.WorkoutData.*;

public class WorkoutSelection extends AppCompatActivity {
    int currentSelection = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_selection);
        Button left = (Button) findViewById(R.id.selectLeft);
        Button right = (Button) findViewById(R.id.selectRight);
        final ListView listView = (ListView) findViewById(R.id.selectActivity);
        ReadWriteUserData readWriteUserData = new ReadWriteUserData(getApplicationContext());

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateNewUser.class);
                startActivity(intent);
            }
        });

        //Sign into existing newUser
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentSelection != -1) {
                    //Login
                    //Intent intent = new Intent(getApplicationContext(), CreateNewUser.class);
                    //startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Select a Workout", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Select Existing User
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentSelection = i;
            }
        });

        ArrayList<String> string = new ArrayList<String>(Arrays.asList(ALLWORKOUTS));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,string);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
    }

}
