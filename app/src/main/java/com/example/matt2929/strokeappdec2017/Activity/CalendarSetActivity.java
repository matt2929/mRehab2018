package com.example.matt2929.strokeappdec2017.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveCalendarDateJSON;

public class CalendarSetActivity extends AppCompatActivity {
	SaveCalendarDateJSON saveCalendarDateJSON;
	TextView textView;
	CalendarView calendarView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_set);
		textView = (TextView) findViewById(R.id.setDateText);
		calendarView = (CalendarView) findViewById(R.id.datePickerSet);
		calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
			@Override
			public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

			}
		});
		saveCalendarDateJSON = new SaveCalendarDateJSON(getApplicationContext());
	}
}
