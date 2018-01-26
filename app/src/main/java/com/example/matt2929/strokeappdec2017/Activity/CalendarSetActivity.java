package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveCalendarDateJSON;

public class CalendarSetActivity extends AppCompatActivity {
	TextView textView;
	CalendarView calendarView;
	Button buttonSave;
	ImageButton imageButton;
	SaveCalendarDateJSON saveCalendarDateJSON;
	int _year = -1, _month = -1, _dom = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_set);
		saveCalendarDateJSON = new SaveCalendarDateJSON(getApplicationContext());
		textView = (TextView) findViewById(R.id.setDateText);
		calendarView = (CalendarView) findViewById(R.id.setDateCalendar);
		imageButton = (ImageButton) findViewById(R.id.setDateHomeButt);
		buttonSave = (Button) findViewById(R.id.setDateSaveButton);
		calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
			@Override
			public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dom) {
				textView.setText(month + "/" + dom + "/" + year);
				_dom = dom;
				_month = month;
				_year = year;
			}
		});

		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), WorkoutOrHistoryOrCalendarActivity.class);
				startActivity(intent);
			}
		});

		buttonSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				saveCalendarDateJSON.addCalendarDate(_dom, _month, _year);
			}
		});
	}
}
