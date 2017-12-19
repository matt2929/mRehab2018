package com.example.matt2929.strokeappdec2017;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        Button buttonExist = (Button) findViewById(R.id.loginExist); //select pre exisitng user
        Button buttonNew = (Button) findViewById(R.id.loginNew); //generate new user
        final ListView listView = (ListView) findViewById(R.id.loginListView);//list all existing user to pick from
        ReadWriteUserData readWriteUserData = new ReadWriteUserData(getApplicationContext());//use this to get users saved o
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
                //Choose a user
                currentSelection=i;
            }
        });
        //turn users into strings for list view
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
                R.layout.text_view_list, android.R.id.text1, userStrings);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
    }
}
