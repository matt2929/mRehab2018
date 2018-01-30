package com.example.matt2929.strokeappdec2017.Activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveCalendarDateJSON;

import java.util.Calendar;

public class CalendarSetActivity extends AppCompatActivity {
	TextView textViewCalendar, textViewTime;
	CalendarView dateView;
	TimePicker timePicker;
	Button buttonDate, buttonTime;
	ImageButton homeDate, homeTime;
	LinearLayout timeLayout, dateLayout;
	SaveCalendarDateJSON saveCalendarDateJSON;
	int _month = -1, _dom = -1, _year = -1, _hour = -1, _min = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_set);
		saveCalendarDateJSON = new SaveCalendarDateJSON(getApplicationContext());
		//Date Views
		textViewCalendar = (TextView) findViewById(R.id.setDateText);
		dateView = (CalendarView) findViewById(R.id.setDateCalendar);
		homeDate = (ImageButton) findViewById(R.id.setDateHomeButt);
		buttonDate = (Button) findViewById(R.id.setDateSaveButton);
		dateLayout = (LinearLayout) findViewById(R.id.setDateView);
		//Time Views
		timePicker = (TimePicker) findViewById(R.id.setTimeCalendar);
		textViewTime = (TextView) findViewById(R.id.setTimeText);
		buttonTime = (Button) findViewById(R.id.setTimeSaveButton);
		homeTime = (ImageButton) findViewById(R.id.setTimeHomeButt);
		timeLayout = (LinearLayout) findViewById(R.id.setTimeView);
		//~~~~~~~~
		dateView();
		dateView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
			@Override
			public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dom) {
				textViewCalendar.setText(month + "/" + dom + "/" + year);
				_year = year;
				_dom = dom;
				_month = month;
			}
		});
		homeDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), WorkoutOrHistoryOrCalendarActivity.class);
				startActivity(intent);
			}
		});
		buttonDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (_year != -1) {
					timeView();
				}
			}
		});
		homeTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), WorkoutOrHistoryOrCalendarActivity.class);
				startActivity(intent);
			}
		});
		buttonTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (_hour != -1) {
					setDateAndTime(_year, _month, _dom, _hour, _min);
				}
			}
		});
		timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker timePicker, int hour, int min) {
				_hour = hour;
				_min = min;
				textViewTime.setText(_hour + ":" + _min);
			}
		});
	}

	public void setDateAndTime(int Year, int Month, int Dom, int Hour, int Min) {
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
		timeLayout.setVisibility(View.GONE);
		dateLayout.setVisibility(View.VISIBLE);
	}

	public void timeView() {
		timeLayout.setVisibility(View.VISIBLE);
		dateLayout.setVisibility(View.GONE);
	}

}
