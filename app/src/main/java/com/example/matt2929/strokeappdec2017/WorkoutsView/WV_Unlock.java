package com.example.matt2929.strokeappdec2017.WorkoutsView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by matt2929 on 12/22/17.
 */

public class WV_Unlock extends WorkoutViewAbstract {

	Paint innerCircle, outerCircle, pointColor;

	float x = 0, y = 0;

	public WV_Unlock(Context context) {
		super(context);
		init();
	}

	public WV_Unlock(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public WV_Unlock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public WV_Unlock(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	public void init() {
		innerCircle = new Paint();
		outerCircle = new Paint();
		pointColor = new Paint();
		innerCircle.setColor(Color.rgb(0, 0, 0));
		outerCircle.setColor(Color.rgb(153, 102, 51));
		pointColor.setColor(Color.YELLOW);
		setBackgroundColor(Color.BLACK);
	}

	@Override
	public void dataIn(float[] f) {
		x = f[0];
		y = f[1];
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		float heightCircle = inchToPixels((1.1811f)), radiusTouch = inchToPixels(0.25f);
		canvas.drawCircle(getWidth() / 2, getHeight() - heightCircle, heightCircle + (radiusTouch * 2), outerCircle);
		canvas.drawCircle(getWidth() / 2, getHeight() - heightCircle, heightCircle + (radiusTouch), innerCircle);
		canvas.drawCircle(getWidth() / 2, getHeight() - heightCircle, heightCircle - (radiusTouch), outerCircle);
		canvas.drawCircle(x, y, radiusTouch, pointColor);
	}

	private float inchToPixels(float dim) {
		DisplayMetrics mDisplayMetric = getContext().getResources().getDisplayMetrics();
		float conversionFactor = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, 1, mDisplayMetric);
		return dim * conversionFactor;
	}


}
