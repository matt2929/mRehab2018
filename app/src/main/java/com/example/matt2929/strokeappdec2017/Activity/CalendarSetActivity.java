package com.example.matt2929.strokeappdec2017.Activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveCalendarDateJSON;

import java.util.Calendar;

public class CalendarSetActivity extends AppCompatActivity {
	CalendarView calendarPicker;
	TimePicker timePicker;
	Button continueButton;
	ImageButton homeButton;
	SaveCalendarDateJSON saveCalendarDateJSON;
	int _month = -1, _dom = -1, _year = -1, _hour = -1, _min = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_set);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		saveCalendarDateJSON = new SaveCalendarDateJSON(getApplicationContext());
		//Date Views
		calendarPicker = findViewById(R.id.setDateCalendar);
		homeButton = findViewById(R.id.homeButton);
		continueButton = findViewById(R.id.continueButton);
		//Time Views
		timePicker = findViewById(R.id.setTimeCalendar);
		//~~~~~~~~
		dateView();
		calendarPicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
			@Override
			public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dom) {
				_year = year;
				_dom = dom;
				_month = month;
				continueButton.setAlpha(1);
				continueButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (_year != -1) {
							timeView();
						}
					}
				});
			}
		});
		homeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), WorkoutOrHistoryOrCalendarActivity.class);
				startActivity(intent);
			}
		});


		timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker timePicker, int hour, int min) {
				_hour = hour;
				_min = min;
				continueButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (_hour != -1) {
							setDateAndTime();
							Intent intent = new Intent(getApplicationContext(), WorkoutOrHistoryOrCalendarActivity.class);
							Toast.makeText(getApplicationContext(), "Date Saved!", Toast.LENGTH_SHORT).show();
							startActivity(intent);
						}

					}
				});
				continueButton.setAlpha(1f);
			}
		});
	}

	public void setDateAndTime() {
		long calID = 1;
		long startMillis = 0;
		long endMillis = 0;
		Calendar beginTime = Calendar.getInstance();
		beginTime.set(_year, _month, _dom, _hour, _min);
		startMillis = beginTime.getTimeInMillis();
		Calendar endTime = Calendar.getInstance();
		endTime.set(_year, _month, _dom, _hour, _min + 5);
		endMillis = endTime.getTimeInMillis();
		ContentResolver cr = getContentResolver();
		ContentValues values = new ContentValues();
		values.put(CalendarContract.Events.DTSTART, startMillis);
		values.put(CalendarContract.Events.DTEND, endMillis);
		values.put(CalendarContract.Events.TITLE, "Workout");
		values.put(CalendarContract.Events.DESCRIPTION, "Your Next Wokrout Time");
		values.put(CalendarContract.Events.CALENDAR_ID, calID);
		values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
			return;
		}
		Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
		long eventID = Long.parseLong(uri.getLastPathSegment());
	}

	public void dateView() {
		timePicker.setVisibility(View.GONE);
		calendarPicker.setVisibility(View.VISIBLE);
		continueButton.setAlpha(.2f);
	}

	public void timeView() {
		continueButton.setAlpha(.2f);
		continueButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		timePicker.setVisibility(View.VISIBLE);
		calendarPicker.setVisibility(View.GONE);
	}

}
