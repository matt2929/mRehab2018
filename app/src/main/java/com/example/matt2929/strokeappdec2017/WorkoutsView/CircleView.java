package com.example.matt2929.strokeappdec2017.WorkoutsView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by matt2929 on 1/5/18.
 */

public class CircleView extends RelativeLayout {
	public Paint paintShape = new Paint();

	public CircleView(Context context) {
		super(context);
		init();
	}

	public CircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public CircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	public void init() {
		paintShape.setColor(Color.BLUE);
		setBackgroundColor(0);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, paintShape);
	}
}
