package com.example.matt2929.strokeappdec2017.WorkoutsView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by matt2929 on 12/31/17.
 */

public class GradeView extends View {
    String Title = "";
    Bitmap bitmap = null;
    float oldValue, newValue;
    boolean didBetter = false;
    private Paint titleTextPaint = new Paint(), textTextPaint = new Paint(), backgroundPaint = new Paint(), marginPaint = new Paint();

    public GradeView(Context context) {
        super(context);
        Init();
    }

    public GradeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    public GradeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init();
    }

    public GradeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Init();
    }

    public void Init() {
        titleTextPaint.setColor(Color.BLACK);
        titleTextPaint.setTextAlign(Paint.Align.CENTER);
        titleTextPaint.setTextSize(80);
        titleTextPaint.setUnderlineText(true);

        textTextPaint.setColor(Color.BLACK);
        textTextPaint.setTextAlign(Paint.Align.CENTER);
        textTextPaint.setTextSize(60);

	    backgroundPaint.setColor(Color.LTGRAY);
	    marginPaint.setColor(Color.DKGRAY);
	    this.setBackgroundColor(Color.BLACK);
    }

    public void SetupView(Bitmap bitmap, String Title, float oldValue, float newValue, boolean didBetter) {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.Title = Title;
        this.bitmap = bitmap;
        this.didBetter = didBetter;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float width = this.getWidth();
        float height = this.getHeight();
        float marginWidth = this.getWidth() / 60;
        float titleHeight = this.getHeight() / 3;
        canvas.drawRect(0, 0, width, height, backgroundPaint);
        canvas.drawText(Title, getWidth() / 2, titleHeight - 30, titleTextPaint);
        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect dest = new Rect((int) (marginWidth * 2), (int) (titleHeight + (marginWidth)), (int) ((getWidth() / 3) - marginWidth), (int) (height - (marginWidth)));
        if (didBetter) {
            backgroundPaint.setColor(Color.GREEN);
        } else {
            backgroundPaint.setColor(Color.RED);
        }
        canvas.drawRect(0, titleHeight, getWidth() / 3, height, backgroundPaint);
        Init();
        canvas.drawBitmap(bitmap, src, dest, null);

        canvas.drawRect(0, 0, marginWidth, height, marginPaint);
        canvas.drawRect(0, 0, width, marginWidth, marginPaint);
        canvas.drawRect(width - marginWidth, 0, width, height, marginPaint);
        canvas.drawRect(0, height - marginWidth, width, height, marginPaint);
        canvas.drawRect(0, titleHeight, width, titleHeight + marginWidth, marginPaint);
        canvas.drawRect(0, titleHeight, width, titleHeight + marginWidth, marginPaint);

        canvas.drawRect(width / 3, titleHeight, (width / 3) + marginWidth, height, marginPaint);
        canvas.drawRect((width / 3) * 2, titleHeight, ((width / 3) * 2) + marginWidth, height, marginPaint);

        canvas.drawText("Now: " + newValue, width / 2 + marginWidth, (height + titleHeight) / 2, textTextPaint);
        canvas.drawText("Last: " + oldValue, (((width / 3) * 2) + width) / 2, (height + titleHeight) / 2, textTextPaint);
        super.onDraw(canvas);
    }
}
