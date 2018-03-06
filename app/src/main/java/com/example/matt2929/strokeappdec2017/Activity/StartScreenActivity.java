package com.example.matt2929.strokeappdec2017.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveAndWriteUserInfo;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.User;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

public class StartScreenActivity extends AppCompatActivity {
	String numberDefinedName;
	RadioButton radioButton;
	Boolean debug = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_screen);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		final Button login = findViewById(R.id.startLogin);
		//	Button demo = (Button) findViewById(R.id.startDemo);
		radioButton = findViewById(R.id.debugRadio);
		NumberPicker numberPicker = findViewById(R.id.numberPicker);
		getPermissions();
		final SaveAndWriteUserInfo saveAndWriteUserInfo = new SaveAndWriteUserInfo(getApplicationContext());//use this to get users saved o
		login.setAlpha(.2f);

		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), WorkoutOrHistoryOrCalendarActivity.class);

				/*Intent intent;
				if (saveAndWriteUserInfo.isUserCreated()) {
					WorkoutData.UserData = saveAndWriteUserInfo.getUser();
					WorkoutData.UserName = saveAndWriteUserInfo.getUser().getName();
					intent = new Intent(getApplicationContext(), WorkoutOrHistoryOrCalendarActivity.class);
				} else {
				*/
				WorkoutData.UserData = new User();
				WorkoutData.UserData.setAge(20);
				WorkoutData.UserData.setGoals("Test");
				WorkoutData.UserData.setHand(1);

				if (!debug) {
					WorkoutData.UserData.setName(numberDefinedName);
					WorkoutData.UserName = numberDefinedName;
				} else {
					WorkoutData.UserData.setName("DEBUG");
					WorkoutData.UserName = "DEBUG";
				}
				//}
				startActivity(intent);
			}
		});
		login.setClickable(false);
		numberPicker.setMaxValue(20);
		numberPicker.setMinValue(0);
		numberPicker.setWrapSelectorWheel(true);
		numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				login.setAlpha(1f);
				login.setClickable(true);
				numberDefinedName = "Test Subject " + newVal + "";
			}
		});
		radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				debug = isChecked;
				login.setClickable(true);
			}
		});
	}

	public void getPermissions() {
		ActivityCompat.requestPermissions(this,
				new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WRITE_CALENDAR}, 55);
	}
}
