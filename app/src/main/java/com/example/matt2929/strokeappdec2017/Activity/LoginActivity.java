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
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.User;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    int currentSelection = -1;
    List<String> userStrings = new ArrayList<>();
    List<User> userList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button buttonExist = (Button) findViewById(R.id.loginExist); //select pre exisitng user
        Button buttonNew = (Button) findViewById(R.id.loginNew); //generate new user
        final ListView listView = (ListView) findViewById(R.id.loginListView);//list all existing user to pick from
        SaveAndWriteUserInfo saveAndWriteUserInfo = new SaveAndWriteUserInfo(getApplicationContext());//use this to get users saved o
        //Create New User
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
	            Intent intent = new Intent(getApplicationContext(), CreateNewUserActivity.class);
	            startActivity(intent);
            }
        });

        //Sign into existing newUser
        buttonExist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentSelection != -1) {
	                //LoginActivity
	                WorkoutData.UserName = userList.get(currentSelection).getName();
	                Intent intent = new Intent(getApplicationContext(), WorkoutOrHistoryOrCalendarActivity.class);
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

        userList = saveAndWriteUserInfo.getUsers();
        for (int i = 0; i < userList.size(); i++) {
            User u = userList.get(i);
            String userString = "";
            userString += "Name: " + u.getName();
            userString += "\nGoals: " + u.getGoals();
            userStrings.add(userString);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.text_view_list, android.R.id.text1, userStrings);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
    }
}
