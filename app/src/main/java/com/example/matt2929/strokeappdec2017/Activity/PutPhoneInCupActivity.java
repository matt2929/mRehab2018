package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveWorkoutJSON;

public class PutPhoneInCupActivity extends AppCompatActivity {
	LinearLayout isPhoneInCup, phoneInCup;
	Button inCupYes, inCupNo, inCupOk;
	Long timer = Long.valueOf(0);
	SaveWorkoutJSON saveWorkoutJSON;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		saveWorkoutJSON = new SaveWorkoutJSON(getApplicationContext());
		setContentView(R.layout.activity_put_phone_in_cup);
		isPhoneInCup = (LinearLayout) findViewById(R.id.isPhoneInCup);
		phoneInCup = (LinearLayout) findViewById(R.id.phoneInCup);
		isPhoneInCupView();
		inCupYes = (Button) findViewById(R.id.inPhoneButtonYes);
		inCupNo = (Button) findViewById(R.id.inPhoneButtonNo);
		inCupOk = (Button) findViewById(R.id.inPhoneButtonOk);
		inCupYes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = getIntent();
				intent.setClass(getApplicationContext(), GoalsAndRepsActivity.class);
				startActivity(intent);
			}
		});
		inCupNo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				timer = System.currentTimeMillis();
				phoneInCupView();
			}
		});
		inCupOk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Long duration = Math.abs(timer - System.currentTimeMillis());
				saveWorkoutJSON.addNewWorkout("Put in cup", "Left", duration / Long.valueOf(1000), Long.valueOf(100), 1);
				Intent intent = getIntent();
				intent.setClass(getApplicationContext(), GoalsAndRepsActivity.class);
				startActivity(intent);
			}
		});
	}


	private void isPhoneInCupView() {
		isPhoneInCup.setVisibility(View.VISIBLE);
		phoneInCup.setVisibility(View.GONE);
	}

	private void phoneInCupView() {
		isPhoneInCup.setVisibility(View.GONE);
		phoneInCup.setVisibility(View.VISIBLE);
	}
}
