package com.example.matt2929.strokeappdec2017;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    int currentSelection = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button buttonExist = (Button) findViewById(R.id.selectLeft);
        Button buttonNew = (Button) findViewById(R.id.loginNew);
        final ListView listView = (ListView) findViewById(R.id.loginListView);
        final Integer noSelectColor = Color.WHITE;
        final Integer selectColor = Color.YELLOW;
        ReadWriteUserData readWriteUserData = new ReadWriteUserData(getApplicationContext());
        //Create New User
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateNewUser.class);
                startActivity(intent);
            }
        });

        //Sign into existing newUser
        buttonExist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentSelection != -1) {
                    //Login
                    Intent intent = new Intent(getApplicationContext(), WorkoutOrHistory.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(getApplicationContext(), "Please Select a User", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Select Existing User
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentSelection=i;
            }
        });
        List<String> userStrings = new ArrayList<>();
        List<User> userList = readWriteUserData.getUsers();
        for (User u : userList) {
            String userString = "";
            userString += "Name: " + u.getName();
            userString += "\nAge:" + u.getAge();
            if (u.getHand() == 0) {
                userString += "\nAffected: Left";
            } else {
                userString += "\nAffected: Right";
            }
            userString += "\nGoals: " + u.getGoals();
            userStrings.add(userString);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, userStrings);


        // Assign adapter to ListView
        listView.setAdapter(adapter);
    }
}
