package com.example.matt2929.strokeappdec2017.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveAndWriteUserInfo;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

public class StartScreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_screen);
		Button login = findViewById(R.id.startLogin);
		Button demo = findViewById(R.id.startDemo);
		getPermissions();
		final SaveAndWriteUserInfo saveAndWriteUserInfo = new SaveAndWriteUserInfo(getApplicationContext());//use this to get users saved o
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent;
				if (saveAndWriteUserInfo.isUserCreated()) {
					WorkoutData.UserData = saveAndWriteUserInfo.getUser();
					WorkoutData.UserName = saveAndWriteUserInfo.getUser().getName();
					intent = new Intent(getApplicationContext(), WorkoutOrHistoryOrCalendarActivity.class);
				} else {
					intent = new Intent(getApplicationContext(), CreateNewUserActivity.class);
				}
				startActivity(intent);
			}
		});

	}

	public void getPermissions() {

		ActivityCompat.requestPermissions(this,
				new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WRITE_CALENDAR, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 55);
	}
}