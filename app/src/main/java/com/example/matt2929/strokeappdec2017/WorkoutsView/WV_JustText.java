package com.example.matt2929.strokeappdec2017.WorkoutsView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by matt2929 on 12/22/17.
 */

public class WV_JustText extends WorkoutViewAbstract {
	Paint textPaint = new Paint();
	String output = "";


	public WV_JustText(Context context) {
		super(context);
		setUp();
	}

	public WV_JustText(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		setUp();
	}

	public WV_JustText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setUp();
	}

	public WV_JustText(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		setUp();
	}

	private void setUp() {
		textPaint.setColor(Color.BLACK);
		textPaint.setTextSize(80);
		textPaint.setTextAlign(Paint.Align.CENTER);
		textPaint.setAntiAlias(true);
		setBackgroundColor(Color.LTGRAY);
	}

	@Override
	public void stringIn(String[] s) {
		super.stringIn(s);
		output = s[0];
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawText("Count " + output + "", getWidth() / 2, getHeight() / 2, textPaint);
	}
}
