package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveWorkoutJSON;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;
import com.example.matt2929.strokeappdec2017.Workouts.WorkoutDescription;

public class PutPhoneInContainer extends AppCompatActivity {
	LinearLayout isPhoneInCup;
	Button inCupYes, inCupNo;
	Long timer = Long.valueOf(0);
	SaveWorkoutJSON saveWorkoutJSON;
	TextView textView;
	ImageView imageView;
	String stillInString = "", printName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		saveWorkoutJSON = new SaveWorkoutJSON(getApplicationContext());
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_put_phone_in_object);
		isPhoneInCup = findViewById(R.id.isPhoneInCup);
		imageView = findViewById(R.id.objectPreviewView);
		textView = findViewById(R.id.promptTextPutIn);
		inCupYes = findViewById(R.id.inPhoneButtonYes);
		inCupNo = findViewById(R.id.inPhoneButtonNo);
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
				pressWhenIn();
			}
		});
		placePhoneInCupView();


	}

	private void placePhoneInCupView() {
		for (WorkoutDescription workoutDescription : WorkoutData.WORKOUT_DESCRIPTIONS) {
			if (workoutDescription.getName().equals(getIntent().getStringExtra("Workout"))) {
				printName = workoutDescription.getPrintType();
				if (workoutDescription.getPrintType().equals(WorkoutData.Print_Container_Bowl)) {
					bowlView();
				} else if (workoutDescription.getPrintType().equals(WorkoutData.Print_Container_Cup)) {
					mugView();
				} else if (workoutDescription.getPrintType().equals(WorkoutData.Print_Container_Door)) {
					doorView();
				} else if (workoutDescription.getPrintType().equals(WorkoutData.Print_Container_Key)) {
					keyView();
				} else if (workoutDescription.getPrintType().equals(WorkoutData.Print_Container_No_Container)) {
					outView();
				}
				break;
			}
		}
	}

	private void pressWhenIn() {
		textView.setText(stillInString);
		inCupNo.setClickable(false);
		inCupNo.setVisibility(View.GONE);
		inCupYes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (printName.equals(WorkoutData.Print_Container_Bowl)) {
					Long duration = Math.abs(timer - System.currentTimeMillis());
					saveWorkoutJSON.addNewWorkout("Put in cup", "Left", duration / Long.valueOf(1000), Long.valueOf(100), 1);
				}
				Intent intent = getIntent();
				intent.setClass(getApplicationContext(), GoalsAndRepsActivity.class);
				startActivity(intent);
			}
		});
	}

	public void bowlView() {
		imageView.setImageResource(R.drawable.bowl);
		textView.setText("Is the phone in the bowl?");
		stillInString = "Please place the phone in the bowl.";
	}

	public void mugView() {
		imageView.setImageResource(R.drawable.mug);
		textView.setText("Is the phone in the mug?");
		stillInString = "Please place the phone in the mug.";

	}

	public void doorView() {
		imageView.setImageResource(R.drawable.doorknob);
		textView.setText("Is the phone in the door knob holder?");
		stillInString = "Please place the phone in the door knob holder.";
	}

	public void keyView() {
		imageView.setImageResource(R.drawable.key);
		textView.setText("Is the phone in the key holder?");
		stillInString = "Please place the phone in the key holder.";

	}

	public void outView() {
		imageView.setImageResource(R.drawable.outside);
		textView.setText("Is the phone outside of all prints?");
		stillInString = "Please remove the phone from the print.";
	}
}
